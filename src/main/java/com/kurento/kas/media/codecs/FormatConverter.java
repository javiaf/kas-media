package com.kurento.kas.media.codecs;

public class FormatConverter {

	private static final int TILE_WIDTH = 64;
	private static final int TILE_HEIGHT = 32;
	private static final int TILE_SIZE = (TILE_WIDTH * TILE_HEIGHT);
	private static final int TILE_GROUP_SIZE = (4 * TILE_SIZE);

	private static long tile_pos(long bx, long by, long nbx, long nby) {

		long base; // Number of memory block of the first block in row by
		long offs; // Offset from base

		if ((by & 1) == 0) {
			base = by * nbx;
			if ((nby & 1) != 0 && (by == (nby - 1))) {
				// Last row when nby is odd
				offs = bx;
			} else {
				offs = bx + ((bx + 2) & ~3);
			}
		} else {
			base = (by & (~1)) * nbx + 2;
			offs = bx + (bx & ~3);
		}

		return base + offs;
	}

	/*
	 * Change QOMX_COLOR_FormatYUV420PackedSemiPlanar64x32Tile2m8ka (colorFormat
	 * = 2141391875) to NV21.
	 */
	public static byte[] qcom_convert(byte[] src, int width, int height,
			int strbits) {
		int stride = width;
		int frameSize = width * height;

		byte[] out = new byte[src.length];
		long tile_w = (width - 1) / TILE_WIDTH + 1;
		long tile_w_align = (tile_w + 1) & ~1;

		long tile_h_luma = (height - 1) / TILE_HEIGHT + 1;
		long tile_h_chroma = (height / 2 - 1) / TILE_HEIGHT + 1;

		long luma_size = tile_w_align * tile_h_luma * TILE_SIZE;

		if ((luma_size % TILE_GROUP_SIZE) != 0)
			luma_size = (((luma_size - 1) / TILE_GROUP_SIZE) + 1)
					* TILE_GROUP_SIZE;

		for (long y = 0; y < tile_h_luma; y++) {
			int row_width = width;
			for (long x = 0; x < tile_w; x++) {
				/* luma source pointer for this tile */
				long srcLuma = tile_pos(x, y, tile_w_align, tile_h_luma)
						* TILE_SIZE;
				/* chroma source pointer for this tile */
				long srcChroma = tile_pos(x, y / 2, tile_w_align, tile_h_chroma)
						* TILE_SIZE;
				if ((y & 1) != 0)
					// src_chroma += TILE_SIZE/2;
					srcChroma += TILE_SIZE / 2;

				/* account for right columns */
				int tile_width = row_width;
				if (tile_width > TILE_WIDTH)
					tile_width = TILE_WIDTH;

				/* account for bottom rows */
				int tile_height = height;
				if (tile_height > TILE_HEIGHT)
					tile_height = TILE_HEIGHT;

				/* dest luma memory index for this tile */
				long luma_idx = y * TILE_HEIGHT * stride + x * TILE_WIDTH;

				/* dest chroma memory index for this tile */
				/* XXX: remove divisions */
				long chroma_idx = (luma_idx / stride) * stride / 2
						+ (luma_idx % stride);

				tile_height /= 2; // we copy 2 luma lines at once
				while (tile_height > 0) {

					System.arraycopy(src, (int) srcLuma, out, (int) luma_idx,
							tile_width);
					srcLuma += TILE_WIDTH;
					luma_idx += stride;

					System.arraycopy(src, (int) srcLuma, out, (int) luma_idx,
							tile_width);

					srcLuma += TILE_WIDTH;
					luma_idx += stride;

					System.arraycopy(src, (int) (luma_size + srcChroma),
							out, (int) (frameSize + chroma_idx), tile_width);

					srcChroma += TILE_WIDTH;
					chroma_idx += stride;

					tile_height--;

				}
				row_width -= TILE_WIDTH;
			}
			height -= TILE_HEIGHT;
		}
		return out;

	}

	public static void convertYUV420SPtoARGB8888(byte[] data, int[] pixels,
			int width, int height) {
		int size = width * height;
		int offset = size;
		int u, v, y1, y2, y3, y4;

		// i along Y and the final pixels
		// k along pixels U and V
		for (int i = 0, k = 0; i < size; i += 2, k += 2) {
			y1 = data[i] & 0xff;
			y2 = data[i + 1] & 0xff;
			y3 = data[width + i] & 0xff;
			y4 = data[width + i + 1] & 0xff;
			v = data[offset + k + 1] & 0xff;
			u = data[offset + k] & 0xff;
			v = v - 128;
			u = u - 128;

			pixels[i] = convertYUVtoARGB(y1, u, v);
			pixels[i + 1] = convertYUVtoARGB(y2, u, v);
			pixels[width + i] = convertYUVtoARGB(y3, u, v);
			pixels[width + i + 1] = convertYUVtoARGB(y4, u, v);

			if (i != 0 && (i + 2) % width == 0)
				i += width;
		}
	
	}

	private static int convertYUVtoARGB(int y, int u, int v) {
		int r = y + (int) (1.772f * v);
		int g = y - (int) (0.344f * v + 0.714f * u);
		int b = y + (int) (1.402f * u);
		r = r > 255 ? 255 : r < 0 ? 0 : r;
		g = g > 255 ? 255 : g < 0 ? 0 : g;
		b = b > 255 ? 255 : b < 0 ? 0 : b;
		return 0xff000000 | (r << 16) | (g << 8) | b;
	}

	/*
	 * First: Copy component Luma Y Second: This function is used to swap
	 * semiplanar U and V interleaved to change from NV21 to NV12 or NV12 to
	 * NV21.
	 */
	public static byte[] swapSPChroma(byte[] frameIn, int width, int height) {
		byte[] frameOut = new byte[frameIn.length];
		int frameSize = width * height;
		int qFrameSize = frameSize / 4;
		System.arraycopy(frameIn, 0, frameOut, 0, frameSize);

		for (int i = 0; i < qFrameSize; i++) {
			frameOut[frameSize + i * 2] = frameIn[frameSize + i * 2 + 1];
			frameOut[frameSize + i * 2 + 1] = frameIn[frameSize + i * 2];
		}
		return frameOut;
	}

	/*
	 * First: Copy component Luma Y Second: Get components Chroma U and V. NV21
	 * is semiplanar format and its components are in same plane interleaved. In
	 * I420 are separated in diferent planes separated by qFrameSize.
	 */
	public static byte[] i420ToNV21(byte[] in, int width, int height) {
		int frameSize = width * height;
		int qFrameSize = frameSize / 4;
		byte[] out = new byte[in.length];

		System.arraycopy(in, 0, out, 0, frameSize);

		for (int i = 0; i < qFrameSize; i++) {
			out[frameSize + i * 2 + 1] = in[frameSize + i + qFrameSize];
			out[frameSize + i * 2] = in[frameSize + i];
		}
		return out;
	}

	/*
	 * First: Copy component Luma Y Second: Get components Chroma U and V. NV21
	 * is semiplanar format and its components are in same plane interleaved. In
	 * I420 are separated in diferent planes separated by qFrameSize.
	 */
	public static byte[] nv21toi420(byte[] in, int width, int height) {
		int frameSize = width * height;
		int qFrameSize = frameSize / 4;
		byte[] out = new byte[in.length];
		System.arraycopy(in, 0, out, 0, frameSize);
		for (int i = 0; i < qFrameSize; i++) {
			out[frameSize + i] = in[frameSize + i * 2];
			out[frameSize + qFrameSize + i] = in[frameSize + i * 2 + 1];
		}
		return out;
	}

	/*
	 * First: Copy component Luma Y Second: Get components Chroma U and V. YV12
	 * is planar format and its components are in diferent planes separated by
	 * qFrameSize, in I420 too, but U and V have to be swapped.
	 */

	public static byte[] swapPlanarChroma(byte[] frameIn, int width, int height) {
		byte[] frameOut = new byte[frameIn.length];
		int frameSize = width * height;
		int qFrameSize = frameSize / 4;
		System.arraycopy(frameIn, 0, frameOut, 0, frameSize);
		System.arraycopy(frameIn, frameSize, frameOut, frameSize + qFrameSize,
				qFrameSize);
		System.arraycopy(frameIn, frameSize + qFrameSize, frameOut, frameSize,
				qFrameSize);
		return frameOut;
	}

	/*
	 * First: Copy component Luma Y Second: Get components Chroma U and V. YV12
	 * is planar format and its components are in diferent planes separated by
	 * qFrameSize. In NV12 are together interleaved
	 */

	public static byte[] yv12ToNV12(byte[] frameIn, int width, int height) {
		byte[] frameOut = new byte[frameIn.length];
		int frameSize = width * height;
		int qFrameSize = frameSize / 4;
		System.arraycopy(frameIn, 0, frameOut, 0, frameSize); // Y

		for (int i = 0; i < qFrameSize; i++) {
			frameOut[frameSize + i * 2] = frameIn[frameSize + i + qFrameSize];
			frameOut[frameSize + i * 2 + 1] = frameIn[frameSize + i];
		}
		return frameOut;
	}
}
