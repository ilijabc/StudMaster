package com.iwedia.tvsearch;

import android.net.Uri;

import com.iwedia.dtv.support.search.EpgSearchEvent;
import com.iwedia.dtv.support.search.IDTVSearchManager;
import com.iwedia.dtv.support.search.IEpgSearchControl;
import com.iwedia.dtv.support.search.IPvrSearchControl;
import com.iwedia.dtv.support.search.ITeletextSearchControl;
import com.iwedia.dtv.support.search.PvrSearchFile;
import com.iwedia.dtv.support.search.TeletextSearchPage;

public class SearchProvider {
	
	private IDTVSearchManager dtvManager;
	
	private static final int EPG_SEARCH_RESULT = 0;
	private static final int TELETEXT_SEARCH_RESULT = 1;
	private static final int PVR_SEARCH_RESULT = 2;
	
	public SearchProvider(IDTVSearchManager dtv) {
		dtvManager = dtv;
	}
	
	public void getEpgSearchResults(String query) {
		IEpgSearchControl epgCtl = dtvManager.getEpgSearchControl();

		EpgSearchEvent events[] = epgCtl.getEventsWithText(query);
		for (EpgSearchEvent event : events) {
			String title = event.getName();
			String suggestion = event.getDescription();
			Uri uri = event.getUri();
			sendResultToQSB(EPG_SEARCH_RESULT, title, suggestion, uri);
		}
	}
	
	public void getTeletextSearchResults(String query) {
		ITeletextSearchControl ttxCtl = dtvManager.getTeletextSearchControl();

		TeletextSearchPage pages[] = ttxCtl.getPagesWithText(query);
		for (TeletextSearchPage page : pages) {
			String title = page.getService().getName() + " : " + page.getNumber();
			String suggestion = page.getSearchedTextLine();
			Uri uri = page.getUri();
			sendResultToQSB(TELETEXT_SEARCH_RESULT, title, suggestion, uri);
		}
	}
	
	public void getPvrSearchResults(String query) {
		IPvrSearchControl pvrCtl = dtvManager.getPvrSearchControl();

		PvrSearchFile files[] = pvrCtl.getEventsWithText(query);
		for (PvrSearchFile file : files) {
			String title = file.getInfo().getTitle();
			String suggestion = file.getInfo().getDescription();
			Uri uri = file.getUri();
			sendResultToQSB(PVR_SEARCH_RESULT, title, suggestion, uri);
		}
	}
	
	private void sendResultToQSB(int type, String title, String suggestion, Uri uri) {
		// TODO Auto-generated method stub
	}
}
