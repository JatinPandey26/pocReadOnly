package com.jatinpandey.readonlypoc.readOnlyPocTry2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class CustomRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        DatasourceType datasourceType = DatasourceContextHolder.getDatasourceType();
        log.info("Determine current lookup key: {}", datasourceType);
        return datasourceType;
    }

}
