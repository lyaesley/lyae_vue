package com.lyae.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 결과값
 * @author		박용서
 * @since		2017. 11. 2.
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class Result<T> {
	String code;
	String message;
	T data;
}
