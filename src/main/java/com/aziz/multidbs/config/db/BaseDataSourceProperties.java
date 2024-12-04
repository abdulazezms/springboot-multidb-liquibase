package com.aziz.multidbs.config.db;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

public abstract class BaseDataSourceProperties extends DataSourceProperties {
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "url='" + getUrl() + '\'' +
                ", username='" + getUsername() + '\'' +
                '}';
    }
}
