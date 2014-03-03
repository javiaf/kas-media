package com.kurento.kas.media.rx;

import java.nio.ByteBuffer;
import android.media.MediaCodec;
import android.media.MediaCodec.CryptoException;
import android.media.MediaFormat;
import android.util.Log;

public class Decoder {

	private MediaCodec mediaCodec;
	private MediaFormat mediaFormat;

	public Decoder(String codecName, String mimeType, int colorFormat,
			int width, int height) {

		if (codecName != null && !codecName.equals("")) {
			mediaCodec = MediaCodec.createByCodecName(codecName);
		} else {
			mediaCodec = MediaCodec.createDecoderByType(mimeType);
		}
		mediaFormat = MediaFormat.createVideoFormat(mimeType, width, height);
		mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, colorFormat);
		mediaCodec.configure(mediaFormat, null, null, 0);
		mediaCodec.start();

	}

	private static final long TIMEOUT_US = 10000;
	private static final String TAG = "Decoder";

	public byte[] putVideoFrame(byte[] frame) {
		ByteBuffer[] codecInputBuffers = mediaCodec.getInputBuffers();
		ByteBuffer[] codecOutputBuffers = mediaCodec.getOutputBuffers();
		ByteBuffer inputBuffer, outputBuffer;
		int inputBufferIndex, outputBufferIndex;
		byte[] output;

		inputBufferIndex = mediaCodec.dequeueInputBuffer(TIMEOUT_US);
		if (inputBufferIndex >= 0) {
			inputBuffer = codecInputBuffers[inputBufferIndex];
			inputBuffer.clear();
			inputBuffer.put(frame);
			try {
				mediaCodec.queueInputBuffer(inputBufferIndex, 0, frame.length,
						0, 0);
			} catch (CryptoException ce) {
				int errorCode = ce.getErrorCode();

				return null;
			}
		}

		MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();

		outputBufferIndex = -1;
		while (outputBufferIndex < 0) {
			outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, -1);
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
					Log.d(TAG, "Timeout receiving Output Buffer. Try Again!");
					break;
				default:
					Log.d(TAG, "No Output Buffer available");
				}
			}
		}
		outputBuffer = codecOutputBuffers[outputBufferIndex];
		output = new byte[bufferInfo.size];
		outputBuffer.get(output);

		mediaCodec.releaseOutputBuffer(outputBufferIndex, false);
		return output;
	}

}
