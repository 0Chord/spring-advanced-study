package hello.advanced.v0;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV0 {

	public void save(String itemId) {
		if (itemId.equals("ex")) {
			throw new IllegalStateException("예외 발생!");
		}

		sleep(1000);
	}

	private static void sleep(int millis) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

	}
}
