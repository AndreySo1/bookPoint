-- alter table room
--     add description varchar(255);

CREATE TABLE migration_test (
                       id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                       text VARCHAR(50) NOT NULL
);