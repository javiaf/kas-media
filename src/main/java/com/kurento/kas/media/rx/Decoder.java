package com.kurento.kas.media.rx;

import java.nio.ByteBuffer;

import com.kurento.kas.media.codecs.FormatConverter;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodec.CryptoException;
import android.media.MediaFormat;
import android.util.Log;

public class Decoder {

	private static final long TIMEOUT_US = 10000;
	private static final String TAG = "MediaDecoder";

	private MediaCodec mediaCodec;
	private MediaFormat mediaFormat;
	private int width;
	private int height;
	private int stride;

	public Decoder(String mimetype, int width, int height) {

		this.width = width;
		this.height = height;
		this.stride = width;
		String codecName = chooseStdCodec(mimetype);
		codecName = "";
		int colorFormat = MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Planar;
		if (codecName != null && !codecName.equals("")) {
			mediaCodec = MediaCodec.createByCodecName(codecName);
		} else {
			mediaCodec = MediaCodec.createDecoderByType(mimetype);
		}

		mediaFormat = MediaFormat.createVideoFormat(mimetype, width, height);

		/* In case of having Codec specific data we should add as below, 
		 * being csd0 and csd1 2 different byte arrays:
		 * 
		 * mediaFormat.setByteBuffer("csd-0", ByteBuffer.wrap(csd0)); 
		 * mediaFormat.setByteBuffer("csd-1", ByteBuffer.wrap(csd1));
		 * 
		 */

		mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, colorFormat);
		mediaCodec.configure(mediaFormat, null, null, 0);
		mediaCodec.start();

	}

	/*
	 * Use default software decoders implemented by Google
	 * */
	
	public String chooseStdCodec(String mimetype) {
		if (mimetype.equals("video/avc"))
			return "OMX.google.h264.decoder";
		if (mimetype.equals("video/3gpp"))
			return "OMX.google.h263.decoder";
		if (mimetype.equals("video/mp4v-es"))
			return "OMX.google.mpeg4.decoder";
		return "";
	}

	public int[] putVideoFrame(byte[] frame) {

		ByteBuffer[] codecInputBuffers, codecOutputBuffers;
		ByteBuffer inputBuffer, outputBuffer;
		int inputBufferIndex, outputBufferIndex;
		byte[] output, frConv;
		int[] rgbOutput;

		Log.d(TAG, "Frame arrived to decoder: " + frame.length + " bytes");
		codecInputBuffers = mediaCodec.getInputBuffers();
		codecOutputBuffers = mediaCodec.getOutputBuffers();
		inputBufferIndex = mediaCodec.dequeueInputBuffer(-1);

		if (inputBufferIndex >= 0) {
			inputBuffer = codecInputBuffers[inputBufferIndex];
			inputBuffer.clear();
			inputBuffer.put(frame);

			try {
				mediaCodec.queueInputBuffer(inputBufferIndex, 0, frame.length,
						0, 0);

			} catch (CryptoException ce) {
				int errorCode = ce.getErrorCode();

			}
		}

		MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();

		outputBufferIndex = -1;
		if (outputBufferIndex < 0) {

			outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo,
					TIMEOUT_US);

			if (outputBufferIndex < 0) {
				switch (outputBufferIndex) {
				case MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED:
					Log.d(TAG, "Output Buffers Changed");
					break;
				case MediaCodec.INFO_OUTPUT_FORMAT_CHANGED:
					MediaFormat oFormat = mediaCodec.getOutputFormat();
					Log.d(TAG, "Output Format Changed " + oFormat.toString());
					stride = oFormat.getInteger("stride");
					break;
				case MediaCodec.INFO_TRY_AGAIN_LATER:
					Log.d(TAG, "Timeout receiving Output Buffer. Try Again!");
					break;
				default:
					Log.d(TAG, "No Output Buffer available");
				}
			} else {
				outputBuffer = codecOutputBuffers[outputBufferIndex];
				Log.d(TAG, "Decoded Frame size= " + bufferInfo.size);
				output = new byte[bufferInfo.size];
				outputBuffer.get(output);
				outputBuffer.clear();
				mediaCodec.releaseOutputBuffer(outputBufferIndex, false);

				frConv = FormatConverter.qcom_convert(output, width, height, stride);

				rgbOutput = FormatConverter.convertYUV420_NV21toARGB8888(
						frConv, width, height);
				return rgbOutput;
			}
		}
		rgbOutput = new int[1];
		rgbOutput[0] = -1;
		return rgbOutput;
	}

}
