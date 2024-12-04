package com.aziz.multidbs.config.db.orders;

import com.aziz.multidbs.config.db.BaseJpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.orders-database.jpa")
public class AppOrdersJpaProperties extends BaseJpaProperties {
}
