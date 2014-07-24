package com.iwedia.dtv.support.search;

import android.net.Uri;

import com.iwedia.dtv.epg.EpgEvent;

public class EpgSearchEvent extends EpgEvent {

	public Uri getUri() {
		// Example: tv://epg_search_result?service_id=101&epg_start_time=16:00:00&epg_end_time=17:15:00&query=test
		return null;
	}

}
