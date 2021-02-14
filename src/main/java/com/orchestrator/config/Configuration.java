package com.orchestrator.config;

import com.orchestrator.interceptor.ConcreteInterceptor;
import com.orchestrator.interceptor.ProcessElementFilterInterceptor;
import com.orchestrator.processors.Processor;
import com.orchestrator.processors.Processor1;
import com.orchestrator.processors.Processor2;
import com.orchestrator.processors.Processor3;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@org.springframework.context.annotation.Configuration
@ComponentScan("com.orchestrator")
public class Configuration {

    @Bean
    Processor1 processor1(){
        return new Processor1();
    }
    @Bean
    Processor2 processor2(){
        return new Processor2();
    }
    @Bean
    Processor3 processor3(){
        return new Processor3();
    }

    @Bean(name="processor1Filter")
    ConcreteInterceptor processor1Filter(){
        ConcreteInterceptor concreteInterceptor= new ConcreteInterceptor();
        initializeProcessElementFilterInterceptor(concreteInterceptor,processor1(),new String[]{"!PROCESSOR1_COMPLETE"});
        return concreteInterceptor;
    }

    @Bean(name="processor2Filter")
    ConcreteInterceptor processor2Filter(){
        ConcreteInterceptor concreteInterceptor= new ConcreteInterceptor();
        initializeProcessElementFilterInterceptor(concreteInterceptor,processor2(),new String[]{"PROCESSOR1_COMPLETE,!PROCESSOR1_COMPLETE"});
        return concreteInterceptor;
    }

    @Bean(name="processor3Filter")
    ConcreteInterceptor processor3Filter(){
        ConcreteInterceptor concreteInterceptor= new ConcreteInterceptor();
        initializeProcessElementFilterInterceptor(concreteInterceptor,processor3(),new String[]{"PROCESSOR1_COMPLETE,PROCESSOR1_COMPLETE,!PROCESSOR1_COMPLETE"});
        return concreteInterceptor;
    }

    private void initializeProcessElementFilterInterceptor(ProcessElementFilterInterceptor interceptor, Processor processor, String [] facts){
        interceptor.setProcessor(processor);
        interceptor.setFacts(facts);
    }

    @Bean
    AspectJExpressionPointcut aspectJExpressionPointcut(){
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("bean(process) and execution(void com.orchestrator.processors.Process.execute(..))");
        return pointcut;
    }
    @Bean
    public Advisor processor1Advisor() {
        AbstractPointcutAdvisor advisor = new DefaultPointcutAdvisor(aspectJExpressionPointcut(), processor1Filter());
        advisor.setOrder(1);
        return advisor;
    }

    @Bean
    public Advisor processor3Advisor() {
        AbstractPointcutAdvisor advisor = new DefaultPointcutAdvisor(aspectJExpressionPointcut(), processor3Filter());
        advisor.setOrder(3);
        return advisor;
    }
}
