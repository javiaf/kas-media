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

package com.kurento.kas.media.codecs;

import java.util.ArrayList;
import java.util.Collection;

import com.kurento.kas.media.exception.CodecNotSupportedException;

/**
 * Supported video codecs.
 */
public enum VideoCodecType {
	H264(0, new String[]{ "H264" }),
	MPEG4(1, new String[]{ "MPEG4", "MP4V-ES" }),
	H263(2, new String[]{ "H263", "H263-1998", "H263-2000" });
	
	private int codecID;
	private Collection<String> collectionCodecStrings;
	
	//private int[] supportedBitrates
	//private int[] supportedFramerates

	private VideoCodecType(int codecID, String[] codecStrings) {
		this.codecID = codecID;
		collectionCodecStrings = new ArrayList<String>();
		for (String s : codecStrings)
			collectionCodecStrings.add(s);
	}

	public int getCodecID() {
		return codecID;
	}

	public Collection<String> getCollectionCodecStrings() {
		return collectionCodecStrings;
	}
	
	public static int getCodecIdFromName(String codecName) throws CodecNotSupportedException {
		if (H264.collectionCodecStrings.contains(codecName.toUpperCase()))
			return H264.codecID;
		else if (MPEG4.collectionCodecStrings.contains(codecName.toUpperCase()))
			return MPEG4.codecID;
		else if (H263.collectionCodecStrings.contains(codecName.toUpperCase()))
			return H263.codecID;

		throw new CodecNotSupportedException("Codec not supported");
	}
	
	public static VideoCodecType getCodecTypeFromName(String codecName) throws CodecNotSupportedException {
		if (H264.collectionCodecStrings.contains(codecName.toUpperCase()))
			return H264;
		else if (MPEG4.collectionCodecStrings.contains(codecName.toUpperCase()))
			return MPEG4;
		else if (H263.collectionCodecStrings.contains(codecName.toUpperCase()))
			return H263;

		throw new CodecNotSupportedException("Codec not supported");
	}
	
}
