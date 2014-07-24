package com.iwedia.dtv.support.search;

import com.iwedia.dtv.pvr.IPvrControl;

public interface IPvrSearchControl extends IPvrControl {

	String URI_PARAM_FILE_INDEX = null;
	String URI_PARAM_TIME_OFFSET = null;

	public PvrSearchFile[] getEventsWithText(String query);

}
