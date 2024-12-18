package ma.enset.aspects;

import ma.enset.aspects.annotations.Cacheable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@EnableAspectJAutoProxy
public class CacheAspect {
    private final Map<String, Object> cache = new HashMap<>();

    @Around("@annotation(cacheable)")
    public Object cacheMethod(ProceedingJoinPoint joinPoint, Cacheable cacheable) throws Throwable {
        String key = joinPoint.getSignature().toString();
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        Object result = joinPoint.proceed();
        cache.put(key, result);
        return result;
    }
}
