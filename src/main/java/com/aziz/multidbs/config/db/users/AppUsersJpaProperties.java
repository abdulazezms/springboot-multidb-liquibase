package com.aziz.multidbs.config.db.users;

import com.aziz.multidbs.config.db.BaseJpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ConfigurationProperties(prefix = "spring.users-database.jpa")
@Primary
public class AppUsersJpaProperties extends BaseJpaProperties {
}
