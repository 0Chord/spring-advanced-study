package hello.advanced.v0;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepositoryV0 orderRepository;

	public void orderItem(String itemId) {
		orderRepository.save(itemId);
	}
}
