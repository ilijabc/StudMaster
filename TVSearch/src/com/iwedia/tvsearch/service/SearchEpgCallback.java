package com.iwedia.tvsearch.service;

import com.iwedia.dtv.epg.IEpgCallback;

public class SearchEpgCallback implements IEpgCallback {

	private TVSearchUpdateService service;
	
	public SearchEpgCallback(TVSearchUpdateService tvSearchUpdateService) {
		service = tvSearchUpdateService;
	}

	@Override
	public void pfAcquisitionFinished(int filterID, int serviceIndex) {
	}

	@Override
	public void pfEventChanged(int filterID, int serviceIndex) {
		service.discardEpgData(serviceIndex);
	}

	@Override
	public void scAcquisitionFinished(int filterID, int serviceIndex) {
	}

	@Override
	public void scEventChanged(int filterID, int serviceIndex) {
		service.deleteEpgData(serviceIndex);
		service.updateEpgData(serviceIndex);
	}

}
