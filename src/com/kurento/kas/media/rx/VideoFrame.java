package com.kurento.kas.media.rx;

public class VideoFrame {

	private int[] dataFrame;
	private int width;
	private int height;
	private int timeBaseNum;
	private int timeBaseDen;
	private long pts;
	private long startTime;
	private long rxTime;

	public VideoFrame(int[] dataFrame, int width, int height, int timeBaseNum,
			int timeBaseDen, long pts, long startTime, long rxTime) {
		this.dataFrame = dataFrame;
		this.width = width;
		this.height = height;
		this.timeBaseNum = timeBaseNum;
		this.timeBaseDen = timeBaseDen;
		this.pts = pts;
		this.startTime = startTime;
		this.rxTime = rxTime;
	}

	public int[] getDataFrame() {
		return dataFrame;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
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
