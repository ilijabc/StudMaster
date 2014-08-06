package com.iwedia.dtv.support.overlay;

public interface IDTVManager extends com.iwedia.dtv.dtvmanager.IDTVManager {

	@Override
	public ITeletextControl getTeletextControl();
	
	public IPvrControl getPvrControl2();
}
