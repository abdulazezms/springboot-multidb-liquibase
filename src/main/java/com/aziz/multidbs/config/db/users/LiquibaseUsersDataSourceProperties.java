package com.aziz.multidbs.config.db.users;

import com.aziz.multidbs.config.db.BaseDataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Database properties to be used when building the {@link javax.sql.DataSource} that Liquibase
 * will use during the migration of the users database.
 *
 */
@Configuration
@ConfigurationProperties(prefix = "spring.users-database.liquibase")
public class LiquibaseUsersDataSourceProperties extends BaseDataSourceProperties {

}
