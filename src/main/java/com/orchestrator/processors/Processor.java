package com.orchestrator.processors;

public interface Processor {
    public String[] process(ProcessorContext input);
    public default String getName(){return this.getClass().getName();}
}
