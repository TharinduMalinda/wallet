package com.wallet.configuration;


import com.wallet.dto.LoggerMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component

public class AspectConfiguration {

    private final Logger log = LoggerFactory.getLogger(AspectConfiguration.class);

    @Pointcut(value = "execution(* com.wallet.*.*.*(..) )")
    public void mainPointCut(){

    }

    @Around("mainPointCut()")
    public Object appLogger(ProceedingJoinPoint pjp) throws Throwable {

            String methodName = pjp.getSignature().getName();
            String className = pjp.getTarget().getClass().toString();
            Object[] argArray =  pjp.getArgs();
            Long startTime = System.currentTimeMillis();
            Object outObject = pjp.proceed();
            Long endTime = System.currentTimeMillis();

            LoggerMessage message = new LoggerMessage(className,methodName,argArray,endTime-startTime,outObject);
            log.info("appLogger : {}", message);
            return outObject;

    }
}
