package com.lyae;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import com.lyae.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@PropertySource("classpath:config.properties")
public class Application implements CommandLineRunner{

	@Autowired BoardService boardService;	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		log.info("서버 시작");
	}
	
	@PreDestroy
	public void shutdown() {
		log.info("서버 종료");
	}
}
