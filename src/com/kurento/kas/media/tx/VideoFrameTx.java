package com.kurento.kas.media.tx;

public class VideoFrameTx {

	private byte[] dataFrame;
	private int width;
	private int height;
	private long time;

	public VideoFrameTx(byte[] data, int width, int height, long time) {
		this.dataFrame = data;
		this.width = width;
		this.height = height;
		this.time = time;
	}

	public byte[] getDataFrame() {
		return dataFrame;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
