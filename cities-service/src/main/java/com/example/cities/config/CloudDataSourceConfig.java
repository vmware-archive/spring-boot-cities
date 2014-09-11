package com.example.cities.config;

import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.PooledServiceConnectorConfig;
import org.springframework.cloud.service.relational.DataSourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Profile("cloud")
@Configuration
public class CloudDataSourceConfig extends AbstractCloudConfig {
    @Bean
    public DataSource dataSource() {
        PooledServiceConnectorConfig.PoolConfig poolConfig = new PooledServiceConnectorConfig.PoolConfig(20, 200);
        DataSourceConfig.ConnectionConfig connectionConfig =
                new DataSourceConfig.ConnectionConfig("sessionVariables=sql_mode='ANSI';characterEncoding=UTF-8");
        DataSourceConfig serviceConfig =
                new DataSourceConfig(poolConfig, connectionConfig);
        return connectionFactory().dataSource("cities-db", serviceConfig);
    }
}
