package hello.proxy.config;

import org.springframework.aop.Pointcut;
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
import hello.proxy.config.v3_proxyfactory.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;

@Configuration
public class ProxyFactoryConfigV1 {

	@Bean
	public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
		OrderControllerV1Impl target = new OrderControllerV1Impl(orderServiceV1(logTrace));
		ProxyFactory factory = new ProxyFactory(target);
		factory.addAdvisor(getAdvisor(logTrace));
		return (OrderControllerV1)factory.getProxy();
	}

	@Bean
	public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
		OrderServiceV1Impl target = new OrderServiceV1Impl(orderRepositoryV1(logTrace));
		ProxyFactory factory = new ProxyFactory(target);
		factory.addAdvisor(getAdvisor(logTrace));
		return (OrderServiceV1)factory.getProxy();
	}

	@Bean
	public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
		OrderRepositoryV1 target = new OrderRepositoryV1Impl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.addAdvisor(getAdvisor(logTrace));
		return (OrderRepositoryV1)proxyFactory.getProxy();
	}

	private static DefaultPointcutAdvisor getAdvisor(LogTrace logTrace) {
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedNames("save*","request*","order*");
		//advice
		LogTraceAdvice advice = new LogTraceAdvice(logTrace);
		return new DefaultPointcutAdvisor(pointcut,advice);
	}
}
