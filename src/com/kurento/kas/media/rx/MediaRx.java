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

package com.kurento.kas.media.rx;

import com.kurento.kas.media.Native;
import com.kurento.kas.media.ports.MediaPort;

public class MediaRx extends Native {

	public static final int DEFAULT_MAX_DELAY = 200;

	/**
	 * Start video reception from RTP based on the sdp arg and send decoded
	 * video frames (RGB32) to videoReceiver.
	 * 
	 * @param sdp
	 *            SDP to indicate the expected video RTP stream.
	 * @param maxDelay
	 *            if > 0, enable reordering received RTP packets waiting at most
	 *            maxDelay milliseconds.
	 * @param videoReceiver
	 *            object that receive decoded video frames.
	 * 
	 * @return <0 if error.
	 */
	private static native int startVideoRx(long videoMediaPortRef, String sdp,
			int maxDelay, VideoRx videoReceiver);

	public static int startVideoRx(MediaPort videoMediaPort, String sdp,
			int maxDelay, VideoRx videoReceiver) {
		return startVideoRx(videoMediaPort.getSelf(), sdp, maxDelay,
				videoReceiver);
	}

	/**
	 * Stop video reception.
	 * 
	 * @return <0 if error.
	 */
	public static native int stopVideoRx();

	/**
	 * Start audio reception from RTP based on the SDP sdp_str arg and send
	 * decoded audio frames (PCM16 samples) to audioReceiver.
	 * 
	 * @param sdp
	 *            SDP to indicate the expected audio RTP stream.
	 * @param maxDelay
	 *            if > 0, enable reordering received RTP packets waiting at most
	 *            maxDelay milliseconds.
	 * @param audioReceiver
	 *            object that receive decoded audio frames.
	 * @return
	 */
	private static native int startAudioRx(long audioMediaPortRef, String sdp,
			int maxDelay, AudioRx audioReceiver);

	public static int startAudioRx(MediaPort audioMediaPort, String sdp,
			int maxDelay, AudioRx audioReceiver) {
		return startAudioRx(audioMediaPort.getSelf(), sdp, maxDelay,
				audioReceiver);
	}

	/**
	 * Stop audio reception.
	 * 
	 * @return <0 if error.
	 */
	public static native int stopAudioRx();

}
