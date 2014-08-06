package com.iwedia.tvsearch.service;

import java.util.ArrayList;

import com.iwedia.dtv.epg.EpgEvent;
import com.iwedia.dtv.epg.IEpgCallback;
import com.iwedia.dtv.epg.IEpgControl;
import com.iwedia.dtv.pvr.IPvrCallback;
import com.iwedia.dtv.pvr.PvrEventMediaAdd;
import com.iwedia.dtv.pvr.PvrEventMediaRemove;
import com.iwedia.dtv.pvr.PvrEventPlaybackJump;
import com.iwedia.dtv.pvr.PvrEventPlaybackPosition;
import com.iwedia.dtv.pvr.PvrEventPlaybackSpeed;
import com.iwedia.dtv.pvr.PvrEventPlaybackStart;
import com.iwedia.dtv.pvr.PvrEventPlaybackStop;
import com.iwedia.dtv.pvr.PvrEventRecordAdd;
import com.iwedia.dtv.pvr.PvrEventRecordConflict;
import com.iwedia.dtv.pvr.PvrEventRecordPosition;
import com.iwedia.dtv.pvr.PvrEventRecordRemove;
import com.iwedia.dtv.pvr.PvrEventRecordResourceIssue;
import com.iwedia.dtv.pvr.PvrEventRecordStart;
import com.iwedia.dtv.pvr.PvrEventRecordStop;
import com.iwedia.dtv.pvr.PvrEventTimeshiftJump;
import com.iwedia.dtv.pvr.PvrEventTimeshiftPosition;
import com.iwedia.dtv.pvr.PvrEventTimeshiftSpeed;
import com.iwedia.dtv.pvr.PvrEventTimeshiftStart;
import com.iwedia.dtv.pvr.PvrEventTimeshiftStop;
import com.iwedia.dtv.service.IServiceCallback;
import com.iwedia.dtv.service.IServiceControl;
import com.iwedia.dtv.service.Service;
import com.iwedia.dtv.setup.ISetupControl;
import com.iwedia.dtv.support.overlay.IDTVManager;
import com.iwedia.dtv.support.overlay.IPvrControl;
import com.iwedia.dtv.support.overlay.ITeletextControl;
import com.iwedia.dtv.support.overlay.MetadataInfo;
import com.iwedia.dtv.support.overlay.TeletextTrack;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

public class TVSearchUpdateService extends android.app.Service {

	IDTVManager dtvManager;

	IEpgControl epgControl = dtvManager.getEpgControl();
	IServiceControl serviceControl = dtvManager.getServiceControl();
	int listIndex = 0;
	int filterId = 0;
	final Uri EPG_TABLE_URL = Uri.parse("epg_table");
	final String EVENT_ID = "event_id";
	final String EVENT_NAME = "event_name";
	final String EVENT_DESCRIPTION = "event_description";
	final String EVENT_START_TIME = "event_start_time";
	final String EVENT_END_TIME = "event_end_time";
	final String SERVICE_ID = "service_id";

	ITeletextControl ttxControl = dtvManager.getTeletextControl();
	int routeId = 0;
	final Uri TELETEXT_TABLE_URL = Uri.parse("teletext_table");
	final String PAGE_INDEX = "";
	final String PAGE_TEXT = "";
	final String PAGE_NUMBER = "";

	IPvrControl pvrControl = dtvManager.getPvrControl2();
	final Uri PVR_TABLE_URL = Uri.parse("pvr_table");
	final String PVR_EVENT_FILE_ID = "pvr_event_file_id";
	final String PVR_EVENT_DESCRIPTION = "pvr_event_description";
	final String PVR_EVENT_OFFSET_TIME = "pvr_event_offset_time";

	final int UNDEFINED = -1;

	ISetupControl setupControl = dtvManager.getSetupControl();
	IEpgCallback instanceOfIEpgCallback = new SearchEpgCallback(this);
	IPvrCallback instanceOfIPvrCallback = new SearchPvrCallback(this);
	IServiceCallback instanceOfIServiceCalback = new SearchServiceCallback(this);

	@Override
	public void onCreate() {
		super.onCreate();
		epgControl.registerCallback(instanceOfIEpgCallback, filterId);
		pvrControl.registerCallback(instanceOfIPvrCallback);
		serviceControl.registerCallback(instanceOfIServiceCalback);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	/*
	 * EPG data
	 */

	public void createEpgData() {
		int servicesCount = serviceControl.getServiceListCount(listIndex);
		for (int serviceIndex = 0; serviceIndex < servicesCount; serviceIndex++) {
			updateEpgData(serviceIndex);
		}
	}

	public void discardEpgData(int serviceIndex) {
		long nowTime = setupControl.getTimeDate().getCalendar()
				.getTimeInMillis();
		getContentResolver().delete(
				EPG_TABLE_URL,
				EVENT_END_TIME + " < ? AND " + SERVICE_ID + " = ?",
				new String[] { String.valueOf(nowTime),
						String.valueOf(serviceIndex) });
	}

	public void deleteEpgData(int serviceIndex) {
		getContentResolver().delete(EPG_TABLE_URL,
				SERVICE_ID + " = " + serviceIndex, null);
	}

	public void updateEpgData(int serviceIndex) {
		int eventsCount = epgControl.getAvailableEventsNumber(filterId,
				serviceIndex);
		ContentValues values[] = new ContentValues[eventsCount];
		for (int eventIndex = 0; eventIndex < eventsCount; eventIndex++) {
			EpgEvent event = epgControl.getRequestedEvent(filterId,
					serviceIndex, eventIndex);
			long startTime = event.getStartTime().getCalendar()
					.getTimeInMillis();
			long endTime = event.getEndTime().getCalendar().getTimeInMillis();
			ContentValues value = new ContentValues();
			value.put(EVENT_ID, event.getEventId());
			value.put(EVENT_NAME, event.getName());
			value.put(EVENT_DESCRIPTION, event.getDescription());
			value.put(EVENT_START_TIME, startTime);
			value.put(EVENT_END_TIME, endTime);
			value.put(SERVICE_ID, serviceIndex);
			values[eventIndex] = value;
		}
		getContentResolver().bulkInsert(EPG_TABLE_URL, values);
	}

	/*
	 * Teletext data
	 */

	public void deleteTeletextData() {
		getContentResolver().delete(TELETEXT_TABLE_URL, null, null);
	}

	public void updateTeletextData() {
		int traksCount = ttxControl.getTeletextTrackCount(routeId);
		ContentValues values[] = new ContentValues[traksCount];
		for (int trackIndex = 0; trackIndex < traksCount; trackIndex++) {
			TeletextTrack track = ttxControl.getTeletextTrack(routeId,
					trackIndex);
			ContentValues value = new ContentValues();
			value.put(PAGE_INDEX, track.getIndex());
			value.put(PAGE_TEXT, track.getTeletextText());
			value.put(PAGE_NUMBER, track.getTeletextPageNumber());
			values[trackIndex] = value;
		}
		getContentResolver().bulkInsert(TELETEXT_TABLE_URL, values);
	}

	/*
	 * PVR data
	 */

	public void createPvrData() {
		int mediaCount = pvrControl.updateMediaList();
		for (int mediaIndex = 0; mediaIndex < mediaCount; mediaIndex++) {
			updatePvrData(mediaIndex);
		}
	}

	public void deletePvrData(int mediaIndex) {
		getContentResolver().delete(PVR_TABLE_URL,
				PVR_EVENT_FILE_ID + " = " + mediaIndex, null);
	}

	public void updatePvrData(int mediaIndex) {
		MetadataInfo metadata = pvrControl.getMetadataInfo(mediaIndex);
		int eventsCount = metadata.getEventsNumber();
		long firstStartTime = UNDEFINED;
		ContentValues values[] = new ContentValues[eventsCount];
		for (int eventIndex = 0; eventIndex < eventsCount; eventIndex++) {
			EpgEvent event = metadata.getEpgEvent(eventIndex);
			long startTime = event.getStartTime().getCalendar()
					.getTimeInMillis();
			if (firstStartTime == UNDEFINED) {
				firstStartTime = startTime;
			}
			ContentValues value = new ContentValues();
			value.put(PVR_EVENT_FILE_ID, mediaIndex);
			value.put(PVR_EVENT_DESCRIPTION, event.getDescription());
			value.put(PVR_EVENT_OFFSET_TIME, startTime - firstStartTime);
			values[eventIndex] = value;
		}
		getContentResolver().bulkInsert(PVR_TABLE_URL, values);
	}
}
