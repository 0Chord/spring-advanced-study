package hello.proxy.proxyfactory;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ConcreteService;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProxyFactoryTest {

	@Test
	@DisplayName("인터페이스가 있으면 JdkDynamicProxy를 사용")
	void interfaceProxy() {
		ServiceInterface target = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.addAdvice(new TimeAdvice());
		ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();
		log.info("targetClass = {}", target.getClass());
		log.info("proxyClass = {}", proxy.getClass());

		proxy.save();

		assertThat(AopUtils.isAopProxy(proxy)).isTrue();
	}

	@Test
	@DisplayName("구체클래스만 있으면 CGLIB를 사용")
	void concreteClassProxy() {
		ConcreteService target = new ConcreteService();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.addAdvice(new TimeAdvice());
		ConcreteService proxy = (ConcreteService)proxyFactory.getProxy();
		log.info("targetClass = {}", target.getClass());
		log.info("proxyClass = {}", proxy.getClass());

		proxy.call();

		assertThat(AopUtils.isAopProxy(proxy)).isTrue();
	}

	@Test
	@DisplayName("proxyTargetClass가 있으면 인터페이스라도 CGLIB 사용")
	void proxyTargetClass() {
		ServiceInterface target = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.setProxyTargetClass(true);
		proxyFactory.addAdvice(new TimeAdvice());
		ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();
		log.info("targetClass = {}", target.getClass());
		log.info("proxyClass = {}", proxy.getClass());

		proxy.save();

		assertThat(AopUtils.isAopProxy(proxy)).isTrue();
	}
}
