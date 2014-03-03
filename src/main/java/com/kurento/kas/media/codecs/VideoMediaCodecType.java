package com.kurento.kas.media.codecs;

import java.util.ArrayList;

import android.media.MediaCodecInfo;
import android.media.MediaCodecList;

import com.kurento.kas.media.codecs.VideoCodecType;

public class VideoMediaCodecType {

	public static final String MIME_H264 = "video/avc";
	public static final String MIME_MPEG4 = "video/mp4v-es";
	public static final String MIME_VP8 = "video/x-vnd.on2.vp8";
	public static final String MIME_H263 = "video/3gpp";

	public static String getVideoMediaCodecType(VideoCodecType videoCodecType) {
		if (videoCodecType == VideoCodecType.H264)
			return MIME_H264;
		if (videoCodecType == VideoCodecType.MPEG4)
			return MIME_MPEG4;
		if (videoCodecType == VideoCodecType.H263)
			return MIME_H263;

		return null;
	}

	public static ArrayList<String> getCodecList() {

		ArrayList<String> auxList = new ArrayList();

		int numCodecs = MediaCodecList.getCodecCount();

		for (int i = 0; i < numCodecs; i++) {
			MediaCodecInfo info = MediaCodecList.getCodecInfoAt(i);
			if (!info.isEncoder()) {
				continue;
			}

			String[] types = info.getSupportedTypes();

			for (int j = 0; j < types.length; j++) {
				if (types[j].startsWith("video"))
					auxList.add(types[j]);
			}

		}
		return auxList;
	}

}
