package com.lyae.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopTest {
	
	@Pointcut("execution(* com.lyae.controller.ImageBoardController.*(..))")
	public void imageBoard(){
		//@Pointcut 구현체는 빈값으로 설정, 작업을 하더라도 실행되지 않는다.
		/**
		 * 포인트컷 지시자의 종류
		 * execution()	: 접근제한자, 리턴타입, 인자타입, 클래스/인터페이스, 메소드명, 파라미터타입, 예외타입 등을 전부 조합가능한 가장 세심한 지정자
		 *				리턴타입 패키지경로 클래스명 메소드명(매개변수)
		 *
		 * within()		: execution 지정자에서 클래스/인터페이스까지만 적용된 경우
		 * 				즉, 클래스 혹은 인터페이스 단위까지만 범위 지정이 가능하다.
		 * 				ex) within(com.blogcode.service.*) : service 패키지 아래의 클래스와 인터페이스가 가진 모든 메소드
		 * 				ex) within(com.blogcode.service..) : service 아래의 모든 *하위패키지까지** 포함한 클래스와 인터페이스가 가진 메소드
		 *
		 * @within()	: 주어진 어노테이션을 사용하는 타입으로 선언된 메소드
		 *
		 *  this()		: 타겟 메소드가 지정된 빈 타입의 인스턴스인 경우
		 *
		 * bean()		: bean이름으로 포인트컷
		 *
		 * args()		: 메소드의 인자가 타겟 명세에 포함된 타입일 경우
		 * 				ex) args(java.io.Serializable) : 하나의 파라미터를 갖고, 그 인자가 Serializable 타입인 모든 메소드
		 *
		 * @args()		: 메소드의 인자가 타겟 명세에 포함된 어노테이션 타입을 갖는 경우
		 * 				ex) @args(com.blogcode.session.User) : 하나의 파라미터를 갖고, 그 인자의 타입이 @User 어노테이션을 갖는 모든 메소드 (@User User user 같이 인자 선언된 메소드)
		 *
		 * target()		: this와 유사하지만 빈 타입이 아닌 타입의 인스턴스인 경우
		 *
		 * @target()	: 타겟 메소드를 실행하는 객체의 클래스가 타겟 명세에 지정된 타입의 어노테이션이 있는 경우
		 *
		 * @annotation	: 타겟 메소드에 특정 어노테이션이 지정된 경우
		 * 				ex) @annotation(org.springframework.transaction.annotation.Transactional) : Transactional 어노테이션이 지정된 메소드 전부
		 *
		 */

		System.out.println("imageBoard() called"); //이 코드는 실행 되지 않음.
	}

	@Before("execution(* com.lyae.controller.BoardController.*(..))") // 메소드 실행 전에 동작
	public void beforeAdvice() {
		System.out.println("beforeAdvice() called");
	}
	
	@After("execution(* com.lyae.controller.BoardController.*(..))")
	public void afterAdvice() {
		System.out.println("afterAdvice() called");
	}

	@AfterReturning(pointcut = "imageBoard()") //메소드가 정상적으로 실행된 후에 동작
	public void afterReturningAdvice() {
		//imageBoard() 메서드에 설정된 @Pointcut("execution(* com.lyae.controller.ImageBoardController.*(..))")에 포인트컷을 설정함.
		System.out.println("aop 테스트");
	}

	@AfterThrowing(pointcut = "imageBoard()") //예외가 발생한 후에 동작
	public void afterThrowingAdvice() {
		System.out.println("aop 테스트");
	}


	@Around("execution(* com.lyae.controller.BoardController.*(..))") //메소드 호출 이전, 이후, 예외발생 등 모든 시점에서 동작
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
