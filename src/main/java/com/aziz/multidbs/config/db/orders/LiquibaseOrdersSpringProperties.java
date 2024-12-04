package com.aziz.multidbs.config.db.orders;

import com.aziz.multidbs.config.db.BaseLiquibaseSpringProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties to be used when building the {@link liquibase.integration.spring.SpringLiquibase} specific
 * to the orders database migration.
 *
 */
@Configuration
@ConfigurationProperties(prefix = "spring.orders-database.liquibase.properties")
public class LiquibaseOrdersSpringProperties extends BaseLiquibaseSpringProperties {
}
