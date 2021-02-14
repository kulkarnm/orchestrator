package com.orchestrator.processors;

public class Processor2 implements Processor {
    @Override
    public String[] process(ProcessorContext ctx) {
        ctx.addToInput("processor2","input");
        ctx.addToOutput("processor2","output");
        return new String[]{"PROCESSOR2_COMPLETE"} ;
    }
}
