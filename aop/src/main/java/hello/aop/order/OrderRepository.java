package hello.aop.order;

import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class OrderRepository {

	public String save(String itemId) {
		log.info("[orderRepository 실행]");

		if (itemId.equals("ex")) {
			throw new IllegalStateException("예외 발생");
		}

		return "ok";
	}
}