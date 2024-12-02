package com.aziz.multidbs.config.db.payments;

import com.aziz.multidbs.config.db.BaseJpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.payments-database.jpa")
public class AppPaymentsJpaProperties extends BaseJpaProperties {
}
