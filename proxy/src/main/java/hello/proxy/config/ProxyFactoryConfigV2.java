package hello.proxy.config;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.proxy.app.v1.OrderControllerV1;
import hello.proxy.app.v1.OrderControllerV1Impl;
import hello.proxy.app.v1.OrderRepositoryV1;
import hello.proxy.app.v1.OrderRepositoryV1Impl;
import hello.proxy.app.v1.OrderServiceV1;
import hello.proxy.app.v1.OrderServiceV1Impl;
import hello.proxy.app.v2.OrderControllerV2;
import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.config.v3_proxyfactory.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;

@Configuration
public class ProxyFactoryConfigV2 {

	@Bean
	public OrderControllerV2 orderControllerV1(LogTrace logTrace) {
		OrderControllerV2 target = new OrderControllerV2(orderServiceV2(logTrace));
		ProxyFactory factory = new ProxyFactory(target);
		factory.addAdvisor(getAdvisor(logTrace));
		return (OrderControllerV2)factory.getProxy();
	}

	@Bean
	public OrderServiceV2 orderServiceV2(LogTrace logTrace) {
		OrderServiceV2 target = new OrderServiceV2(orderRepositoryV2(logTrace));
		ProxyFactory factory = new ProxyFactory(target);
		factory.addAdvisor(getAdvisor(logTrace));
		return (OrderServiceV2)factory.getProxy();
	}

	@Bean
	public OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace) {
		OrderRepositoryV2 target = new OrderRepositoryV2();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.addAdvisor(getAdvisor(logTrace));
		return (OrderRepositoryV2)proxyFactory.getProxy();
	}

	private static DefaultPointcutAdvisor getAdvisor(LogTrace logTrace) {
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedNames("save*","request*","order*");
		//advice
		LogTraceAdvice advice = new LogTraceAdvice(logTrace);
		return new DefaultPointcutAdvisor(pointcut,advice);
	}
}
