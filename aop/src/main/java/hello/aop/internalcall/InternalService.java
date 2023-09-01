package hello.aop.internalcall;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InternalService
{

	public void internal() {
		log.info("call internal");
	}

}
