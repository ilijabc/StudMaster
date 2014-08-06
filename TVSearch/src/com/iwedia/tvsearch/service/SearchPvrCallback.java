package com.iwedia.tvsearch.service;

import com.iwedia.dtv.pvr.IPvrCallback;
import com.iwedia.dtv.pvr.MediaInfo;
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

public class SearchPvrCallback implements IPvrCallback {

	private TVSearchUpdateService service;

	public SearchPvrCallback(TVSearchUpdateService tvSearchUpdateService) {
		service = tvSearchUpdateService;
	}

	@Override
	public void eventDeviceError() {
	}

	@Override
	public void eventMediaAdd(PvrEventMediaAdd pvrEventMediaAdd) {
		int mediaCount = service.pvrControl.updateMediaList();
		for (int mediaIndex = 0; mediaIndex < mediaCount; mediaIndex++) {
			MediaInfo media = service.pvrControl.getMediaInfo(mediaIndex);
			if (media.getTitle().equals(pvrEventMediaAdd.getTitle())) {
				service.updatePvrData(mediaIndex);
			}
		}
	}

	@Override
	public void eventMediaRemove(PvrEventMediaRemove pvrEventMediaRemove) {
		int mediaCount = service.pvrControl.updateMediaList();
		for (int mediaIndex = 0; mediaIndex < mediaCount; mediaIndex++) {
			MediaInfo media = service.pvrControl.getMediaInfo(mediaIndex);
			if (media.getTitle().equals(pvrEventMediaRemove.getTitle())) {
				service.deletePvrData(mediaIndex);
			}
		}
	}

	@Override
	public void eventPlaybackJump(PvrEventPlaybackJump arg0) {
	}

	@Override
	public void eventPlaybackPosition(PvrEventPlaybackPosition arg0) {
	}

	@Override
	public void eventPlaybackSpeed(PvrEventPlaybackSpeed arg0) {
	}

	@Override
	public void eventPlaybackStart(PvrEventPlaybackStart arg0) {
	}

	@Override
	public void eventPlaybackStop(PvrEventPlaybackStop arg0) {
	}

	@Override
	public void eventRecordAdd(PvrEventRecordAdd arg0) {
	}

	@Override
	public void eventRecordConflict(PvrEventRecordConflict arg0) {
	}

	@Override
	public void eventRecordPosition(PvrEventRecordPosition arg0) {
	}

	@Override
	public void eventRecordRemove(PvrEventRecordRemove arg0) {
	}

	@Override
	public void eventRecordResourceIssue(PvrEventRecordResourceIssue arg0) {
	}

	@Override
	public void eventRecordStart(PvrEventRecordStart arg0) {
	}

	@Override
	public void eventRecordStop(PvrEventRecordStop arg0) {
	}

	@Override
	public void eventTimeshiftJump(PvrEventTimeshiftJump arg0) {
	}

	@Override
	public void eventTimeshiftPosition(PvrEventTimeshiftPosition arg0) {
	}

	@Override
	public void eventTimeshiftSpeed(PvrEventTimeshiftSpeed arg0) {
	}

	@Override
	public void eventTimeshiftStart(PvrEventTimeshiftStart arg0) {
	}

	@Override
	public void eventTimeshiftStop(PvrEventTimeshiftStop arg0) {
	}

}
