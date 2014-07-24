package com.iwedia.dtv.support.search;

import com.iwedia.dtv.teletext.ITeletextControl;

public interface ITeletextSearchControl extends ITeletextControl {

	String URI_PARAM_PAGE_NUMBER = null;

	public TeletextSearchPage[] getPagesWithText(String query);

}
