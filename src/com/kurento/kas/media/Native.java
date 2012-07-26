package com.kurento.kas.media;

public class Native {

	static {
		System.loadLibrary("kas-media-native");
	}

	protected long self;

	public long getSelf() {
		return self;
	}

}
