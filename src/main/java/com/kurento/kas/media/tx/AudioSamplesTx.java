package com.kurento.kas.media.tx;

public class AudioSamplesTx {

	private short[] dataSamples;
	private int size;
	private long time;

	public AudioSamplesTx(short[] data, int size, long time) {
		this.dataSamples = data;
		this.size = size;
		this.time = time;
	}

	public short[] getDataSamples() {
		return dataSamples;
	}

	public int getSize() {
		return size;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
