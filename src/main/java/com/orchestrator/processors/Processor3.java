package com.orchestrator.processors;

public class Processor3 implements Processor{
    @Override
    public String[] process(ProcessorContext ctx) {
        ctx.addToInput("processor3","input");
        ctx.addToOutput("processor3","output");
        return new String[]{"PROCESSOR3_COMPLETE"} ;
    }
}
