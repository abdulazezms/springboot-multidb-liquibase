-- Create the databases
CREATE DATABASE users_db;
CREATE DATABASE orders_db;
CREATE DATABASE payments_db;

-- ========================================= users DB =========================================
\c users_db

-- Create the first user with DDL access (Liquibase user)
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'users_db_liquibase_user') THEN
CREATE ROLE users_db_liquibase_user WITH LOGIN PASSWORD 'users_db_liquibase_password';
END IF;
END $$;

-- Grant full DDL access to users_db_liquibase_user (CREATE, ALTER, DROP, etc.)
GRANT USAGE ON SCHEMA public TO users_db_liquibase_user;
GRANT ALL ON SCHEMA public TO users_db_liquibase_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO users_db_liquibase_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO users_db_liquibase_user;
ALTER DEFAULT PRIVILEGES FOR ROLE users_db_liquibase_user IN SCHEMA public GRANT ALL ON TABLES TO users_db_liquibase_user;
ALTER DEFAULT PRIVILEGES FOR ROLE users_db_liquibase_user IN SCHEMA public GRANT ALL ON SEQUENCES TO users_db_liquibase_user;

-- Create the second user with DML access (App user)
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'users_db_app_user') THEN
CREATE ROLE users_db_app_user WITH LOGIN PASSWORD 'users_db_app_password';
END IF;
END $$;

-- Grant full DML access to users_db_app_user (SELECT, INSERT, UPDATE, DELETE)
GRANT pg_read_all_data TO users_db_app_user;
GRANT pg_write_all_data TO users_db_app_user;


-- ========================================= orders DB =========================================
\c orders_db

-- Create the first user with DDL access (Liquibase user)
DO $$
BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'orders_db_liquibase_user') THEN
CREATE ROLE orders_db_liquibase_user WITH LOGIN PASSWORD 'orders_db_liquibase_password';
END IF;
END $$;

-- Grant full DDL access to orders_db_liquibase_user (CREATE, ALTER, DROP, etc.)
GRANT USAGE ON SCHEMA public TO orders_db_liquibase_user;
GRANT ALL ON SCHEMA public TO orders_db_liquibase_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO orders_db_liquibase_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO orders_db_liquibase_user;
ALTER DEFAULT PRIVILEGES FOR ROLE orders_db_liquibase_user IN SCHEMA public GRANT ALL ON TABLES TO orders_db_liquibase_user;
ALTER DEFAULT PRIVILEGES FOR ROLE orders_db_liquibase_user IN SCHEMA public GRANT ALL ON SEQUENCES TO orders_db_liquibase_user;

-- Create the second user with DML access (App user)
DO $$
BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'orders_db_app_user') THEN
CREATE ROLE orders_db_app_user WITH LOGIN PASSWORD 'orders_db_app_password';
END IF;
END $$;

-- Grant full DML access to orders_db_app_user (SELECT, INSERT, UPDATE, DELETE)
GRANT pg_read_all_data TO orders_db_app_user;
GRANT pg_write_all_data TO orders_db_app_user;


-- ========================================= payments DB =========================================
\c payments_db

-- Create the first user with DDL access (Liquibase user)
DO $$
BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'payments_db_liquibase_user') THEN
CREATE ROLE payments_db_liquibase_user WITH LOGIN PASSWORD 'payments_db_liquibase_password';
END IF;
END $$;

-- Grant full DDL access to payments_db_liquibase_user (CREATE, ALTER, DROP, etc.)
GRANT USAGE ON SCHEMA public TO payments_db_liquibase_user;
GRANT ALL ON SCHEMA public TO payments_db_liquibase_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO payments_db_liquibase_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO payments_db_liquibase_user;
ALTER DEFAULT PRIVILEGES FOR ROLE payments_db_liquibase_user IN SCHEMA public GRANT ALL ON TABLES TO payments_db_liquibase_user;
ALTER DEFAULT PRIVILEGES FOR ROLE payments_db_liquibase_user IN SCHEMA public GRANT ALL ON SEQUENCES TO payments_db_liquibase_user;

-- Create the second user with DML access (App user)
DO $$
BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'payments_db_app_user') THEN
CREATE ROLE payments_db_app_user WITH LOGIN PASSWORD 'payments_db_app_password';
END IF;
END $$;

-- Grant full DML access to payments_db_app_user (SELECT, INSERT, UPDATE, DELETE)
GRANT pg_read_all_data TO payments_db_app_user;
GRANT pg_write_all_data TO payments_db_app_user;

