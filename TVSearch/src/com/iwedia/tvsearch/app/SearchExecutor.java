package com.iwedia.tvsearch.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.net.Uri;

import com.iwedia.dtv.pvr.IPvrControl;
import com.iwedia.dtv.support.search.IDTVSearchManager;
import com.iwedia.dtv.support.search.IEpgSearchControl;
import com.iwedia.dtv.support.search.IPvrSearchControl;
import com.iwedia.dtv.support.search.ITeletextSearchControl;
import com.iwedia.dtv.types.InternalException;

public class SearchExecutor {
	
	private Context context;
	private IDTVSearchManager dtvManager;
	
	public SearchExecutor(Context ctx, IDTVSearchManager dtv) {
		context = ctx;
		dtvManager = dtv;
	}
	
	public void executeEpgSearchResult(Uri uri)
			throws NumberFormatException, InternalException, ParseException {
		SimpleDateFormat df = new SimpleDateFormat(IEpgSearchControl.URI_DATE_FORMAT);
		Date now = Calendar.getInstance().getTime();

		String startDate = uri.getQueryParameter(IEpgSearchControl.URI_PARAM_START_DATE);
		String endDate = uri.getQueryParameter(IEpgSearchControl.URI_PARAM_END_DATE);
		String serviceId = uri.getQueryParameter(IEpgSearchControl.URI_PARAM_SERVICE_ID);
		if (df.parse(startDate).after(now)) {
			showScheduleDialog(serviceId, startDate, endDate);
		} else {
			dtvManager.getServiceControl().startService(0, 0, Integer.parseInt(serviceId));
		}
	}
	
	public void executeTeletextSearchResult(Uri uri)
			throws NumberFormatException, InternalException {
		String track = uri.getQueryParameter(ITeletextSearchControl.URI_PARAM_PAGE_NUMBER);
		dtvManager.getTeletextControl().setCurrentTeletextTrack(0, Integer.parseInt(track));
	}
	
	public void executePvrSearchResult(Uri uri)
			throws NumberFormatException, InternalException {
		String fileIndex = uri.getQueryParameter(IPvrSearchControl.URI_PARAM_FILE_INDEX);
		String timeOffset = uri.getQueryParameter(IPvrSearchControl.URI_PARAM_TIME_OFFSET);
		dtvManager.getPvrControl().startPlayback(0, Integer.parseInt(fileIndex));
		dtvManager.getPvrControl().jump(0, Integer.parseInt(timeOffset), false);
	}

	private void showScheduleDialog(String serviceId, String startDate, String endDate) {
		// TODO Auto-generated method stub
		
	}
}
