package com.lyae.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyae.dao.BoardDao;

@Service
public class BoardService {
	@Autowired BoardDao boardDao;
	
	public void abc()	{
		System.out.println("테스트 실행");
	}
}
