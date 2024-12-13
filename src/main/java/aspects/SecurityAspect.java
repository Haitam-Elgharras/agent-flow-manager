package aspects;

import aspects.annotations.SecuredBy;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;


@Component
@Aspect
@EnableAspectJAutoProxy
public class SecurityAspect {
    @Around(value = "@annotation(securedBy)",argNames = "pjp,securedBy")
    public Object secure(ProceedingJoinPoint pjp, SecuredBy securedBy) throws Throwable {
        // the roles specified in the annotation
        String[] roles = securedBy.roles();

        if(SecurityContext.isAuthenticated()){
            for (String role : roles) {
                if(SecurityContext.hasRole(role)){
                    return pjp.proceed();
                }
            }
            throw new RuntimeException("403 : Access Denied to " + pjp.getSignature());
        }
        throw new RuntimeException("You must be authenticated");

    }
}

