package com.kurento.kas.media.codecs;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import android.util.Base64;
import android.util.Log;

public class Base64Converter {

	private static final String TAG = "Converter64";

	private Byte[] createByteArray(String sdp64) {
		Byte[] dec2;
		byte[] decoded;
		decoded = Base64.decode(sdp64, Base64.DEFAULT);
		dec2 = new Byte[decoded.length + 4];
		dec2[0] = 0;
		dec2[1] = 0;
		dec2[2] = 0;
		dec2[3] = 1;
		for (int i = 0; i < decoded.length; i++) {
			dec2[i + 4] = decoded[i];
		}
		return dec2;

	}

	public ArrayList<Byte[]> sdp64ToByteArray(String sdp64) {
		ArrayList<Byte[]> csd0 = new ArrayList<Byte[]>();
		Byte[] dec2;

		while (sdp64.indexOf(',') != -1) {
			String firstPart = sdp64.substring(0, sdp64.indexOf(','));
			dec2 = createByteArray(firstPart);
			csd0.add(dec2);

			sdp64 = sdp64.substring(sdp64.indexOf(',') + 1);
		}
		dec2 = createByteArray(sdp64);

		csd0.add(dec2);
		return csd0;
	}

	public byte[] deleteHeader(Byte[] chain) {
		byte[] newChain = new byte[chain.length - 4];
		for (int i = 0; i < chain.length - 4; i++) {
			newChain[i] = chain[i + 4];
		}
		return newChain;
	}

	public String byteArrayToSdp(ArrayList<Byte[]> csd) {
		int i, size = csd.size();
		String sdp = "";
		for (i = 0; i < size; i++) {
			if (i != 0)
				sdp += ',';
			byte[] newchain = deleteHeader(csd.get(i));
			sdp += new String(Base64.encode(newchain, Base64.NO_WRAP));
		}
		System.out.println("SDP-PPS es: " + sdp);
		return sdp;
	}

	public ArrayList<Byte[]> createArrayList(byte[] extradata) {
		int pos = 0;
		int length = extradata.length;
		boolean begin = true;
		ArrayList<Byte> bytes = null;
		ArrayList<Byte[]> byteArrayList = new ArrayList();
		while (pos < length) {
			if (begin) {
				if (bytes != null && bytes.size() > 0) {
					Byte[] array = new Byte[bytes.size()];
					for (int i = 0; i < array.length; i++)
						array[i] = bytes.get(i);
					byteArrayList.add(array);
				}

				bytes = new ArrayList();
				pos += 4;
				bytes.add((byte) 0);
				bytes.add((byte) 0);
				bytes.add((byte) 0);
				bytes.add((byte) 1);
				begin = false;
			}

			if ((pos + 3 < length) && extradata[pos] == 0
					&& extradata[pos + 1] == 0 && extradata[pos + 2] == 0
					&& extradata[pos + 3] == 1) {
				begin = true;

			} else {
				bytes.add(extradata[pos]);
				pos++;
			}

		}
		if (pos == length) {
			Byte[] array = new Byte[bytes.size()];
			for (int i = 0; i < array.length; i++)
				array[i] = bytes.get(i);
			byteArrayList.add(array);

		}

		return byteArrayList;

	}

	public byte [] join(byte [] array1, byte [] array2){
		byte [] output = new byte[array1.length+array2.length];
		int len1 = array1.length;
		int len2 = array2.length;
		for(int i=0; i< len1; i++){
			output[i] = array1[i];
		}
		for(int i=0; i< len2; i++){
			output[len1+i]=array2[i];
		}
		return output;
	}
}
