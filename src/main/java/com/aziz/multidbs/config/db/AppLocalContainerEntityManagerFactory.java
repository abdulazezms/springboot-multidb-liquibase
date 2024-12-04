package com.aziz.multidbs.config.db;

import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

public class AppLocalContainerEntityManagerFactory {

    public static LocalContainerEntityManagerFactoryBean createLocalContainerEntityManagerFactoryBean(String [] packagesToScan,
                                                                                                      DataSource dataSource,
                                                                                                      JpaProperties jpaProperties,
                                                                                                      EntityManagerFactoryBuilder builder,
                                                                                                      String persistenceUnitName) {
        return builder
                .dataSource(dataSource)
                .packages(packagesToScan)
                .persistenceUnit(persistenceUnitName)
                .properties(jpaProperties.getProperties())
                .build();
    }
}
