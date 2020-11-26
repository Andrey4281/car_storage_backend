package ru.job4j.cars_storage.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class AspectLogger {

    @Pointcut("@annotation(LoggerAnnotationMethod)")
    public void loggableMethod() { }

    @Before(value = "loggableMethod()")
    private void beforeLog(JoinPoint joinPoint) {
        log.debug("Method is executing now:" + joinPoint.getTarget().getClass().getSimpleName() + " "
                + joinPoint.getSignature().getName());
        String args = Arrays.stream(joinPoint.getArgs()).filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.debug("Parameters: " + joinPoint.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(pointcut = "loggableMethod()", returning = "ret")
    private void afterLog(Object ret) {
        log.debug("Returning value: " + ret);
    }
}
