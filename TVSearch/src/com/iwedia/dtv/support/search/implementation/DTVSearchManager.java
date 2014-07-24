package com.iwedia.dtv.support.search.implementation;

import com.iwedia.dtv.dtvmanager.DTVManager;
import com.iwedia.dtv.support.search.IDTVSearchManager;
import com.iwedia.dtv.support.search.IEpgSearchControl;
import com.iwedia.dtv.support.search.IPvrSearchControl;
import com.iwedia.dtv.support.search.ITeletextSearchControl;

public class DTVSearchManager extends DTVManager implements IDTVSearchManager {

	private static DTVSearchManager mInstance = null;
	
	public static DTVSearchManager getInstance() {
		if (mInstance == null) {
			mInstance = new DTVSearchManager();
		}
		return mInstance;
	}

	private EpgSearchControl epgSearchControl;
	private TeletextSearchControl teletextSearchControl;
	private PvrSearchControl pvrSearchControl;
	
	public DTVSearchManager() {
		epgSearchControl = new EpgSearchControl();
		teletextSearchControl = new TeletextSearchControl();
		pvrSearchControl = new PvrSearchControl();
	}
	
	@Override
	public IEpgSearchControl getEpgSearchControl() {
		return epgSearchControl;
	}

	@Override
	public ITeletextSearchControl getTeletextSearchControl() {
		return teletextSearchControl;
	}

	@Override
	public IPvrSearchControl getPvrSearchControl() {
		return pvrSearchControl;
	}
}
