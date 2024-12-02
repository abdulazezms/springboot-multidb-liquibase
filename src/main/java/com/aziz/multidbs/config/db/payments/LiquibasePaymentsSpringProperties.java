package com.aziz.multidbs.config.db.payments;

import com.aziz.multidbs.config.db.BaseLiquibaseSpringProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties to be used when building the {@link liquibase.integration.spring.SpringLiquibase} specific
 * to the payments database migration.
 *
 */
@Configuration
@ConfigurationProperties(prefix = "spring.payments-database.liquibase.properties")
public class LiquibasePaymentsSpringProperties extends BaseLiquibaseSpringProperties {
}
