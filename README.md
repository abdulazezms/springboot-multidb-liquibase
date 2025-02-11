# Spring Boot Multi-Database Liquibase Setup

## Table of Contents
- [Overview](#overview)
- [Architecture](#architecture)
    - [Database Configuration Structure](#database-configuration-structure)
    - [Base Classes](#base-classes)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
    - [Application Properties](#application-properties-applicationyml)
- [Database Setup](#database-setup)
- [Quick Start](#quick-start)
- [Database Configuration Details](#database-configuration-details)
    - [Dual DataSource Pattern](#dual-datasource-pattern)
    - [Key Differences](#key-differences)
- [TODO](#todo)
    - [Release Liquibase Connections After Migration](#release-liquibase-connections-after-migration)
- [Testing](#testing)
    - [Test Configuration](#test-configuration)
    - [Key Testing Components](#key-testing-components)

A Spring Boot application demonstrating how to manage multiple databases with separate configurations and automated schema management using Liquibase. This project demonstrates the separation between DDL (schema changes) and DML (data manipulation) operations through dedicated database users.

## Overview

This project showcases:
- Multi-database configuration with dedicated datasources for Liquibase separate from the application
- Automated database migrations with Liquibase

## Architecture

### Database Configuration Structure
Each database (Users, Orders, Payments) has two distinct configuration sets:

1. **Application Database Configuration**
    - Configures JPA/Hibernate access
    - Uses DML-privileged user (app_user)
    - Components:
        - `App{Database}DatabaseConfiguration`
        - `App{Database}DataSourceProperties`
        - `App{Database}JpaProperties`

2. **Liquibase Configuration**
    - Manages schema migrations
    - Uses DDL-privileged user (liquibase_user)
    - Components:
        - `Liquibase{Database}DatabaseConfiguration`
        - `Liquibase{Database}DataSourceProperties`
        - `Liquibase{Database}SpringProperties`

### Base Classes
- `BaseDataSourceProperties`: Common datasource properties
- `BaseJpaProperties`: Common JPA properties
- `BaseLiquibaseSpringProperties`: Common Liquibase properties
- `AppLocalContainerEntityManagerFactory`: Factory for `LocalContainerEntityManagerFactoryBean` creation
- `SpringLiquibaseFactory`: Factory for `SpringLiquibase` creation

## Project Structure

```plaintext
src/
├── main/
│   ├── java/
│   │   └── com/aziz/multidbs/
│   │       └── config/
│   │           └── db/
│   │               ├── AppLocalContainerEntityManagerFactory.java
│   │               ├── BaseDataSourceProperties.java
│   │               ├── BaseJpaProperties.java
│   │               ├── BaseLiquibaseSpringProperties.java
│   │               └── SpringLiquibaseFactory.java
│   │               ├── users/
│   │               │   ├── AppUsersDatabaseConfiguration.java
│   │               │   ├── AppUsersDataSourceProperties.java
│   │               │   ├── AppUsersJpaProperties.java
│   │               │   ├── LiquibaseUsersDatabaseConfiguration.java
│   │               │   ├── LiquibaseUsersDataSourceProperties.java
│   │               │   └── LiquibaseUsersSpringProperties.java
│   │               ├── orders/
│   │               │   └── [Similar structure to users]
│   │               └── payments/
│   │                   └── [Similar structure to users]
│   └── resources/
│       ├── application.yml
│       └── db/
│           ├── users-database/
│           │   └── changelog/
│           │       └── db.changelog-root.xml
│           ├── orders-database/
│           └── payments-database/
├── scripts/
│   └── init.sql                # Database and user initialization
└── docker-compose.yml
```

## Configuration

### application.yml
```yaml
spring:
  users-database:
    jpa:
      properties:
        hibernate.hbm2ddl.auto: validate
      show-sql: true
    datasource:
      username: ${USERS_DB_APP_USER}
      password: ${USERS_DB_APP_PASSWORD}
      url: ${USERS_DB_URL}
    liquibase:
      username: ${USERS_DB_LIQUIBASE_USER}
      password: ${USERS_DB_LIQUIBASE_PASSWORD}
      url: ${USERS_DB_URL}
      properties:
        change-log: classpath:db/users-database/changelog/db.changelog-root.xml
    packages-to-scan: com.aziz.multidbs.domain.users
```
Similar configurations exist for `orders-database` and `payments-database`.

## Database Setup

The project uses PostgreSQL with the following structure for each database:

1. **Database Users**:
    - Liquibase User (DDL operations)
        - Full schema modification rights
        - Used for running migrations
    - Application User (DML operations)
        - Data manipulation rights only
        - Used by the application for regular operations

2. **Initial Setup**:
   ```sql
   -- Create database
   CREATE DATABASE users_db;
   
   -- Create and configure Liquibase user
   CREATE ROLE users_db_liquibase_user WITH LOGIN PASSWORD 'password';
   GRANT ALL ON SCHEMA public TO users_db_liquibase_user;
   
   -- Create and configure Application user
   CREATE ROLE users_db_app_user WITH LOGIN PASSWORD 'password';
   GRANT pg_read_all_data TO users_db_app_user;
   GRANT pg_write_all_data TO users_db_app_user;
   ```

## Quick Start

1. Clone the repository:
```bash
git clone https://github.com/abdulazezms/springboot-multidb-liquibase.git
cd springboot-multidb-liquibase
```

2. Start the application and databases:
```bash
docker-compose up
```

The Docker setup includes:
- PostgreSQL container
- Automatic database creation
- User setup with appropriate permissions
- Health check configuration


## Database Configuration Details

### Dual DataSource Pattern
The project implements a dual DataSource pattern for each database:

#### Liquibase DataSource
- **Purpose**: Schema migrations and DDL operations against the database.
- **Configuration Classes**:
    - `Liquibase{Database}DatabaseConfiguration`
    - `Liquibase{Database}DataSourceProperties`
- **User Privileges**: Full DDL access
- **Connection Lifecycle**:
    - Managed by HikariCP connection pool
    - Max connections are reserved on application startup
    - Connections/sessions moved from idle to active states on demand
    - Currently holds connection until application shutdown (see TODO section)
- **Example Configuration**:
```java
@Bean
public DataSource usersLiquibaseDataSource(LiquibaseUsersDataSourceProperties dataSourceProperties) {
    return dataSourceProperties
            .initializeDataSourceBuilder()
            .type(HikariDataSource.class)
            .build();
}
```

#### Application DataSource
- **Purpose**: Regular application operations (DML) against the database.
- **Configuration Classes**:
    - `App{Database}DatabaseConfiguration`
    - `App{Database}DataSourceProperties`
- **User Privileges**: Restricted to DML operations
- **Connection Lifecycle**:
    - Managed by HikariCP connection pool
    - Max connections are reserved on application startup
    - Connections/sessions moved from idle to active states on demand
- **Example Configuration**:
```java
@Bean
public DataSource usersDataSource(AppUsersDataSourceProperties dataSourceProperties) {
    return dataSourceProperties
            .initializeDataSourceBuilder()
            .type(HikariDataSource.class)
            .build();
}
```

### Key Differences

1. **User Permissions**
    - Liquibase DataSource: Full schema modification rights
    - Application DataSource: Data manipulation only

2. **Usage Pattern**
    - Liquibase DataSource: Used only during schema updates
    - Application DataSource: Used throughout application lifecycle

3. **Configuration Location**
    - Liquibase configuration under `spring.*-database.liquibase`
    - Application configuration under `spring.*-database.datasource`

## TODO

### Release Liquibase Connections After Migration

**Current Behavior**:
- Liquibase, through Hikari CP, caches connections to each database after migrations complete
- These connections remain until application shutdown
- Consumes database resources unnecessarily

**Proposed Solution**:
1. Implement a custom `SpringLiquibase` extension that releases connections after migration
2. Add a post-migration hook to handle this

## Testing

The project uses TestContainers for integration testing, providing an isolated test environment with real PostgreSQL databases.

### Test Configuration

- Uses PostgreSQL 17 Alpine image for testing
- Automatically initializes test databases using `init.sql` script
- Configures separate test profile with TestContainers support

### Key Testing Components

1. **Database Container Setup**
```java
@Container
static PostgreSQLContainer<?> databaseServer =
        new PostgreSQLContainer<>("postgres:17-alpine")
                .withCopyFileToContainer(
                        MountableFile.forClasspathResource(
                                "init.sql"), "/docker-entrypoint-initdb.d/"
                ).withAccessToHost(true)
                .withExposedPorts(5432);
```

2. **Test Environment Configuration**
- Uses `@ActiveProfiles("test")` for test-specific configurations
- Dynamically sets database port using TestContainers
```java
@BeforeAll
static void setUp() {
    System.setProperty("DB_HOST_PORT", 
            "localhost:" + databaseServer.getMappedPort(5432));
}
```

3. **Spring Context and Liquibase Migrations**
- `@SpringBootTest` annotation triggers the full Spring context initialization
- During context initialization, Liquibase automatically performs database migrations
- Each database's changelog is executed against its respective test database
- This ensures the test databases have the correct schema before tests run

4. **Integration Tests**
- A single simple integration test is written to ensure the correct datasource is used per repository. 
```mvn
./mvnw clean verify
- ```