package com.aziz.multidbs.config.db;

import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;

public abstract class BaseJpaProperties extends JpaProperties {
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "properties=" + getProperties() +
                ", showSql=" + isShowSql() +
                '}';
    }
}
