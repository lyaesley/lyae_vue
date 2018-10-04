package com.lyae.util;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * OffsetSize 객체
 * 2017-02-26 박용서 작성 : 
 */
@Data @Getter @ToString
public class OffsetSize {
	final int offset;
	final int size;
	
	private OffsetSize(int offset, int size) {
		this.offset = offset;
		this.size = size;
	}
	
	/**
	 * 오프셋을 쉬프트하여 반환합니다.
	 * @return
	 */
	public OffsetSize shiftOffset(int shiftSize) {
		return new OffsetSize(offset + shiftSize, size);
	}
	
	public static OffsetSize make(int offset, int size) {
		return new OffsetSize(offset, size);
	}
	
	public static OffsetSize[] makeOffsetSizeArray(int... fixedSizeList) {
		int i = 0, offset = 0;
		OffsetSize[] list = new OffsetSize[fixedSizeList.length + 1];
		list[i++] = null;
		
		for (int size : fixedSizeList) {
			list[i++] = new OffsetSize(offset, size);
			offset += size;
		}
		
		return list;
	}
	
	public static String toStringOffsetSizeList(OffsetSize[] osList) {
		StringBuilder sb = new StringBuilder(osList.length * 6);
		int i = 0;
		for (OffsetSize os : osList) {
			if (i == 0) { i++; sb.append('\n'); continue; }
			sb.append(String.format("%3s.\t%d\t%d\n", i, os.size, os.offset));
			i++;
		}
		return sb.toString();
	}
}
