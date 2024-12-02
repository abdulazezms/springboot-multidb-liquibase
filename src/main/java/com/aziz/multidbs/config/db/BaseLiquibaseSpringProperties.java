package com.aziz.multidbs.config.db;

import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;

public abstract class BaseLiquibaseSpringProperties extends LiquibaseProperties {
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "changeLog='" + getChangeLog() + '\'' +
                ", contexts=" + getContexts() +
                ", defaultSchema='" + getDefaultSchema() + '\'' +
                ", liquibaseSchema='" + getLiquibaseSchema() + '\'' +
                ", enabled=" + isEnabled() +
                '}';
    }
}
