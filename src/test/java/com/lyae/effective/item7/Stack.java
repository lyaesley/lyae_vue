package com.lyae.effective.item7;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.EmptyStackException;

@Data @Slf4j
public class Stack {
	private Object[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 2;

	public Stack() {
		elements = new Object[DEFAULT_INITIAL_CAPACITY];
	}

	public void push(Object e) {
		ensureCapacity();
		elements[size++] = e;
	}

	public Object pop() {
		if (size == 0) {
			throw new EmptyStackException();
		}
		Object result = elements[--size];
		elements[size] = null; // 다 쓴 참조 해제
		return result;
	}

	private void ensureCapacity() {
		if (elements.length == size) {
			elements = Arrays.copyOf(elements, 2 * size + 1);
		}

	}

	private void toStringElements() {
		for(Object e : elements) {
			System.out.println(e.toString());
		}
	}

	public static void main(String[] args) {
		Stack stack = new Stack();
		log.info("elements size 1:  {}", stack.getElements().length);

		log.info("size 1:  {}", stack.getSize());

		stack.push("a");
		log.info("size 2:  {}", stack.getSize());

		stack.push("b");
		log.info("size 3:  {}", stack.getSize());

		stack.pop();
		log.info("size 4:  {}", stack.getSize());

		stack.push("c");
		log.info("size 5:  {}", stack.getSize());

		stack.push("d");
		log.info("size 6:  {}", stack.getSize());

		log.info("elements size 2:  {}", stack.getElements().length);

		stack.toStringElements();
	}


}
