package com.iwedia.dtv.support.search;

import com.iwedia.dtv.epg.IEpgControl;

public interface IEpgSearchControl extends IEpgControl {

	public String URI_DATE_FORMAT = null;
	public String URI_PARAM_START_DATE = null;
	public String URI_PARAM_END_DATE = null;
	public String URI_PARAM_SERVICE_ID = null;

	public SearchableEpgEvent[] getEventsWithText(String query);
}
