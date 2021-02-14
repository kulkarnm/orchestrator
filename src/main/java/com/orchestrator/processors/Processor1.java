package com.orchestrator.processors;

public class Processor1 implements Processor {
    @Override
    public String[] process(ProcessorContext ctx) {
        ctx.addToInput("processor1","input");
        ctx.addToOutput("processor1","output");
        return new String[]{"PROCESSOR1_COMPLETE"} ;
    }

}
