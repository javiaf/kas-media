package com.kurento.kas.media.ports;

import com.kurento.kas.media.Native;

public class MediaPort extends Native {

	public MediaPort(long self) {
		this.self = self;
	}

	public int getPort() {
		return getPort(self);
	}

	private native int getPort(long self);

}
