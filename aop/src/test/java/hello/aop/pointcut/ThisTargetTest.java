package hello.aop.pointcut;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import hello.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// @SpringBootTest(properties = "spring.aop.proxy-target-class=false")
@SpringBootTest(properties = "spring.aop.proxy-target-class=true")

@Import({ThisTargetTest.ThisTargetAspect.class})
public class ThisTargetTest {

	@Autowired
	MemberService memberService;

	@Test
	void success() {
		log.info("memberService Proxy = {}",memberService.getClass());
		memberService.hello("helloA");
	}

	@Aspect
	@Slf4j
	static class ThisTargetAspect {
		@Around("this(hello.aop.member.MemberService)")
		public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
			log.info("[this-interface] {}",joinPoint.getSignature());
			return joinPoint.proceed();
		}

		@Around("target(hello.aop.member.MemberService)")
		public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
			log.info("[target-interface] {}",joinPoint.getSignature());
			return joinPoint.proceed();
		}

		@Around("this(hello.aop.member.MemberServiceImpl)")
		public Object doThisImpl(ProceedingJoinPoint joinPoint) throws Throwable {
			log.info("[this-impl] {}",joinPoint.getSignature());
			return joinPoint.proceed();
		}

		@Around("target(hello.aop.member.MemberServiceImpl)")
		public Object doTargetImpl(ProceedingJoinPoint joinPoint) throws Throwable {
			log.info("[target-impl] {}",joinPoint.getSignature());
			return joinPoint.proceed();
		}
	}
}
