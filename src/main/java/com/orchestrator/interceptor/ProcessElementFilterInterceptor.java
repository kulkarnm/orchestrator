package com.orchestrator.interceptor;

import com.orchestrator.processors.Processor;
import com.orchestrator.processors.ProcessorContext;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.List;
import java.util.Map;

public abstract class ProcessElementFilterInterceptor implements MethodInterceptor {
   // private static Logger logger = Logger.getLogger(ActivityFilterInterceptor.class);
    protected Processor processor;
    protected String[] facts;

    public String[] getFacts() {
        return facts;
    }

    public void setFacts(String[] facts) {
        this.facts = facts;
    }

    public ProcessElementFilterInterceptor(){
    }

    public Processor getProcessor(){
        return processor;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    /**
     * Invokes underlying activity with the arguments provided and registers the Facts
     * returned by the {@link Processor#process(com.orchestrator.processors.ProcessorContext)} method.
     */
    @SuppressWarnings("unchecked")
    public Object invoke(MethodInvocation invocation) throws Throwable {
       // logger.info(">>>> Executing " + processor.getName());
        Map input = (Map) invocation.getArguments()[0];
        String[] facts = processor.process((ProcessorContext)invocation.getArguments()[0]);
        for (String fact : facts) {
            List<String> factRegistry = (List<String>) input.get("FACTS");
            factRegistry.add(fact);
        }
        return invocation.proceed();
    }
}
