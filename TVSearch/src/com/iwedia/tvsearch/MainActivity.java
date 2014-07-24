package com.iwedia.tvsearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.iwedia.dtv.dtvmanager.DTVManager;
import com.iwedia.dtv.dtvmanager.IDTVManager;
import com.iwedia.dtv.support.search.IDTVSearchManager;

public class MainActivity extends Activity {
	public static final String TAG = "zzz";

	private IDTVSearchManager dtvManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
//		IDTVSearchManager dtvManager = DTVSearchManager.getInstance();
		IDTVManager m = new DTVManager();
		
		Log.d(TAG, "onCreate");
		
		Intent intent = getIntent();
		if (intent != null) {
			Log.d(TAG, "Intent: action=" + intent.getAction() + " data=" + intent.getDataString());
		}
	}
}
