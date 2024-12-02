package com.aziz.multidbs.config.db;

import liquibase.UpdateSummaryEnum;
import liquibase.UpdateSummaryOutputEnum;
import liquibase.integration.spring.SpringLiquibase;
import liquibase.ui.UIServiceEnum;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

public class SpringLiquibaseFactory {

    public static SpringLiquibase createSpringLiquibase(DataSource dataSource, LiquibaseProperties liquibaseProperties) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(liquibaseProperties.getChangeLog());
        liquibase.setClearCheckSums(liquibaseProperties.isClearChecksums());
        if (!CollectionUtils.isEmpty(liquibaseProperties.getContexts())) {
            liquibase.setContexts(StringUtils.collectionToCommaDelimitedString(liquibaseProperties.getContexts()));
        }
        liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
        liquibase.setLiquibaseSchema(liquibaseProperties.getLiquibaseSchema());
        liquibase.setLiquibaseTablespace(liquibaseProperties.getLiquibaseTablespace());
        liquibase.setDatabaseChangeLogTable(liquibaseProperties.getDatabaseChangeLogTable());
        liquibase.setDatabaseChangeLogLockTable(liquibaseProperties.getDatabaseChangeLogLockTable());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        liquibase.setShouldRun(liquibaseProperties.isEnabled());
        if (!CollectionUtils.isEmpty(liquibaseProperties.getLabelFilter())) {
            liquibase.setLabelFilter(StringUtils.collectionToCommaDelimitedString(liquibaseProperties.getLabelFilter()));
        }
        liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
        liquibase.setRollbackFile(liquibaseProperties.getRollbackFile());
        liquibase.setTestRollbackOnUpdate(liquibaseProperties.isTestRollbackOnUpdate());
        liquibase.setTag(liquibaseProperties.getTag());
        if (liquibaseProperties.getShowSummary() != null) {
            liquibase.setShowSummary(UpdateSummaryEnum.valueOf(liquibaseProperties.getShowSummary().name()));
        }
        if (liquibaseProperties.getShowSummaryOutput() != null) {
            liquibase
                    .setShowSummaryOutput(UpdateSummaryOutputEnum.valueOf(liquibaseProperties.getShowSummaryOutput().name()));
        }
        if (liquibaseProperties.getUiService() != null) {
            liquibase.setUiService(UIServiceEnum.valueOf(liquibaseProperties.getUiService().name()));
        }
        return liquibase;
    }
}
