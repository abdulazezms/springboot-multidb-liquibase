package com.aziz.multidbs.config.db.users;

import com.aziz.multidbs.config.db.BaseLiquibaseSpringProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties to be used when building the {@link liquibase.integration.spring.SpringLiquibase} specific
 * to the users database migration.
 *
 */
@Configuration
@ConfigurationProperties(prefix = "spring.users-database.liquibase.properties")
public class LiquibaseUsersSpringProperties extends BaseLiquibaseSpringProperties {
}
