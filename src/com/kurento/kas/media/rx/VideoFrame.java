package com.kurento.kas.media.rx;

public class VideoFrame extends RxPacket {

	private int[] dataFrame;
	private int width;
	private int height;

	public VideoFrame(int[] dataFrame, int width, int height, int timeBaseNum,
			int timeBaseDen, long pts, long startTime, long rxTime,
			int encodedSize) {
		super(timeBaseNum, timeBaseDen, pts, startTime, rxTime, encodedSize);
		this.dataFrame = dataFrame;
		this.width = width;
		this.height = height;
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

}
