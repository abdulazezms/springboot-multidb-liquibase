package com.aziz.multidbs.config.db.payments;

import com.aziz.multidbs.config.db.BaseDataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Database properties to be used when building the {@link javax.sql.DataSource} that the application
 * JPA repositories wil use when interacting with the payments database
 *
 */
@Configuration
@ConfigurationProperties(prefix = "spring.payments-database.datasource")
public class AppPaymentsDataSourceProperties extends BaseDataSourceProperties {
}
