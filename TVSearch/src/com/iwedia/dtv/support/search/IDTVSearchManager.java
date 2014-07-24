package com.iwedia.dtv.support.search;

import com.iwedia.dtv.dtvmanager.IDTVManager;

public interface IDTVSearchManager extends IDTVManager {

	public IEpgSearchControl getEpgSearchControl();
	
	public ITeletextSearchControl getTeletextSearchControl();
	
	public IPvrSearchControl getPvrSearchControl();
}
