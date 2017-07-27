package com.ljy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ljy.dao.BoardDao;

@Service
public class BoardService {
	@Autowired BoardDao boardDao;
}
