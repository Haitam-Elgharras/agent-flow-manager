package ma.enset.aspects;

import ma.enset.aspects.annotations.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@Aspect
@EnableAspectJAutoProxy
public class LoggingAspect {
    Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Around("@annotation(log)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, Log log) throws Throwable {
        logger.info("*********************");
        logger.info("Log before method " + joinPoint.getSignature());
        logger.info("*********************");
        long t1 = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long t2 = System.currentTimeMillis();
        logger.info("*********************");
        logger.info("Log after method");
        logger.info("*********************");
        logger.info("Method "+joinPoint.getSignature().getName()+" took "+(t2-t1)+" ms");
        return result;
    }
}