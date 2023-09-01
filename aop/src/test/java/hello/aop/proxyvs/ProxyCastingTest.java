package hello.aop.proxyvs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProxyCastingTest {

	@Test
	void jdkProxy() {
		MemberService target = new MemberServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.setProxyTargetClass(false); //jdk 동적 프록시

		//프록시를 인터페이스로 캐스팅
		MemberService proxy = (MemberService)proxyFactory.getProxy();

		//JDK 동적 프록시를 구현 클래스로 캐스팅 시도 실패, ClassCastException 예외 발생
		Assertions.assertThrows(ClassCastException.class, () -> {
			MemberServiceImpl castingMemberService = (MemberServiceImpl)proxy;
		});
	}

	@Test
	void cglibProxy() {
		MemberService target = new MemberServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.setProxyTargetClass(true); //jdk 동적 프록시

		//프록시를 인터페이스로 캐스팅
		MemberService proxy = (MemberService)proxyFactory.getProxy();

		//CGLIB 구현 구체 클래스 캐스팅 시도 성공
		MemberServiceImpl castingMemberService = (MemberServiceImpl)proxy;
	}
}
