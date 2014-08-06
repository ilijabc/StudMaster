package com.iwedia.tvsearch.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.iwedia.dtv.service.IServiceCallback;
import com.iwedia.dtv.service.ServiceListUpdateData;

public class SearchServiceCallback implements IServiceCallback {

	private TVSearchUpdateService service;

	private ScheduledExecutorService scheduler = Executors
			.newSingleThreadScheduledExecutor();

	public SearchServiceCallback(TVSearchUpdateService tvSearchUpdateService) {
		service = tvSearchUpdateService;
	}

	@Override
	public void channelChangeStatus(int liveRoute, boolean channelChanged) {
		service.deleteTeletextData();
		scheduler.scheduleAtFixedRate(new Runnable() {
	
			@Override
			public void run() {
				service.deleteTeletextData();
				service.updateTeletextData();
			}
		}, 30, 5 * 60, TimeUnit.SECONDS);
	}

	@Override
	public void safeToUnblank(int arg0) {
	}

	@Override
	public void serviceScrambledStatus(int arg0, boolean arg1) {
	}

	@Override
	public void serviceStopped(int liveRoute, boolean serviceStopped) {
	}

	@Override
	public void signalStatus(int arg0, boolean arg1) {
	}

	@Override
	public void updateServiceList(ServiceListUpdateData arg0) {
	}

}
