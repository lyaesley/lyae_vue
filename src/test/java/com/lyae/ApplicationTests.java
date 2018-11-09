package com.lyae;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lyae.Service.TestAsyncTask;
import com.lyae.service.MovieService;
import com.lyae.service.SoccerService;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class ApplicationTests {          

	@Autowired MovieService movieService;
	@Autowired SoccerService soccerService;
	@Autowired TestAsyncTask a;



//	@Test
	public void seasonsList() {
		soccerService.test_seasonsList();
	}
	
//	@Test
	public void async() throws InterruptedException {
		long st = System.currentTimeMillis();
		System.out.println("시작시간 : " +st );
		
		a.executorSample("111");
		a.executorSample("222");
		a.executorSample("333");
		a.executorSample("444");
		a.executorSample("555");
		a.executorSample("666");
		a.executorSample("777");
		a.executorSample("888");
		a.executorSample("999");
		a.executorSample("000");
		
		a.executorSample("111");
		a.executorSample("222");
		a.executorSample("333");
		a.executorSample("444");
		a.executorSample("555");
		a.executorSample("666");
		a.executorSample("777");
		a.executorSample("888");
		a.executorSample("999");
		a.executorSample("000");
		
		a.executorSample("111");
		a.executorSample("222");
		a.executorSample("333");
		a.executorSample("444");
		a.executorSample("555");
		a.executorSample("666");
		a.executorSample("777");
		a.executorSample("888");
		a.executorSample("999");
		a.executorSample("000");

		long en = System.currentTimeMillis();
		System.out.println("종료시간 : " +en );
		
		long res = en - st;

		System.out.println("걸린 시간: " + res + " 밀리초");

		Thread.sleep(15*1000);
	}
	
//	@Test
	public void testapiMovieSearch() {
		movieService.testapiMovieSearch();
	}
}
