package com.aziz.multidbs.config.db.orders;

import com.aziz.multidbs.config.db.AppLocalContainerEntityManagerFactory;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Objects;

/**
 * Database related beans to be used by JPA repositories to interact the orders database
 *
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.aziz.multidbs.repository.orders",
        entityManagerFactoryRef = "ordersLocalContainerEntityManagerFactoryBean",
        transactionManagerRef = "ordersPlatformTxManager")
@Slf4j
public class AppOrdersDatabaseConfiguration {

    @Bean
    public DataSource ordersDataSource(AppOrdersDataSourceProperties dataSourceProperties) {
        log.debug("Creating DataSource for the 'orders' database with the following properties: {}", dataSourceProperties);
        return dataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean ordersLocalContainerEntityManagerFactoryBean(@Value("${spring.orders-database.packages-to-scan}") String[] packagesToScan,
                                                                             @Qualifier("ordersDataSource") DataSource dataSource,
                                                                             AppOrdersJpaProperties jpaProperties,
                                                                             EntityManagerFactoryBuilder builder) {
        log.debug("Creating LocalContainerEntityManagerFactoryBean for the 'orders' database with the following parameters: " +
                    "packages to scan: {}, jpa properties: {}", Arrays.toString(packagesToScan), jpaProperties);
        return AppLocalContainerEntityManagerFactory.createLocalContainerEntityManagerFactoryBean(packagesToScan, dataSource, jpaProperties, builder, "app-orders");
    }


    @Bean
    public PlatformTransactionManager ordersPlatformTxManager(@Qualifier("ordersLocalContainerEntityManagerFactoryBean") LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
        return new JpaTransactionManager(Objects.requireNonNull(localContainerEntityManagerFactoryBean.getObject()));
    }
}
