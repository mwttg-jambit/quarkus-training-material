package at.javatraining.interceptors;

import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.concurrent.atomic.AtomicInteger;

@Interceptor
@CountRequests
@Slf4j
@ApplicationScoped
public class RequestCounterInterceptor {
    AtomicInteger requestCounter = new AtomicInteger(0);

    @AroundInvoke
    public Object countRequest(InvocationContext ctx) throws Exception {
        log.info("call detected: {}", ctx.getMethod().getName());
        log.info("Counter: {}", requestCounter.incrementAndGet());

        return ctx.proceed();
    }
}
