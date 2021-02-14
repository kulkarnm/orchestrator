package com.orchestrator.processors;

import org.springframework.stereotype.Component;

@Component
public class Process implements GenericProcess{

    @Override
    public void execute(Object input) {
        System.out.println("Dummy Process");
    }
}
