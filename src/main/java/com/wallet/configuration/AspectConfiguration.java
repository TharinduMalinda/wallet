package com.wallet.configuration;


import com.wallet.dto.LoggerMessageDTO;
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

    //exclude the JwtFilter class since it is final
    @Pointcut(value = "execution(* com.wallet.*.*.*(..) ) && !within(com.wallet.security.JwtFilter)")

    public void mainPointCut(){

    }

    //main log creation
    @Around("mainPointCut()")
    public Object appLogger(ProceedingJoinPoint pjp) throws Throwable {

            String methodName = pjp.getSignature().getName();
            String className = pjp.getTarget().getClass().toString();
            Object[] argArray =  pjp.getArgs();
            Long startTime = System.currentTimeMillis();
            Object outObject = pjp.proceed();
            Long endTime = System.currentTimeMillis();

            LoggerMessageDTO message = new LoggerMessageDTO(className,methodName,argArray,endTime-startTime,outObject);
            log.info("appLogger : {}", message);
            return outObject;

    }
}
