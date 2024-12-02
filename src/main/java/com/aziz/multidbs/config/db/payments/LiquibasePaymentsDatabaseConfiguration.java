package com.aziz.multidbs.config.db.payments;

import com.aziz.multidbs.config.db.SpringLiquibaseFactory;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Database related beans to be used by Liquibase during the migration of the payments database.
 *
 */
@Configuration
@Slf4j
public class LiquibasePaymentsDatabaseConfiguration {

    @Bean
    public DataSource paymentsLiquibaseDataSource(LiquibasePaymentsDataSourceProperties dataSourceProperties) {
        return dataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public SpringLiquibase paymentsSpringLiquibase(@Qualifier("paymentsLiquibaseDataSource") DataSource dataSource,
                                                   LiquibasePaymentsSpringProperties liquibaseProperties) {
        SpringLiquibase springLiquibase = SpringLiquibaseFactory.createSpringLiquibase(dataSource, liquibaseProperties);

        HikariDataSource hikariDataSource = (HikariDataSource) springLiquibase.getDataSource();

        log.debug("Creating SpringLiquibase for 'payments' database with the " +
                        "following properties: {}. DB username: {}, DB URL: {}",
                liquibaseProperties, hikariDataSource.getUsername(), hikariDataSource.getJdbcUrl());

        return springLiquibase;
    }
}
