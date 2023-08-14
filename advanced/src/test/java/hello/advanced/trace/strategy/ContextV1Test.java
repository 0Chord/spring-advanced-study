package hello.advanced.trace.strategy;

import org.junit.jupiter.api.Test;

import hello.advanced.trace.strategy.code.strategy.ContextV1;
import hello.advanced.trace.strategy.code.strategy.Strategy;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic1;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic2;
import hello.advanced.trace.template.code.AbstractTemplate;
import hello.advanced.trace.template.code.SubClassLogic1;
import hello.advanced.trace.template.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextV1Test {

	@Test
	void strategyV0() {
		logic1();
		logic2();
	}

	private void logic1() {
		long startTime = System.currentTimeMillis();
		//비즈니스 로직 실행
		log.info("비즈니스 로직1 실행");
		//비즈니스 로직 종료
		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime;
		log.info("resultTime = {}", resultTime);
	}

	private void logic2() {
		long startTime = System.currentTimeMillis();
		//비즈니스 로직 실행
		log.info("비즈니스 로직2 실행");
		//비즈니스 로직 종료
		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime;
		log.info("resultTime = {}", resultTime);
	}

	@Test
	void templateMethodV1() {
		AbstractTemplate template = new SubClassLogic1();
		template.execute();
		AbstractTemplate template2 = new SubClassLogic2();
		template2.execute();
	}

	@Test
	void templateMethodV2() {
		AbstractTemplate template1 = new AbstractTemplate() {
			@Override
			protected void call() {
				log.info("비즈니스 로직1 실행");
			}
		};

		template1.execute();

		AbstractTemplate template2 = new AbstractTemplate() {
			@Override
			protected void call() {
				log.info("비즈니스 로직2 실행");
			}
		};

		template2.execute();
	}

	@Test
	void strategyV1() {
		StrategyLogic1 strategyLogic1 = new StrategyLogic1();
		ContextV1 contextV1 = new ContextV1(strategyLogic1);
		contextV1.execute();

		StrategyLogic2 strategyLogic2 = new StrategyLogic2();
		ContextV1 contextV2 = new ContextV1(strategyLogic2);
		contextV2.execute();
	}

	@Test
	void strategyV2() {
		Strategy logic1 = new Strategy() {
			@Override
			public void call() {
				log.info("비즈니스 로직1 실행");
			}
		};
		ContextV1 contextV1 = new ContextV1(logic1);
		contextV1.execute();

		Strategy logic2 = new Strategy() {
			@Override
			public void call() {
				log.info("비즈니스 로직2 실행");
			}
		};

		ContextV1 contextV2 = new ContextV1(logic2);
		contextV2.execute();
	}

	@Test
	void strategyV3() {
		ContextV1 contextV1 = new ContextV1(new Strategy() {
			@Override
			public void call() {
				log.info("비즈니스 로직1 실행");
			}
		});
		contextV1.execute();

		ContextV1 contextV2 = new ContextV1(new Strategy() {
			@Override
			public void call() {
				log.info("비즈니스 로직2 실행");
			}
		});
		contextV2.execute();
	}


	@Test
	void strategyV4() {

		ContextV1 contextV1 = new ContextV1(() -> log.info("비즈니스 로직1 실행"));
		contextV1.execute();

		ContextV1 contextV2 = new ContextV1(() -> log.info("비즈니스 로직2 실행"));
		contextV2.execute();
	}
}
