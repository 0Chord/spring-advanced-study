package hello.aop.pointcut;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Method;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import hello.aop.member.MemberServiceImpl;

public class ArgsTest {

	Method helloMethod;

	@BeforeEach
	public void init() throws NoSuchMethodException {
		helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
	}

	private AspectJExpressionPointcut pointcut(String expression) {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(expression);
		return pointcut;
	}

	@Test
	void args() {
		assertThat(pointcut("args(String)").matches(helloMethod, MemberServiceImpl.class))
			.isTrue();
		assertThat(pointcut("args(Object)").matches(helloMethod, MemberServiceImpl.class))
			.isTrue();
		assertThat(pointcut("args()").matches(helloMethod, MemberServiceImpl.class))
			.isFalse();
		assertThat(pointcut("args(..)").matches(helloMethod, MemberServiceImpl.class))
			.isTrue();
		assertThat(pointcut("args(*)").matches(helloMethod, MemberServiceImpl.class))
			.isTrue();
		assertThat(pointcut("args(String, ..)").matches(helloMethod, MemberServiceImpl.class))
			.isTrue();

	}

	@Test
	void argsVsExpression() {
		assertThat(pointcut("args(String)").matches(helloMethod, MemberServiceImpl.class))
			.isTrue();
		assertThat(pointcut("args(java.io.Serializable)").matches(helloMethod, MemberServiceImpl.class))
			.isTrue();
		assertThat(pointcut("args(Object)").matches(helloMethod, MemberServiceImpl.class))
			.isTrue();

		//Execution
		assertThat(pointcut("execution(* *(String))").matches(helloMethod, MemberServiceImpl.class))
			.isTrue();
		assertThat(pointcut("execution(* *(java.io.Serializable))").matches(helloMethod, MemberServiceImpl.class))
			.isFalse();
		assertThat(pointcut("execution(* *(Object))").matches(helloMethod, MemberServiceImpl.class))
			.isFalse();

	}
}

