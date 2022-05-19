package com.jambit.interceptors;

import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.Arrays;

@Interceptor
@MeasurePerformance
@ApplicationScoped
@Slf4j
public class PerformanceInterceptor {
    @AroundInvoke
    public Object measurePerformance(InvocationContext ctx) throws Throwable{

        String methodCalled = ctx.getMethod().getName();
        String params = Arrays.toString(ctx.getParameters());
        String calledClass = ctx.getTarget().getClass().getSimpleName();

        long start = System.currentTimeMillis();
        Object result = ctx.proceed();
        long duration = System.currentTimeMillis() - start;

        log.warn("Performance: {} ms of call {}.{}({})", duration, calledClass, methodCalled, params);

        return result;
    }
}
