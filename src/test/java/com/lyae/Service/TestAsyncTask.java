package com.lyae.Service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TestAsyncTask {

//	 @Async("executorSample")
		@Async
	    public void executorSample(String str) {
	        // LOG : 시작 입력
	        // ...
	        System.out.println("==============>>>>>>>>>>>> THREAD START");
	        // 내용
	        // 내용
	        // 내용
	        System.out.println(str);
	        try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        // LOG : 종료 입력
	        // ...
	        System.out.println("Execute method asynchronously. " + Thread.currentThread().getName());
	        System.out.println("==============>>>>>>>>>>>> THREAD END");
	    }
}
