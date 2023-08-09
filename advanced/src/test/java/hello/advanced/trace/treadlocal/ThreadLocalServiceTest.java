package hello.advanced.trace.treadlocal;

import org.junit.jupiter.api.Test;

import hello.advanced.trace.treadlocal.code.FieldService;
import hello.advanced.trace.treadlocal.code.ThreadLocalService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalServiceTest {

	private ThreadLocalService threadLocalService = new ThreadLocalService();

	@Test
	void field() {
		log.info("main start");

		Runnable userA = () -> {
			threadLocalService.logic("userA");
		};

		Runnable userB = () -> {
			threadLocalService.logic("userB");
		};

		Thread threadA = new Thread(userA);
		threadA.setName("thread-A");
		Thread threadB = new Thread(userB);
		threadB.setName("thread-B");

		threadA.start();
		sleep(100);//동시성 문제 발생 X
		threadB.start();
		sleep(2000); //메인쓰레드 종료 대기
		log.info("main exit");
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
