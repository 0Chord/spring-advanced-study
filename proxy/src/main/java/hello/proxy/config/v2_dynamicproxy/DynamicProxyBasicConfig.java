package hello.proxy.config.v2_dynamicproxy;

import java.lang.reflect.Proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.proxy.app.v1.OrderControllerV1;
import hello.proxy.app.v1.OrderControllerV1Impl;
import hello.proxy.app.v1.OrderRepositoryV1;
import hello.proxy.app.v1.OrderRepositoryV1Impl;
import hello.proxy.app.v1.OrderServiceV1;
import hello.proxy.app.v1.OrderServiceV1Impl;
import hello.proxy.config.v2_dynamicproxy.handler.LogTraceBasicHandler;
import hello.proxy.trace.logtrace.LogTrace;

@Configuration
public class DynamicProxyBasicConfig {

	@Bean
	public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
		OrderRepositoryV1Impl repositoryImpl = new OrderRepositoryV1Impl();
		LogTraceBasicHandler handler = new LogTraceBasicHandler(repositoryImpl, logTrace);
		return (OrderRepositoryV1)Proxy.newProxyInstance(OrderRepositoryV1.class.getClassLoader(),
			new Class[] {OrderRepositoryV1.class}, handler);
	}

	@Bean
	public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
		OrderServiceV1Impl orderService = new OrderServiceV1Impl(orderRepositoryV1(logTrace));
		LogTraceBasicHandler handler = new LogTraceBasicHandler(orderService, logTrace);
		OrderServiceV1 proxy = (OrderServiceV1)Proxy.newProxyInstance(OrderServiceV1.class.getClassLoader(),
			new Class[] {OrderServiceV1.class}, handler);

		return proxy;
	}

	@Bean
	public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
		OrderControllerV1Impl orderController = new OrderControllerV1Impl(orderServiceV1(logTrace));
		LogTraceBasicHandler handler = new LogTraceBasicHandler(orderController, logTrace);
		OrderControllerV1 proxy = (OrderControllerV1)Proxy.newProxyInstance(OrderControllerV1.class.getClassLoader(),
			new Class[] {OrderControllerV1.class}, handler);

		return proxy;
	}
}
