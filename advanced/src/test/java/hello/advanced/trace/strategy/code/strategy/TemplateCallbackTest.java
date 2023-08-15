package hello.advanced.trace.strategy.code.strategy;

import org.junit.jupiter.api.Test;

import hello.advanced.trace.strategy.code.template.Callback;
import hello.advanced.trace.strategy.code.template.TimeLogTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TemplateCallbackTest {

	/**
	 * 템플릿 콜백 패턴 - 익명 내부 클래스
	 */
	@Test
	void callbackV1() {
		TimeLogTemplate timeLogTemplate = new TimeLogTemplate();
		timeLogTemplate.execute(() -> log.info("비즈니스 로직1 실행"));

		timeLogTemplate.execute(() -> log.info("비즈니스 로직2 실행"));
	}
}
