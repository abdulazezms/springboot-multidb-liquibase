# Spring Boot Multi-Database with Liquibase

## Overview
This Spring Boot application demonstrates the configuration of multiple databases with Liquibase integration. 

## What is included?
- **Multi-Database Configuration**: Each domain (Users, Orders, and Payments) uses its own database.
- **Liquibase Integration**: Database migration management for each database. Each database has its changelog files organized in a directory named after the database.
- **Dedicated Liquibase User**: Each database has a dedicated user with DDL permissions for performing Liquibase updates.
- **Dedicated App User**: Each database utilizes a dedicated user with DML permissions for performing CRUD operations through the app.

## Project Structure

```
└── spring-boot-multi-database
├── scripts/
│   └── init.sql                       # Includes SQL setup instructions for creating the databases, users, and grants.
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/aziz/multidbs/
│   │   │       └── config/
│   │   │           └── db/            # Contains base Database classes with common properties and factories.
│   │   │               ├── users/     # Contains Database related configuration to allow Liquibase and the app to use their own separate DataSources when connecting to the users database.
│   │   │               ├── orders/    # Contains Database related configuration to allow Liquibase and the app to use their own separate DataSources when connecting to the orders database.
│   │   │               └── payments/  # Contains Database related configuration to allow Liquibase and the app to use their own separate DataSources when connecting to the payments database.
│   │   └── resources/
│   │       └── db/
│   │           ├── users-database/    # Liquibase changelogs for users database
│   │           ├── orders-database/   # Liquibase changelogs for orders database
│   │           └── payments-database/ # Liquibase changelogs for payments database
│   └── test/
│       └── java/
│           └── com/aziz/multidbs/
│               └── service/
│                   └── PaymentServiceTest.java     # Contains an integration test to ensure proper connection is made against all the databases.
└── docker-compose.yml                              # Docker Compose configuration
```

## Context Initialization High Level Steps
1. SpringLiquibase beans get registered (1 for each database).
2. Each of them will run the new changelog files, if any, against the corresponding database, using the credentials and properties configured in `application.yml` for that database.
3. App is initialized and 3 `LocalContainerEntityManagerFactoryBean` are exposed to be used by the app. One for each database. 
4. Whenever a JPA related logic is performed, the correct beans (transaction manager, entity manager factory, etc.) associated with that database will be used. 

## Usage
```shell
docker-compose up
```