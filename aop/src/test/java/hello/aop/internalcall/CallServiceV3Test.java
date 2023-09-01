package hello.aop.internalcall;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import hello.aop.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Import(CallLogAspect.class)
@Slf4j
class CallServiceV3Test {

	@Autowired
	CallServiceV3 callServiceV3;
	@Test
	void external() {
		callServiceV3.external();
	}
}