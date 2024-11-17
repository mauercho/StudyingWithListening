package com.ssafy.a304.shortgong.global.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class TimeAspect {

	@Around("execution(* com.ssafy.a304.shortgong.domain.summary.controller.SummaryController.createSummary(..))")
	public Object executionAspect(ProceedingJoinPoint joinPoint) throws Throwable {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		Object result = joinPoint.proceed();

		stopWatch.stop();
		log.info("Execution time of {}: {} ms", joinPoint.getSignature(), stopWatch.getTotalTimeMillis());

		return result;
	}
}