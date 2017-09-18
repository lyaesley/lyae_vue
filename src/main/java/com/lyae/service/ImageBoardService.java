package com.lyae.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyae.dao.BoardDao;

@Service
public class ImageBoardService {
	@Autowired BoardDao boardDao;
}
