package com.kurento.kas.media.tx;

import java.nio.ByteBuffer;

import java.util.HashMap;
import com.kurento.kas.media.codecs.FormatConverter;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.util.Log;

public class Encoder {

	private MediaCodec mediaCodec;
	private MediaFormat mediaFormat;
	private HashMap<String, Integer> formats;
	//private byte[] csd;       -- FOR CODEC SPECIFIC DATA RETURNED ENCODING FIRST FRAME
	private int width;
	private int height;
	static final int NV21 = 2;
	static final int YV12 = 3;
	private static final String TAG = "MediaEncoder";

	public Encoder(String codecName, String mimeType, String colorFormat,
			int width, int height, int frameRate, int bitRate, int gopSize) {

		initFormats();
		this.width = width;
		this.height = height;
		int iFrameInterval = Math.round((float) gopSize / (float) frameRate);
		if (codecName != null && !codecName.equals("")) {
			mediaCodec = MediaCodec.createByCodecName(codecName);
		} else {
			mediaCodec = MediaCodec.createEncoderByType(mimeType);
		}
		mediaFormat = MediaFormat.createVideoFormat(mimeType, width, height);
		mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, bitRate);
		mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, frameRate);
		mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT,
				formats.get(colorFormat));
		mediaFormat
				.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, iFrameInterval);
		mediaFormat.setInteger("stride", width);
		mediaFormat.setInteger("slice-height", height);
		Log.d(TAG, mediaFormat.toString());
		mediaCodec.configure(mediaFormat, null, null,
				MediaCodec.CONFIGURE_FLAG_ENCODE);
		mediaCodec.start();
		Log.d(TAG, "Output Format beginning " + mediaFormat.toString());

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
			long tIni = System.nanoTime();
			frConverted = swapColorFormat(input, NV21,
					mediaFormat.getInteger(MediaFormat.KEY_COLOR_FORMAT),
					width, height);
			cDelay = System.nanoTime() - tIni;
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
			Log.d(TAG, "Encoded " + output.length + " bytes");
			return output;

		} catch (Throwable t) {
			System.out.println("catch");
			t.printStackTrace();
		}
		return null;

	}

	public byte[] swapColorFormat(byte[] frameIn, int colorFormatIn,
			int colorFormatOut, int width, int height) {

		switch (colorFormatIn) {

		case NV21:
			switch (colorFormatOut) {

			case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Planar:
				return FormatConverter.nv21toi420(frameIn, width, height);

			case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar:
				return FormatConverter.swapSPChroma(frameIn, width, height);
			}
		case YV12:
			switch (colorFormatOut) {

			case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Planar:
				return FormatConverter.swapPlanarChroma(frameIn, width, height);
			case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar:
				return FormatConverter.yv12ToNV12(frameIn, width, height);
			}
		}
		return null;
	}

}
