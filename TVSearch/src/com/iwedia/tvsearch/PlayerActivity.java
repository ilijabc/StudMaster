package com.iwedia.tvsearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class PlayerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		if (intent != null) {
//			Log.d(TAG, "Intent: action=" + intent.getAction() + " data=" + intent.getDataString());
		}
	}
}
