package com.orchestrator.aspects;

import com.orchestrator.interceptor.ProcessElementFilterInterceptor;
import com.orchestrator.processors.Processor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
@Aspect
public class TransitionGovernorAspect {
   // private static Logger logger = Logger.getLogger(TransitionGovernorAspect.class);
    /**
     *
     * @param thisJoinPoint
     * @param proxyMethodInvocation - the argument of the target method which is MethodInvocation
     * @param filter - the target of this interception which is {@link com.orchestrator.interceptor.ProcessElementFilterInterceptor}
     * @return
     * @throws Throwable
     */
    @Around("execution(public java.lang.Object process.orchestration.sample.*.invoke(..)) " +
            "&& args(proxyMethodInvocation) && target(filter)")
    public Object intercept(ProceedingJoinPoint thisJoinPoint,
                            MethodInvocation proxyMethodInvocation,
                            ProcessElementFilterInterceptor filter) throws Throwable{
        Object returned = null;
        // Now we need to extract Fact registry from the input parameter of
        // the invocation object of the proxy filter
        Map input = (Map) proxyMethodInvocation.getArguments()[0];
        // get the list of registered facts
        List<String> factRegistry = (List<String>) input.get("FACTS");
        // get current activity which is wired to the proxy fiter
        Processor currentProcessor = filter.getProcessor();
        //logger.info(">> Intercepting: " + currentActivity);
        // get the list of fact rules to be evaluated against registered facts
        String[] facts = filter.getFacts();
        // Evaluate the facts in the context of the current Activity
        // and make grant/deny decision
        if (this.grantInvocation(currentProcessor, factRegistry, facts)){
            //logger.info(">> Allowing invocation of the activity");
            // proceeds to the target which is a filter's invoke(..) method
            returned = thisJoinPoint.proceed();
        } else {
            // invoke proceed(..) method of the proxy filter
            // by doing so we'll skip the actual invocation fo the invoke(..)
            // method and obviously the activity itself
            //logger.info(">> Skipping invocation of the activity");
            returned = proxyMethodInvocation.proceed();
        }
        return returned;
    }
    /**
     * Evaluates Fact rules based on a simple algorithm which checks for existence of fact or
     * lack there off(based on presence of '!' character).
     * @param processor
     * @param factRegistry
     * @param facts
     * @return
     */
    private boolean grantInvocation(Processor processor, List factRegistry, String[] facts){
        boolean grantInvocation = true;

        for (String fact : facts) {
            if (StringUtils.startsWithIgnoreCase(fact, "!")){
                fact = StringUtils.trimLeadingCharacter(fact, '!');
                grantInvocation = !factRegistry.contains(fact);
            } else {
                grantInvocation = factRegistry.contains(fact);
            }
            // since all conditions must return "true", we don;t need to evaluate anymore if
            // grantInvocation is already "false"
            if (!grantInvocation){
                break;
            }
        }

        return grantInvocation;
    }
}
