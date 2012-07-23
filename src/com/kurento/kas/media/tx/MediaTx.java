/*
 * Kurento Android Media: Android Media Library based on FFmpeg.
 * Copyright (C) 2011  Tikal Technologies
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.kurento.kas.media.tx;

import com.kurento.kas.media.Native;
import com.kurento.kas.media.codecs.AudioCodecType;
import com.kurento.kas.media.codecs.VideoCodecType;

/**
 * <p>
 * Provides static methods that map native media functions.
 * 
 * </p>
 * 
 * @author mparis
 * 
 */
public class MediaTx extends Native {

	// VIDEO
	private static native int initVideo(String outfile, int width, int height,
			int frame_rate_num, int frame_rate_den, int bit_rate, int gop_size,
			VideoCodecType videoCodecType, int payload_type);

	/**
	 * Initialize video transmission with the configuration of videoInfoTx.
	 * 
	 * @param videoInfoTx
	 * @return <0 if error.
	 */
	public static int initVideo(VideoInfoTx videoInfoTx) {
		return initVideo(videoInfoTx.getOut(), videoInfoTx.getVideoProfile()
				.getWidth(), videoInfoTx.getVideoProfile().getHeight(),
				videoInfoTx.getVideoProfile().getFrameRateNum(), videoInfoTx
						.getVideoProfile().getFrameRateDen(), videoInfoTx
						.getVideoProfile().getBitRate(), videoInfoTx
						.getVideoProfile().getGopSize(), videoInfoTx
						.getVideoProfile().getVideoCodecType(),
				videoInfoTx.getPayloadType());
	}

	/**
	 * Receive a video frame in NV21, encode it with the parameters set with
	 * {@link MediaTx#initVideo initVideo} and write to file or send it through
	 * RTP.
	 * 
	 * @param frame
	 *            video frame in NV21 format.
	 * @param width
	 *            video frame width in pixels.
	 * @param height
	 *            video frame height in pixels.
	 * @param time
	 *            Time in milliseconds when the frame is captured relative to
	 *            the first frame in the video sequence. It is used to assign a
	 *            correct pts.
	 * @return <0 if error.
	 */
	private static native int putVideoFrame(byte[] frame, int width,
			int height,
			long time);

	public static int putVideoFrame(VideoFrameTx vf) {
		return putVideoFrame(vf.getDataFrame(), vf.getWidth(), vf.getHeight(),
				vf.getTime());
	}

	/**
	 * 
	 * @return <0 if error.
	 */
	public static native int finishVideo();

	// AUDIO
	/**
	 * Returns the audio frame size (number of samples) used to encode.
	 */
	private static native int initAudio(String outfile,
			AudioCodecType audioCodectype,
			int sample_rate, int bit_rate, int payload_type);

	/**
	 * Initialize audio transmission with the configuration of audioInfoTx.
	 * 
	 * @param audioInfoTx
	 * @return <0 if error, else the audio frame size (number of samples) used
	 *         to encode.
	 */
	public static int initAudio(AudioInfoTx audioInfoTx) {
		return initAudio(audioInfoTx.getOut(), audioInfoTx.getAudioProfile()
				.getAudioCodecType(), audioInfoTx
				.getAudioProfile().getSampleRate(), audioInfoTx
				.getAudioProfile().getBitRate(), audioInfoTx.getPayloadType());
	}

	/**
	 * Receive an audio frame (set of audio samples) in PCM16, encode them with
	 * the parameters set with {@link MediaTx#initAudio initAudio} and write to
	 * file or send it through RTP.
	 * 
	 * @param in_buffer
	 * @param in_size
	 * @return
	 */
	private static native int putAudioSamples(short[] in_buffer, int in_size,
			long time);

	public static int putAudioSamples(AudioSamplesTx as) {
		return putAudioSamples(as.getDataSamples(), as.getSize(), as.getTime());
	}

	/**
	 * 
	 * @return <0 if error.
	 */
	public static native int finishAudio();

}
