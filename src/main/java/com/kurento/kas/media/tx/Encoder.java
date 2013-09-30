package com.kurento.kas.media.tx;

import java.nio.ByteBuffer;
import java.util.HashMap;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.util.Log;

public class Encoder {

	private MediaCodec mediaCodec;
	private MediaFormat mediaFormat;
	private HashMap<String, Integer> formats;

	static final int NV21 = 2;
	static final int YV12 = 3;
	private static final String TAG = "MediaEncoder";

	public Encoder(String codecName, String mimeType, String colorFormat,
			int width, int height, int frameRate, int bitRate, int gopSize) {

		initFormats();
		// mediaCodec = MediaCodec.createByCodecName(codecName);

		mediaCodec = MediaCodec.createEncoderByType(mimeType);
		mediaFormat = MediaFormat.createVideoFormat(mimeType, width, height);
		mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, bitRate);
		mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, frameRate);
		mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT,
				formats.get(colorFormat));
		mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, gopSize);
		mediaFormat.setInteger("stride", width);
		mediaFormat.setInteger("slice-height", height);
		Log.d(TAG, mediaFormat.toString());
		mediaCodec.configure(mediaFormat, null, null,
				MediaCodec.CONFIGURE_FLAG_ENCODE);
		mediaCodec.start();

	}

	private void initFormats() {
		formats = new HashMap();

		formats.put("12bitRGB444",
				MediaCodecInfo.CodecCapabilities.COLOR_Format12bitRGB444);
		formats.put("16bitARGB1555",
				MediaCodecInfo.CodecCapabilities.COLOR_Format16bitARGB1555);
		formats.put("16bitARGB4444",
				MediaCodecInfo.CodecCapabilities.COLOR_Format16bitARGB4444);
		formats.put("16bitBGR565",
				MediaCodecInfo.CodecCapabilities.COLOR_Format16bitBGR565);

		formats.put("16bitRGB565",
				MediaCodecInfo.CodecCapabilities.COLOR_Format16bitRGB565);
		formats.put("18BitBGR666",
				MediaCodecInfo.CodecCapabilities.COLOR_Format18BitBGR666);
		formats.put("18bitARGB1665",
				MediaCodecInfo.CodecCapabilities.COLOR_Format18bitARGB1665);
		formats.put("18bitRGB666",
				MediaCodecInfo.CodecCapabilities.COLOR_Format18bitRGB666);

		formats.put("19bitARGB1666",
				MediaCodecInfo.CodecCapabilities.COLOR_Format19bitARGB1666);
		formats.put("24BitABGR6666",
				MediaCodecInfo.CodecCapabilities.COLOR_Format24BitABGR6666);
		formats.put("24BitARGB6666",
				MediaCodecInfo.CodecCapabilities.COLOR_Format24BitARGB6666);
		formats.put("24bitARGB1887",
				MediaCodecInfo.CodecCapabilities.COLOR_Format24bitARGB1887);
		formats.put("24bitBGR888",
				MediaCodecInfo.CodecCapabilities.COLOR_Format24bitBGR888);
		formats.put("24bitRGB888",
				MediaCodecInfo.CodecCapabilities.COLOR_Format24bitRGB888);

		formats.put("25bitARGB1888",
				MediaCodecInfo.CodecCapabilities.COLOR_Format25bitARGB1888);
		formats.put("32bitARGB8888",
				MediaCodecInfo.CodecCapabilities.COLOR_Format32bitARGB8888);
		formats.put("32bitBGRA8888",
				MediaCodecInfo.CodecCapabilities.COLOR_Format32bitBGRA8888);
		formats.put("8bitRGB332",
				MediaCodecInfo.CodecCapabilities.COLOR_Format8bitRGB332);
		formats.put("CbYCrY",
				MediaCodecInfo.CodecCapabilities.COLOR_FormatCbYCrY);
		formats.put("CrYCbY",
				MediaCodecInfo.CodecCapabilities.COLOR_FormatCrYCbY);

		formats.put("L16", MediaCodecInfo.CodecCapabilities.COLOR_FormatL16);
		formats.put("L2", MediaCodecInfo.CodecCapabilities.COLOR_FormatL2);
		formats.put("L24", MediaCodecInfo.CodecCapabilities.COLOR_FormatL24);
		formats.put("L32", MediaCodecInfo.CodecCapabilities.COLOR_FormatL32);
		formats.put("L4", MediaCodecInfo.CodecCapabilities.COLOR_FormatL4);
		formats.put("L8", MediaCodecInfo.CodecCapabilities.COLOR_FormatL8);
		formats.put("Monochrome",
				MediaCodecInfo.CodecCapabilities.COLOR_FormatMonochrome);
		formats.put("RawBayer10bit",
				MediaCodecInfo.CodecCapabilities.COLOR_FormatRawBayer10bit);
		formats.put("RawBayer8bit",
				MediaCodecInfo.CodecCapabilities.COLOR_FormatRawBayer8bit);
		formats.put(
				"RawBayer8bitcompressed",
				MediaCodecInfo.CodecCapabilities.COLOR_FormatRawBayer8bitcompressed);
		formats.put("YCbYCr",
				MediaCodecInfo.CodecCapabilities.COLOR_FormatYCbYCr);
		formats.put("YCrYCb",
				MediaCodecInfo.CodecCapabilities.COLOR_FormatYCrYCb);

		formats.put("YUV411PackedPlanar",
				MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV411PackedPlanar);
		formats.put("YUV411Planar",
				MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV411Planar);
		formats.put("YUV420PackedPlanar",
				MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420PackedPlanar);
		formats.put(
				"YUV420PackedSemiPlanar",
				MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420PackedSemiPlanar);
		formats.put("YUV420Planar",
				MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Planar);
		formats.put("YUV420SemiPlanar",
				MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar);
		formats.put("YUV422PackedPlanar",
				MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV422PackedPlanar);
		formats.put(
				"YUV422PackedSemiPlanar",
				MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV422PackedSemiPlanar);
		formats.put("YUV422Planar",
				MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV422Planar);
		formats.put("YUV422SemiPlanar",
				MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV422SemiPlanar);
		formats.put("YUV444Interleaved",
				MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV444Interleaved);
		formats.put(
				"QCOM_YUV420SemiPlanar",
				MediaCodecInfo.CodecCapabilities.COLOR_QCOM_FormatYUV420SemiPlanar);
		formats.put(
				"TI_YUV420PackedSemiPlanar",
				MediaCodecInfo.CodecCapabilities.COLOR_TI_FormatYUV420PackedSemiPlanar);

	}

	public void close() {
		try {
			mediaCodec.stop();
			mediaCodec.release();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] putVideoFrame(byte[] input, int width, int height) {
		byte[] frConverted, output;
		ByteBuffer[] inputBuffers, outputBuffers;
		long cDelay;
		int inputBufferIndex, outputBufferIndex;
		ByteBuffer inputBuffer, outputBuffer;

		try {
			frConverted = new byte[input.length];
			cDelay = swapColorFormat(input, frConverted, NV21,
					mediaFormat.getInteger(MediaFormat.KEY_COLOR_FORMAT),
					width, height);
			Log.d(TAG, "Conversion Delay: " + cDelay / 1000000000.00);
			inputBuffers = mediaCodec.getInputBuffers();
			outputBuffers = mediaCodec.getOutputBuffers();
			inputBufferIndex = mediaCodec.dequeueInputBuffer(-1);
			if (inputBufferIndex >= 0) {
				inputBuffer = inputBuffers[inputBufferIndex];
				inputBuffer.clear();
				inputBuffer.put(frConverted);
				mediaCodec.queueInputBuffer(inputBufferIndex, 0,
						frConverted.length, 0, 0);
			}
			MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();

			outputBufferIndex = -1;
			while (outputBufferIndex < 0) {
				outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo,
						-1);
				if (outputBufferIndex < 0) {
					switch (outputBufferIndex) {
					case MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED:
						Log.d(TAG, "Output Buffers Changed");
						break;
					case MediaCodec.INFO_OUTPUT_FORMAT_CHANGED:
						Log.d(TAG, "Output Format Changed "
								+ mediaCodec.getOutputFormat().toString());
						break;
					case MediaCodec.INFO_TRY_AGAIN_LATER:
						Log.d(TAG,
								"Timeout receiving Output Buffer. Try Again!");
						break;
					default:
						Log.d(TAG, "No Output Buffer available");
					}
				}
			}
			outputBuffer = outputBuffers[outputBufferIndex];
			output = new byte[bufferInfo.size];
			outputBuffer.get(output);

			mediaCodec.releaseOutputBuffer(outputBufferIndex, false);
			return output;

		} catch (Throwable t) {
			System.out.println("catch");
			t.printStackTrace();
		}
		return null;

	}

	public int getOutputSize(byte[] output) {
		if (output != null)
			return output.length;
		else
			return -1;

	}

	public long swapColorFormat(byte[] frameIn, byte[] frameOut,
			int colorFormatIn, int colorFormatOut, int width, int height) {
		int frameSize = width * height;
		int qFrameSize = frameSize / 4;
		long tIni = System.nanoTime();
		switch (colorFormatIn) {

		case NV21:
			switch (colorFormatOut) {

			case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Planar:
				// CHECK THIS!!

				System.arraycopy(frameIn, 0, frameOut, 0, frameSize); // Y
				for (int i = 0; i < qFrameSize; i++) {
					frameOut[frameSize + i] = frameIn[frameSize + i * 2 + 1];
					frameOut[frameSize + qFrameSize + i] = frameIn[frameSize
							+ i * 2 + 1];
				}
				break;
			case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar:

				System.arraycopy(frameIn, 0, frameOut, 0, frameSize); // Y

				for (int i = 0; i < qFrameSize; i++) {
					frameOut[frameSize + i * 2] = frameIn[frameSize + i * 2 + 1]; // Cb
																					// (U)
					frameOut[frameSize + i * 2 + 1] = frameIn[frameSize + i * 2]; // Cr
																					// (V)
				}
				break;
			}
			break;
		case YV12:
			switch (colorFormatOut) {

			case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Planar:
				System.arraycopy(frameIn, 0, frameOut, 0, frameSize); // Y
				System.arraycopy(frameIn, frameSize, frameOut, frameSize
						+ qFrameSize, qFrameSize); // Cr (V)
				System.arraycopy(frameIn, frameSize + qFrameSize, frameOut,
						frameSize, qFrameSize); // Cb (U)
				break;
			case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar:

				System.arraycopy(frameIn, 0, frameOut, 0, frameSize); // Y

				for (int i = 0; i < qFrameSize; i++) {
					frameOut[frameSize + i * 2] = frameIn[frameSize + i
							+ qFrameSize]; // Cb (U)
					frameOut[frameSize + i * 2 + 1] = frameIn[frameSize + i]; // Cr
																				// (V)
				}
				break;
			}
			break;
		}

		return System.nanoTime() - tIni;

	}

}