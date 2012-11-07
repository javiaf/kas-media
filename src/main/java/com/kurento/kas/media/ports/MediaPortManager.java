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

	/**
	 * Retrieve a free local port.
	 * 
	 * @return a {@link MediaPort} related with the local port binded.
	 */
	public static MediaPort takeMediaPort() {
		return takeMediaPort(0);
	}

	/**
	 * Retrieve a local port.
	 * 
	 * @param port
	 *            to be binded.
	 * @returna {@link MediaPort} related with the local port binded.
	 */
	public static MediaPort takeMediaPort(int port) {
		long mp = takeMediaPortNative(port);
		return new MediaPort(mp);
	}

	/**
	 * Release a {@link MediaPort}.
	 * 
	 * @param mediaPort
	 *            to be released
	 * @return the number of users of this the mediaPort.
	 */
	public static int releaseMediaPort(MediaPort mediaPort) {
		return releaseMediaPortNative(mediaPort.getSelf());
	}

	private static native long takeMediaPortNative(int port);

	private static native int releaseMediaPortNative(long mediaPortRef);

}
