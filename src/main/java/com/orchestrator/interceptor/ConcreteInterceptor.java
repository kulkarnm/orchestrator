package com.orchestrator.interceptor;

import com.orchestrator.processors.Processor;

public class ConcreteInterceptor extends ProcessElementFilterInterceptor {
    private Processor processor;
    private String[] facts;

    public String[] getFacts() {
        return facts;
    }

    public void setFacts(String[] facts) {
        this.facts = facts;
    }

    public ConcreteInterceptor(){
    }

    public Processor getProcessor(){
        return processor;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

}
