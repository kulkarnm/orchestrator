package com.orchestrator.processors;

import java.util.HashMap;
import java.util.Map;

public class ProcessorContext {
    private Map<String,String> input;
    private Map<String,String> output;

    public ProcessorContext() {
        input = new HashMap<>();
        output = new HashMap<>();
    }

    public void addToInput(String key,String value){
        input.put(key,value);
    }
    public void addToOutput(String key,String value){
        output.put(key,value);
    }
}
