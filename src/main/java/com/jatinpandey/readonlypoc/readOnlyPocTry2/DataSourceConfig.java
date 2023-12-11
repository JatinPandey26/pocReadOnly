package com.jatinpandey.readonlypoc.readOnlyPocTry2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Configuration
public class DataSourceConfig {

    @Primary
    @Bean(name = "primaryDataSourceProperties")
    @ConfigurationProperties("spring.datasource.default")
    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "primaryDataSource")
    public DataSource primaryDataSource(@Qualifier("primaryDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "readOnlyDataSourceProperties")
    @ConfigurationProperties("spring.datasource.read")
    public DataSourceProperties readOnlyDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "readOnlyDataSource")
    public DataSource readOnlyDataSource(@Qualifier("readOnlyDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    public CustomRoutingDataSource customRoutingDataSource(
            @Qualifier("primaryDataSource") DataSource primaryDataSource,
            @Qualifier("readOnlyDataSource") DataSource readOnlyDataSource) {

        CustomRoutingDataSource routingDataSource = new CustomRoutingDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DatasourceType.PRIMARY, primaryDataSource);
        targetDataSources.put(DatasourceType.READ_ONLY, readOnlyDataSource);

        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(primaryDataSource);
        log.info("Routing data source initialized " + routingDataSource);
        return routingDataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("customRoutingDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("customRoutingDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.jatinpandey.readonlypoc")
                .persistenceUnit("yourPersistenceUnit")
                .build();
    }
}

