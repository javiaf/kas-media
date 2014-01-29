package com.kurento.kas.media.codecs;

import com.kurento.kas.media.codecs.VideoCodecType;

public class VideoMediaCodecType{


	public static final String MIME_H264 = "video/avc";
	public static final String MIME_MPEG4 = "video/mp4v-es";
	public static final String MIME_VP8 = "video/x-vnd.on2.vp8";
	public static final String MIME_H263 = "video/3gpp";

	public static String getVideoMediaCodecType(VideoCodecType videoCodecType){
		if (videoCodecType == VideoCodecType.H264)
			return MIME_H264;
		if (videoCodecType == VideoCodecType.MPEG4)
			return MIME_MPEG4;
		if (videoCodecType == VideoCodecType.H263)
			return MIME_H263;
		
		return null;
	}

}

