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
 * 
 * @author mparis
 * 
 */
public enum AudioCodecType {
	AMR(0, 8000, 12200, new String[]{ "AMR", "AMR-NB" }),
	MP2(1, 44100, 64000, new String[]{ "MP2", "MPA" }),
	AAC(2, 44100, 64000, new String[]{ "AAC" });
	
	private int codecID;
	private int supportedSampleRate;
	private int supportedBitRate;
	private Collection<String> collectionCodecStrings;

	private AudioCodecType(int codecID, int supportedSampleRate, int supportedBitRate, String[] codecStrings) {
		this.codecID = codecID;
		this.supportedSampleRate = supportedSampleRate;
		this.supportedBitRate = supportedBitRate;
		collectionCodecStrings = new ArrayList<String>();
		for (String s : codecStrings)
			collectionCodecStrings.add(s);
	}

	public int getCodecID() {
		return codecID;
	}

	public int getSupportedSampleRate() {
		return supportedSampleRate;
	}

	public int getSupportedBitRate() {
		return supportedBitRate;
	}

	public Collection<String> getCollectionCodecStrings() {
		return collectionCodecStrings;
	}
	
	public static int getCodecIdFromName(String codecName) throws CodecNotSupportedException {
		if (AMR.collectionCodecStrings.contains(codecName.toUpperCase()))
			return AMR.codecID;
		else if (MP2.collectionCodecStrings.contains(codecName.toUpperCase()))
			return MP2.codecID;
		else if (AAC.collectionCodecStrings.contains(codecName.toUpperCase()))
			return AAC.codecID;

		throw new CodecNotSupportedException("Codec not supported");
	}
	
	public static AudioCodecType getCodecTypeFromName(String codecName) throws CodecNotSupportedException {
		if (AMR.collectionCodecStrings.contains(codecName.toUpperCase()))
			return AMR;
		else if (MP2.collectionCodecStrings.contains(codecName.toUpperCase()))
			return MP2;
		else if (AAC.collectionCodecStrings.contains(codecName.toUpperCase()))
			return AAC;

		throw new CodecNotSupportedException("Codec not supported");
	}
	
}
