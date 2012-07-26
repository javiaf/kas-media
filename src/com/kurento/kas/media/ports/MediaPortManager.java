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

package com.kurento.kas.media.ports;

import com.kurento.kas.media.Native;

public class MediaPortManager extends Native {

//	/**
//	 * Retrive a free local port for audio.
//	 * 
//	 * @return the local port taken.
//	 */
//	public static int takeAudioLocalPort() {
//		return takeAudioLocalPort(-1);
//	}
//
//	/**
//	 * Try to retrieve the audioPort local port for audio.
//	 * 
//	 * @param audioPort
//	 * @return the local port taken.
//	 */
//	public static native int takeAudioLocalPort(int audioPort);
//
//	/**
//	 * Indicate that the audio local port taken by
//	 * {@link MediaPortManager#takeAudioLocalPort takeAudioLocalPort} will not
//	 * be used.
//	 * 
//	 * @return the remaining processes that are using the audio port.
//	 */
//	public static native int releaseAudioLocalPort();
//
//	public static int takeVideoLocalPort() {
//		return takeVideoLocalPort(-1);
//	}
//
//	public static native int takeVideoLocalPort(int videoPort);
//
//	/**
//	 * Indicate that the video local port taken by
//	 * {@link MediaPortManager#takeAudioLocalPort takeAudioLocalPort} will not
//	 * be used.
//	 * 
//	 * @return the remaining processes that are using the video port.
//	 */
//	public static native int releaseVideoLocalPort();

	// ////////////////////////////////////////////////////////////////////

	public static MediaPort takeMediaPort() {
		return takeMediaPort(0);
	}

	public static MediaPort takeMediaPort(int port) {
		long mp = takeMediaPortNative(port);
		return new MediaPort(mp);
	}

	public static int releaseMediaPort(MediaPort mediaPort) {
		return releaseMediaPortNative(mediaPort.getSelf());
	}

	private static native long takeMediaPortNative(int port);

	private static native int releaseMediaPortNative(long mediaPortRef);

}
