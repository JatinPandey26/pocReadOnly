package com.jatinpandey.readonlypoc.readOnlyPocTry2;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j

public class ReadOnlyOpAspect {



    @After("@annotation(com.jatinpandey.readonlypoc.readOnlyPocTry2.ReadOnlyOp)")
    public void resetDatasource() {
        DatasourceContextHolder.clearDatasourceType();
    }

    @Before("@annotation(com.jatinpandey.readonlypoc.readOnlyPocTry2.ReadOnlyOp)")
    public void setReadOnlyDatasource() {
        log.info("Setting read-only datasource");
        DatasourceContextHolder.setDatasourceType(DatasourceType.READ_ONLY);
        log.info("Datasource set to: {}", DatasourceContextHolder.getDatasourceType());
    }

//    @Before("execution(* com.jatinpandey.readonlypoc.readOnlyPocTry2.*.*(..)) && !@annotation(com.jatinpandey.readonlypoc.readOnlyPocTry2.ReadOnlyOp)")
//    public void setDefaultDatasource() {
//        // Logic to set the datasource to the default (non-read-only) type
//        log.info("setting default datasource");
//        DatasourceContextHolder.setDatasourceType(DatasourceType.DEFAULT);
//    }

}