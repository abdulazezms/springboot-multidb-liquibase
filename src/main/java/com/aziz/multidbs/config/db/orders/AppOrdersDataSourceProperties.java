package com.aziz.multidbs.config.db.orders;

import com.aziz.multidbs.config.db.BaseDataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Database properties to be used when building the {@link javax.sql.DataSource} that the application
 * JPA repositories wil use when interacting with the orders database
 *
 */
@Configuration
@ConfigurationProperties(prefix = "spring.orders-database.datasource")
public class AppOrdersDataSourceProperties extends BaseDataSourceProperties {
}
