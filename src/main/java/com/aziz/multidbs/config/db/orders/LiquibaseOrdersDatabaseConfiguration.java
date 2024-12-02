package com.aziz.multidbs.config.db.orders;

import com.aziz.multidbs.config.db.SpringLiquibaseFactory;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Database related beans to be used by Liquibase during the migration of the orders database.
 *
 */
@Configuration
@Slf4j
public class LiquibaseOrdersDatabaseConfiguration {

    @Bean
    public DataSource ordersLiquibaseDataSource(LiquibaseOrdersDataSourceProperties dataSourceProperties) {
        return dataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public SpringLiquibase ordersSpringLiquibase(@Qualifier("ordersLiquibaseDataSource") DataSource dataSource,
                                                 LiquibaseOrdersSpringProperties liquibaseProperties) {
        SpringLiquibase springLiquibase = SpringLiquibaseFactory.createSpringLiquibase(dataSource, liquibaseProperties);

        HikariDataSource hikariDataSource = (HikariDataSource) springLiquibase.getDataSource();

        log.debug("Creating SpringLiquibase for 'orders' database with the " +
                "following properties: {}. DB username: {}, DB URL: {}",
                liquibaseProperties, hikariDataSource.getUsername(), hikariDataSource.getJdbcUrl());

        return springLiquibase;
    }
}
