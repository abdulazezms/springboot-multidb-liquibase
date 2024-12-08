-- Connect to the 'users' database
\c users_db;

-- Insert a record into the t_user table
INSERT INTO t_user (id, name)
VALUES ('2', 'User2');

-- Connect to the 'payments' database
\c payments_db;
-- Insert a record into the t_payment table
INSERT INTO t_payment (id, user_id, order_id, is_successful)
VALUES ('2',
        '2',
        '2',
        true);