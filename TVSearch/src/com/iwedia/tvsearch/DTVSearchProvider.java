package com.iwedia.tvsearch;

import com.iwedia.dtv.epg.EpgEvent;
import com.iwedia.dtv.epg.IEpgControl;
import com.iwedia.dtv.support.search.EpgSearchEvent;
import com.iwedia.dtv.support.search.IDTVSearchManager;
import com.iwedia.dtv.support.search.IEpgSearchControl;
import com.iwedia.dtv.support.search.implementation.DTVSearchManager;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class DTVSearchProvider extends ContentProvider {

	private IDTVSearchManager dtvManager;
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		dtvManager = DTVSearchManager.getInstance();
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Cursor getEpgSearchResults(String query) {
		IEpgSearchControl epgCtl = dtvManager.getEpgSearchControl();

		EpgSearchEvent events[] = epgCtl.getEventsWithText(query);
		for (EpgSearchEvent event : events) {
			String title = event.getName();
			String suggestion = event.getDescription();
			Uri uri = event.getUri();
			sendResultToQSB(EPG_SEARCH_RESULT, title, suggestion, uri);
		}
	}
}
