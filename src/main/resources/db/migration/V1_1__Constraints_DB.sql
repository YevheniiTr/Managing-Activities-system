--Users
ALTER TABLE Users
    ADD CONSTRAINT email_unique UNIQUE (email);
--Roles
ALTER TABLE Roles
    ADD CONSTRAINT kind_of_roles_name CHECK (name in ('USER', 'MODER', 'ADMIN'));
