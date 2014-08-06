package com.iwedia.dtv.support.overlay;

public interface ITeletextControl extends com.iwedia.dtv.teletext.ITeletextControl {

	@Override
	public TeletextTrack getTeletextTrack(int routeId, int trackIndex);
}
