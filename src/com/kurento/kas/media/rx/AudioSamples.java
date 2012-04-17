package com.kurento.kas.media.rx;

public class AudioSamples extends RxPacket {

	private byte[] dataSamples;
	private int size;

	public AudioSamples(byte[] dataSamples, int size, int timeBaseNum,
			int timeBaseDen, long pts, long startTime, long rxTime) {
		super(timeBaseNum, timeBaseDen, pts, startTime, rxTime);
		this.dataSamples = dataSamples;
		this.size = size;
	}

	public byte[] getDataSamples() {
		return dataSamples;
	}

	public int getSize() {
		return size;
	}

}
