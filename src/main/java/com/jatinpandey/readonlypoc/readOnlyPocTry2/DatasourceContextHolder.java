package com.jatinpandey.readonlypoc.readOnlyPocTry2;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatasourceContextHolder {

    private static final ThreadLocal<DatasourceType> contextHolder = new ThreadLocal<>();

    public static void setDatasourceType(DatasourceType datasourceType) {
        contextHolder.set(datasourceType);
        log.info("Datasource type set to: {}", datasourceType);
    }

    public static DatasourceType getDatasourceType() {
        log.info("Getting datasource type Context: {}", contextHolder.get());
        return contextHolder.get();
    }

    public static void clearDatasourceType() {
        contextHolder.remove();
    }
}
