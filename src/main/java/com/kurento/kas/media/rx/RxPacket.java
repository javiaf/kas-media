package com.kurento.kas.media.rx;

public class RxPacket {

	private int timeBaseNum;
	private int timeBaseDen;
	private long pts;
	private long startTime;
	private long rxTime;
	private int encodedSize;

	public RxPacket(int timeBaseNum, int timeBaseDen, long pts, long startTime,
			long rxTime, int encodedSize) {
		this.timeBaseNum = timeBaseNum;
		this.timeBaseDen = timeBaseDen;
		this.pts = pts;
		this.startTime = startTime;
		this.rxTime = rxTime;
		this.encodedSize = encodedSize;
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

	public int getEncodedSize() {
		return encodedSize;
	}

}
