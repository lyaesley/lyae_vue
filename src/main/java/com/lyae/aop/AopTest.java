package com.lyae.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopTest {
	
	@Pointcut("execution(* com.lyae.controller.ImageBoardController.*(..))")
	public void imageBoard(){System.out.println("imageBoard() called");}

	@AfterReturning(pointcut = "imageBoard()")
	public void test() {
		System.out.println("aop 테스트");
	}
	
	@Before("execution(* com.lyae.controller.BoardController.*(..))")
	public void beforeAdvice() {
		System.out.println("beforeAdvice() called");
	}
	
	@After("execution(* com.lyae.controller.BoardController.*(..))")
	public void afterAdvice() {
		System.out.println("afterAdvice() called");
	}
	
	@Around("execution(* com.lyae.controller.BoardController.*(..))")
	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
		// before advice
		System.out.println("aroundAdvice(): before");
		
		// proceed() 메서드 호출 => 실제 비즈니스
		// 비즈니스가 리턴하는 객체가 있을 수 있으므로 Obejct로 받아준다.
		Object result = pjp.proceed();
		
		// after advice
		System.out.println("aroundAdvice(): after");
		
		return result;
	}
}
