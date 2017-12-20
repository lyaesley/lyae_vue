package com.lyae;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lyae.service.MovieService;
import com.lyae.service.SoccerService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {          

	@Autowired MovieService movieService;
	@Autowired SoccerService soccerService;

//	@Test
	public void testapiMovieSearch() {
		movieService.testapiMovieSearch();
	}

	@Test
	public void seasonsList() {
		soccerService.test_seasonsList();
	}
}
