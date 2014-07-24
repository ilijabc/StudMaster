package com.iwedia.tvsearch;

import java.util.ArrayList;

import com.iwedia.dtv.dtvmanager.IDTVManager;
import com.iwedia.dtv.epg.EpgEvent;
import com.iwedia.dtv.epg.IEpgControl;
import com.iwedia.dtv.pvr.IPvrControl;
import com.iwedia.dtv.pvr.MediaInfo;
import com.iwedia.dtv.service.IServiceControl;
import com.iwedia.dtv.service.Service;
import com.iwedia.dtv.teletext.ITeletextControl;
import com.iwedia.dtv.teletext.TeletextTrack;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

public class DTVSearchService extends android.app.Service {

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
	int[] lastUpdateTime;
	int nowTime;
	
	ITeletextControl ttxControl = dtvManager.getTeletextControl();
	int routeId = 0;
	final Uri TELETEXT_TABLE_URL = Uri.parse("teletext_table");
	final String PAGE_INDEX = "";
	final String PAGE_NAME = "";
	final String PAGE_NUMBER = "";
	
	IPvrControl pvrControl = dtvManager.getPvrControl();
	final Uri PVR_TABLE_URL = Uri.parse("pvr_table");
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	private void refreshEpgData() {
		// brisanje zastarelih podataka
		getContentResolver().delete(EPG_TABLE_URL, EVENT_END_TIME + "<" + nowTime, null);
		
		// ubacivanje novih podataka
		// iteracija servisa
		int servicesCount = serviceControl.getServiceListCount(listIndex);
		for (int serviceIndex = 0; serviceIndex < servicesCount; serviceIndex++) {
			ArrayList<ContentValues> values = new ArrayList<ContentValues>();
			// iteracija dogadjaja
			int eventsCount = epgControl.getAvailableEventsNumber(filterId, serviceIndex);
			for (int eventIndex = 0; eventIndex < eventsCount; eventIndex++) {
				EpgEvent event = epgControl.getRequestedEvent(
						filterId, serviceIndex, eventIndex);
				long startTime = event.getStartTime().getCalendar().getTimeInMillis();
				long endTime = event.getEndTime().getCalendar().getTimeInMillis();
				if (startTime > lastUpdateTime[serviceIndex]) {
					ContentValues value = new ContentValues();
					value.put(EVENT_ID, event.getEventId());
					value.put(EVENT_NAME, event.getName());
					value.put(EVENT_DESCRIPTION, event.getDescription());
					value.put(EVENT_START_TIME, startTime);
					value.put(EVENT_END_TIME, endTime);
					value.put(SERVICE_ID, event.getServiceIndex());
					values.add(value);
				}
			}
			lastUpdateTime[serviceIndex] = nowTime;
			getContentResolver().bulkInsert(EPG_TABLE_URL, 
					values.toArray(new ContentValues[values.size()]));
		}
	}
	
	private void refreshTeletextData() {
		getContentResolver().delete(TELETEXT_TABLE_URL, null, null);
		
		Service service = serviceControl.getActiveService(routeId);
		
		int traksCount = ttxControl.getTeletextTrackCount(routeId);
		ContentValues values[] = new ContentValues[traksCount];
		for (int trackIndex = 0; trackIndex < traksCount; trackIndex++) {
			TeletextTrack track = ttxControl.getTeletextTrack(routeId, trackIndex);
			ContentValues value = new ContentValues();
			value.put(PAGE_INDEX, track.getIndex());
			value.put(PAGE_NAME, track.getName());
			value.put(PAGE_NUMBER, track.getTeletextPageNumber());
			values[trackIndex] = value;
		}
		getContentResolver().bulkInsert(TELETEXT_TABLE_URL, values);
	}
	
	private void refreshPvrData() {
		ArrayList<MediaInfo> recordings = pvrControl.getRecordings();
		ContentValues values[] = new ContentValues[recordings.size()];
		for (MediaInfo info : recordings) {
			info.
		}
	}
}
