package com.example.ems.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    // Runs before every method inside com.example.ems.Service package
    @Before("execution(* com.example.ems.Service.*.*(..))")
    public void logMethodStart(JoinPoint joinPoint) {
        System.out.println("➡️ [DEBUG] Entering Method: " + joinPoint.getSignature());
        System.out.println("➡️ [DEBUG] Arguments: " + Arrays.toString(joinPoint.getArgs()));
    }

    // Runs after method completes successfully
    @AfterReturning(pointcut = "execution(* com.example.ems.Service.*.*(..))", returning = "result")
    public void logMethodReturn(JoinPoint joinPoint, Object result) {
        System.out.println("✅ [DEBUG] Method Completed: " + joinPoint.getSignature());
        System.out.println("✅ [DEBUG] Returned: " + result);
    }

    // Runs if method throws exception
    @AfterThrowing(pointcut = "execution(* com.example.ems.Service.*.*(..))", throwing = "ex")
    public void logMethodException(JoinPoint joinPoint, Throwable ex) {
        System.out.println("❌ [ERROR] Exception in Method: " + joinPoint.getSignature());
        System.out.println("❌ [ERROR] Exception Message: " + ex.getMessage());
    }
}
