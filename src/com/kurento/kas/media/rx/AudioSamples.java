package com.kurento.kas.media.rx;

public class AudioSamples {

	private byte[] dataSamples;
	private int size;
	private int timeBaseNum;
	private int timeBaseDen;
	private long pts;
	private long startTime;
	private long rxTime;

	public AudioSamples(byte[] dataSamples, int size, int timeBaseNum,
			int timeBaseDen, long pts, long startTime, long rxTime) {
		this.dataSamples = dataSamples;
		this.size = size;
		this.timeBaseNum = timeBaseNum;
		this.timeBaseDen = timeBaseDen;
		this.pts = pts;
		this.startTime = startTime;
		this.rxTime = rxTime;
	}

	public byte[] getDataSamples() {
		return dataSamples;
	}

	public int getSize() {
		return size;
	}

	public int getTimeBaseNum() {
		return timeBaseNum;
	}

	public int getTimeBaseDen() {
		return timeBaseDen;
	}

	public long getPts() {
		return pts;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getRxTime() {
		return rxTime;
	}

}
