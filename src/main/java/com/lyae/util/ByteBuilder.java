package com.lyae.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 바이트 버퍼
 * 2017-10-19 박용서 작성
 * thread-non-safe
 * 버전 3
 */
public class ByteBuilder {
	
	// ==================================================================
	// - 선언 & 생성자
	// ==================================================================
	byte buf[];
	int wpos, rpos, size;
	final int capacity;
	final String charset;
	final byte space;
	final Map<String, SimpleDateFormat> dateMap = new HashMap<String, SimpleDateFormat>();
	final static byte BYTE_ZERO = (byte)'0';
	final static byte[] BYTES_NEW_LINE = new byte[]{'\r', '\n'};
	
	/**
	 * 바이트 빌더를 생성합니다. (UTF-8)
	 * @param capacity 공간단위
	 */
	public ByteBuilder(int capacity) {
		buf = new byte[this.capacity = size = capacity];
		wpos = rpos = 0;
		this.charset = "UTF-8";
		this.space = (byte)' ';
	}
	
	/**
	 * 바이트 빌더를 생성합니다.
	 * @param capacity 공간단위
	 * @param charset 캐릭터셋
	 */
	public ByteBuilder(int capacity, String charset) {
		buf = new byte[this.capacity = size = capacity];
		wpos = rpos = 0;
		this.charset = charset;
		this.space = (byte)' ';
	}
	
	/**
	 * 바이트 빌더를 생성합니다.
	 * @param charset 캐릭터셋
	 * @param data 데이터
	 */
	public ByteBuilder(String data, String charset) throws UnsupportedEncodingException {
		wpos = capacity = size = (buf = data.getBytes(charset)).length; 
		rpos = 0;
		this.charset = charset;
		this.space = (byte)' ';
	}
	
	
	// ==================================================================
	// - 쓰기
	// ==================================================================
	
	/**
	 * 버퍼에 순차적으로 씁니다.
	 * @param data
	 * @param offset
	 * @param len
	 */
	public ByteBuilder write(byte[] data, int offset, int len) {
		allocate(len - offset);
		System.arraycopy(data, offset, buf, wpos, len);
		wpos += len;
		return this;
	}
	/**
	 * 버퍼에 순차적으로 씁니다.
	 * @param data
	 */
	public ByteBuilder write(byte[] data) {
		return write(data, 0, data.length);
	}
	
	/**
	 * 버퍼에 순차적으로 씁니다.
	 * @param data
	 */
	public ByteBuilder write(String data) {
		try {
			write(data.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	/**
	 * 버퍼에 순차적으로 씁니다. [고정크기]
	 * @param data
	 * @param fixedSize
	 * @throws IOException
	 */
	public ByteBuilder write(String data, int fixedSize) throws IOException {
		if (data == null || data.length() == 0) {
			writeSpace(fixedSize);
			return this;
		}
		byte[] b = data.getBytes(charset);
		int fillSize = fixedSize - b.length;
		if (fillSize < 0) {
			throw new IOException("data over fixedSize : data["+data+"] fixedSize["+fixedSize+"] charset["+charset+"]");
		}
		write(b, 0, b.length);
		writeSpace(fillSize);
		return this;
	}
	
	/**
	 * 버퍼에 순차적으로 씁니다. [고정크기]
	 * @param data
	 * @param fixedSize
	 * @throws IOException
	 */
	public ByteBuilder write(int data, int fixedSize) throws IOException {
		return write(Integer.toString(data), fixedSize);
	}
	
	/**
	 * 버퍼에 순차적으로 씁니다. [고정크기]
	 * @param data
	 * @param fixedSize
	 * @throws IOException
	 */
	public ByteBuilder write(long data, int fixedSize) throws IOException {
		return write(Long.toString(data), fixedSize);
	}
	
	/**
	 * 스트림을 쓰기합니다.
	 * @param is
	 * @throws IOException
	 */
	public ByteBuilder write(InputStream is) throws IOException {
		byte[] isBuf = new byte[capacity];
		int isLen;
		while ( (isLen = is.read(isBuf)) >= 0 ) {
			write(isBuf, 0, isLen);
		}
		return this;
	}
	
	/**
	 * 파일을 읽습니다.
	 * @param is
	 * @throws IOException
	 */
	public ByteBuilder write(File file) throws IOException {
		try (InputStream is = new FileInputStream(file)) {
			byte[] isBuf = new byte[capacity];
			int isLen;
			while ( (isLen = is.read(isBuf)) >= 0 ) {
				write(isBuf, 0, isLen);
			}
		} catch (IOException e) {
			throw e;
		}
		return this;
	}
	
	/**
	 * 스트림을 쓰기합니다.
	 * @param is
	 * @throws IOException
	 */
	public ByteBuilder write(InputStream is, int limit) throws IOException {
		byte[] isBuf = new byte[capacity];
		int isLen;
		int sum = 0;
		while ( (isLen = is.read(isBuf)) >= 0 ) {
			sum+=isLen;
			
			if (sum < limit) {
				write(isBuf, 0, isLen);
			} else if (sum == limit) {
				write(isBuf, 0, isLen);
				break;
			} else if (sum > limit) {
				isLen -= (sum - limit);
				write(isBuf, 0, isLen);
				break;
			}
		}
		return this;
	}
	
	/**
	 * 버퍼에 순차적으로 해당 바이트를 채웁니다.
	 * @param fillByte
	 * @param fixedSize
	 */
	public ByteBuilder writeFill(byte fillByte, int fixedSize) {
		allocate(fixedSize);
		for (int i = 0 ; i < fixedSize ; i++) {
			buf[wpos++] = fillByte;
		}
		return this;
	}
	
	/**
	 * 공백쓰기
	 * @param fixedSize
	 */
	public ByteBuilder writeSpace(int fixedSize) {
		writeFill(space, fixedSize);
		return this;
	}
	
	/**
	 * 버퍼에 순차적으로 씁니다. [고정크기]
	 * @param data
	 * @param fixedSize
	 * @throws IOException
	 */
	public ByteBuilder writeZeroFill(int data, int fixedSize) throws IOException {
		byte[] val = Integer.toString(data).getBytes();
		writeFill(BYTE_ZERO, fixedSize - val.length);
		write(val, 0, val.length);
		return this;
	}
	
	/**
	 * 버퍼에 순차적으로 씁니다. [고정크기]
	 * @param data
	 * @param fixedSize
	 * @throws IOException
	 */
	public ByteBuilder writeZeroFill(long data, int fixedSize) throws IOException {
		byte[] val = Long.toString(data).getBytes();
		writeFill(BYTE_ZERO, fixedSize - val.length);
		write(val, 0, val.length);
		return this;
	}
	
	/**
	 * 줄바꿈 1byte [\n]
	 */
	public ByteBuilder writeLine1() {
		return write(BYTES_NEW_LINE, 0 , 1);
	}
	
	/**
	 * 줄바꿈 2byte [\r\n]
	 */
	public ByteBuilder writeLine2() {
		return write(BYTES_NEW_LINE, 0 , 2);
	}
	
	/**
	 * 데이터를 해당위치에 삽입합니다.
	 * @param data
	 * @param dataOffset
	 * @param dataLen
	 * @param insertOffset
	 */
	public ByteBuilder insert(byte[] data, int dataOffset, int dataLen, int insertOffset) {
		allocateTotal(insertOffset + dataLen);
		System.arraycopy(data, dataOffset, buf, insertOffset, dataLen);
		return this;
	}
	
	/**
	 * 데이터를 해당위치에 삽입합니다.
	 * @param data
	 * @param insertOffset
	 */
	public ByteBuilder insert(byte[] data, int offset) {
		return insert(data, 0, data.length, offset);
	}
	
	/**
	 * 데이터를 해당위치에 삽입합니다.
	 * @param data
	 * @param insertOffset
	 */
	public ByteBuilder insert(String data, int offset) {
		try {
			insert(data.getBytes(charset), offset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	/**
	 * 데이터를 해당위치에 삽입합니다.
	 * @param data
	 * @param offset
	 * @param fixedSize
	 * @throws IOException
	 */
	public ByteBuilder insert(String data, int offset, int fixedSize) throws IOException {
		if (data == null || data.length() == 0) {
			insertFill(space, offset, fixedSize);
			return this;
		}
		byte[] b = data.getBytes(charset);
		int len = b.length;
		int fillSize = fixedSize - len;
		if (fillSize < 0) {
			throw new IOException("data over fixedSize : data["+data+"] fixedSize["+fixedSize+"] charset["+charset+"]");
		}
		insert(b, 0, len, offset);
		insertSpace(offset + len, fillSize);
		return this;
	}
	
	/**
	 * 데이터를 해당위치에 삽입합니다.
	 * @param data
	 * @param os
	 * @throws IOException
	 */
	public ByteBuilder insert(String data, OffsetSize os) throws IOException {
		return insert(data, os.offset, os.size);
	}
	
	/**
	 * 데이터를 해당위치에 삽입합니다.
	 * 예) startSeq 3, endSql 5 라면
	 * 3, 4, 5 이렇게 3개를 합친영역을 잡는다.
	 * @param data
	 * @param osList
	 * @param startSeq 시작 시퀀스
	 * @param endSql 종료 시퀀스
	 * @throws IOException
	 */
	public ByteBuilder insert(String data, OffsetSize[] osList, int startSeq, int endSql) throws IOException {
		int size = 0;
		int len = endSql - startSeq;
		if (len > 0) {
			for (int i = 0 ; i <= len ; i++) {
				size += osList[startSeq + i].size;
			}
			insert(data, osList[startSeq].offset, size);
		} else {
			throw new IOException("startSeq는 endSql 보다 작아야합니다.");
		}
		return this;
	}
	
	/**
	 * 데이터를 해당위치에 삽입합니다.
	 * @param data
	 * @param os
	 * @throws IOException
	 */
	public ByteBuilder insert(int data, OffsetSize os) throws IOException {
		return insert(Integer.toString(data), os.offset, os.size);
	}
	
	/**
	 * 데이터를 해당위치에 삽입합니다.
	 * @param data
	 * @param os
	 * @throws IOException
	 */
	public ByteBuilder insert(long data, OffsetSize os) throws IOException {
		return insert(Long.toString(data), os.offset, os.size);
	}
	
	/**
	 * 버퍼에 순차적으로 씁니다. [고정크기]
	 * @param data
	 * @param fixedSize
	 * @throws IOException
	 */
	public ByteBuilder insertZeroFill(int data, OffsetSize os) throws IOException {
		byte[] val = Integer.toString(data).getBytes();
		insertFill(BYTE_ZERO, os.offset, os.size - val.length);
		insert(val, 0, val.length, os.offset + (os.size - val.length));
		return this;
	}
	
	/**
	 * 버퍼에 순차적으로 씁니다. [고정크기]
	 * @param data
	 * @param fixedSize
	 * @throws IOException
	 */
	public ByteBuilder insertZeroFill(long data, OffsetSize os) throws IOException {
		byte[] val = Long.toString(data).getBytes();
		insertFill(BYTE_ZERO, os.offset, os.size - val.length);
		return insert(val, 0, val.length, os.offset + (os.size - val.length));
	}
	
	/**
	 * 해당범위를 채웁니다.
	 * @param fillByte
	 * @param offset
	 * @param fixedSize
	 */
	public ByteBuilder insertFill(byte fillByte, int offset, int fixedSize) {
		allocateTotal(offset + fixedSize);
		for (int i = 0 ; i < fixedSize ; i++) {
			buf[offset++] = fillByte;
		}
		return this;
	}
	
	/**
	 * 해당범위를 공백으로 채웁니다.
	 * @param offset
	 * @param fixedSize
	 */
	public ByteBuilder insertSpace(int offset, int fixedSize) {
		return insertFill(space, offset, fixedSize);
	}
	
	// ==================================================================
	// - 읽기
	// ==================================================================
	
	/**
	 * 현재까지 내용을 outputStream에 적재합니다.
	 * @param os
	 * @throws IOException
	 */
	public ByteBuilder read(OutputStream os) throws IOException {
		os.write(buf, 0, wpos);
		os.flush();
		return this;
	}
	
	/**
	 * 현재까지 내용을 outputStream에 적재합니다.
	 * @param os
	 * @param limit
	 * @throws IOException
	 */
	public ByteBuilder read(OutputStream os, int limit) throws IOException {
		os.write(buf, 0, Math.min(wpos, limit));
		os.flush();
		return this;
	}
	
	/**
	 * 해당 위치만큼을 읽어옵니다.
	 * @param offset
	 * @param size
	 * @return
	 * @throws IOException
	 */
	public byte[] read(int offset, int size) throws IOException {
		int to = offset + size;
		if (wpos < (to)) {
			throw new IOException("읽기범위["+offset+" ~ "+to+"]가 전체길이["+wpos+"]를 초과합니다.");
		}
		return Arrays.copyOfRange(buf, offset, to);
	}
	
	/**
	 * 해당 위치만큼을 읽어옵니다.
	 * @param os
	 * @return
	 * @throws IOException
	 */
	public byte[] read(OffsetSize os) throws IOException {
		return read(os.offset, os.size);
	}
	
	/**
	 * 순차적으로 읽습니다.
	 * @param size
	 * @return
	 * @throws IOException
	 */
	public byte[] read(int size) throws IOException {
		byte[] rv = read(rpos, rpos + size);
		rpos += size;
		return rv;
	}
	
	/**
	 * 해당 위치만큼을 읽어옵니다.
	 * @param offset
	 * @param size
	 * @return
	 * @throws IOException
	 */
	public String readString(int offset, int size, boolean isEmptyToNull) throws IOException {
		int to = offset + size;
		if (wpos < (to)) {
			throw new IOException("읽기범위["+offset+" ~ "+to+"]가 전체길이["+wpos+"]를 초과합니다.");
		}
		
		while (to > offset) {
			byte b = buf[--to];
			if (b == space || b == 0) {
				continue;
			} else {
				return new String(buf, offset, (to - offset + 1), charset);
			}
		}
		
		return isEmptyToNull ? null : "";
	}
	
	/**
	 * 해당 위치만큼을 읽어옵니다.
	 * @param os
	 * @return
	 * @throws IOException
	 */
	public String readString(OffsetSize os, boolean isEmptyToNull) throws IOException {
		return readString(os.offset, os.size, isEmptyToNull);
	}
	
	/**
	 * 해당 위치만큼을 범위로 읽어옵니다.
	 * 예) startSeq 3, endSql 5 라면
	 * 3, 4, 5 이렇게 3개를 합친영역을 잡는다.
	 * @param osList
	 * @param startSeq
	 * @param endSql
	 * @param isEmptyToNull
	 * @return
	 * @throws IOException
	 */
	public String readString(OffsetSize[] osList, int startSeq, int endSql, boolean isEmptyToNull) throws IOException {
		int size = 0;
		int len = endSql - startSeq;
		if (len > 0) {
			for (int i = 0 ; i <= len ; i++) {
				size += osList[startSeq + i].size;
			}
			return readString(osList[startSeq].offset, size, isEmptyToNull);
		} else {
			throw new IOException("startSeq는 endSql 보다 작아야합니다.");
		}
	}
	
	/**
	 * 순차적으로 읽습니다.
	 * @param size
	 * @return
	 * @throws IOException
	 */
	public String readString(int size, boolean isEmptyToNull) throws IOException {
		String rv = readString(rpos, size, isEmptyToNull);
		rpos += size;
		return rv;
	}
	
	/**
	 * 해당 위치만큼을 읽어옵니다.
	 * @param offset
	 * @param size
	 * @return
	 * @throws IOException
	 */
	public int readInt(int offset, int size, int defaultValue) throws IOException {
		String rv = readString(offset, size, true);
		// 변환중 오류는 defaultValue 가져오기 오류는 IOException
		if (rv != null) {
			try {
				return Integer.parseInt(rv); 
			} catch (Exception e) {} // 단순 변환오류
		}
		return defaultValue;
	}
	
	/**
	 * 해당 위치만큼을 읽어옵니다.
	 * @param os
	 * @return
	 * @throws IOException
	 */
	public int readInt(OffsetSize os, int defaultValue) throws IOException {
		return readInt(os.offset, os.size, defaultValue);
	}
	
	/**
	 * 순차적으로 읽습니다.
	 * @param size
	 * @return
	 * @throws IOException
	 */
	public int readInt(int size, int defaultValue) throws IOException {
		int rv = readInt(rpos, rpos + size, defaultValue);
		rpos += size;
		return rv;
	}
	
	
	/**
	 * 해당 위치만큼을 읽어옵니다.
	 * @param offset
	 * @param size
	 * @return
	 * @throws IOException
	 */
	public long readLong(int offset, int size, long defaultValue) throws IOException {
		String rv = readString(offset, size, true);
		// 변환중 오류는 defaultValue 가져오기 오류는 IOException
		if (rv != null) {
			try {
				return Long.parseLong(rv); 
			} catch (Exception e) {} // 단순 변환오류
		}
		return defaultValue;
	}
	
	/**
	 * 해당 위치만큼을 읽어옵니다.
	 * @param os
	 * @return
	 * @throws IOException
	 */
	public long readLong(OffsetSize os, long defaultValue) throws IOException {
		return readLong(os.offset, os.size, defaultValue);
	}
	
	/**
	 * 순차적으로 읽습니다.
	 * @param size
	 * @return
	 * @throws IOException
	 */
	public long readLong(int size, long defaultValue) throws IOException {
		long rv = readLong(rpos, rpos + size, defaultValue);
		rpos += size;
		return rv;
	}
	
	/**
	 * 해당길이 까지 읽어 trim 후 입력 문자열과 같은지 확인한다.
	 * @param size
	 * @param defaultValue
	 * @return
	 * @throws IOException
	 */
	public boolean readAndMatch(int size, String matchString) throws IOException {
		return readString(size, false).equals(matchString);
	}
	
	/**
	 * 해당 사이즈까지 읽지않고 점프합니다.
	 * @param byteSize
	 * @throws IOException
	 */
	public ByteBuilder readIgnore(int byteSize) throws IOException {
		rpos += byteSize;
		if (rpos > wpos) {
			throw new IOException("범위를 벗어났습니다.\n크기["+wpos+"] 이동위치["+rpos+"]");
		}
		return this;
	}
	
	/**
	 * 일지하는 바이트까지 무시합니다.
	 * @param byteSize
	 * @throws IOException
	 */
	public ByteBuilder readIgnoreMatch(byte b) throws IOException {
		for (int i = rpos ; i < wpos ; i++) {
			if (buf[i] == b) {
				rpos = i + 1;
				return this;
			}
		}
		throw new IOException("해당 문자열을 찾을 수 없습니다.");
	}
	
	// ==================================================================
	// - 특수
	// ==================================================================
	/**
	 * 쓰기 포인터를 이동시킵니다.
	 * @param pos
	 * @throws IOException
	 */
	public ByteBuilder moveWritePointer(int pos) throws IOException {
		if (pos > 0 && pos <= size) {
			this.wpos = pos;
			return this;
		}
		throw new IOException("범위를 초과하였습니다. pos["+pos+"] size["+size+"]");
	}
	
	/**
	 * 읽기 포인터를 이동시킵니다.
	 * @param pos
	 * @throws IOException
	 */
	public ByteBuilder moveReadPointer(int pos) throws IOException {
		if (pos > 0 && pos <= size) {
			this.rpos = pos;
			return this;
		}
		throw new IOException("범위를 초과하였습니다. pos["+pos+"] size["+size+"]");
	}
	
	/**
	 * rectifyWritePointer() 후 byte [0] 값을 공백으로 치환합니다.
	 */
	public ByteBuilder rectifyWritePointerWithFillSpace() {
		rectifyWritePointer();
		rectifyFillSpace(wpos);
		return this;
	}
	
	/**
	 * 전체 프로토콜 사이즈를 설정하고 byte [0] 값을 공백으로 치환합니다.
	 * @param totalSize
	 */
	public ByteBuilder rectifyFillSpace(int totalSize) {
		allocateTotal(wpos = totalSize);
		for (int i = (wpos - 1) ; i >= 0 ; i--) {
			if (buf[i] == 0) {
				buf[i] = space;
			}
		}
		return this;
	}
	
	/**
	 * 쓰기 포인터를 교정합니다.<br>
	 * write가 아닌 insert로만 입력했다면 반드시 필요한 과정입니다.
	 */
	public ByteBuilder rectifyWritePointer() {
		for (int i = (size - 1) ; i >= 0 ; i--) {
			if (buf[i] != 0) {
				wpos = i + 1;
				return this;
			}
		}
		wpos = 0;
		return this;
	}
	
	// ==================================================================
	// - 반환
	// ==================================================================
	/**
	 * 자바 스트링 기준의 바이트를 구해옵니다.(한글/영문 상관없이 1)
	 * @return
	 */
	public int length() {
		return toString().length();
	}
	
	/**
	 * 바이트의 사이즈를 구해옵니다.(한글 : 문자셋에 따라 다른 크기)<br>
	 * (캐릭터 셋에 따라 같은 문자열이라도 다른 크기가 발생합니다.)
	 * @return
	 */
	public int lengthBytes() {
		return wpos;
	}
	
	/**
	 * 스트링으로 반환
	 */
	@Override
	public String toString() {
		try {
			return new String(buf, 0, wpos, charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 바이트로 반환
	 * @return
	 */
	public byte[] toBytes() {
		return Arrays.copyOfRange(buf, 0, wpos);
	}
	
	// ==================================================================
	// - 기타 내부 동작
	// ==================================================================
	/**
	 * 재할당 [미할당]
	 * @param appendLength
	 */
	void allocate(int appendLength) {
		allocateNeed(appendLength + wpos - size);
	}
	/**
	 * 재할당 [전체기준]
	 * @param totalSize
	 */
	void allocateTotal(int totalSize) {
		allocateNeed(totalSize - size);
	}
	/**
	 * 재할당 [직접할당]
	 * @param needSize
	 */
	void allocateNeed(int needSize) {
		if (needSize > 0) {
			int allocate = (((needSize / capacity) + 1) * capacity) + size;
			buf = Arrays.copyOf(buf, allocate);
			size = allocate;
		}
	}
	
	/**
	 * 데이트 포맷을 가져옴
	 * @param format
	 * @return
	 */
	SimpleDateFormat getDateFormat(String format) {
		SimpleDateFormat sdf = dateMap.get(format);
		if (sdf == null) {
			dateMap.put(format, (sdf = new SimpleDateFormat(format)));
		}
		return sdf;
	}
}
