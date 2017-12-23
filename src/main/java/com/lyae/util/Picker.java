package com.lyae.util;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.lyae.util.WebUtil.WebResult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Picker {
	final Object val;
	public Picker(Object val) {
		this.val = val;
	}
	
	public Picker get(int idx) {
		return new Picker(((List<Object>) val).get(idx));
	}
	
	public Picker get(String name) {
		return new Picker(((Map<String, Object>) val).get(name));
	}
	
	public String toString() {
		return val.toString();
	}
	
	/**
	 * 
	 * @author		이준영
	 * @since		2017. 12. 20.
	 */
	@Data @AllArgsConstructor @NoArgsConstructor
	public class WebResult<T> {
		int status;
		String message;
		T data;
		public boolean isSuccess() {
			return status == 200;
		}
	}
	
}
