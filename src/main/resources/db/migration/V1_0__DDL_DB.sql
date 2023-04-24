CREATE TABLE Users
(
    id       BIGSERIAL PRIMARY KEY,
    email    varchar(128) NOT NULL,
    password varchar(128) NOT NULL
);

CREATE TABLE Roles
(
    id   BIGSERIAL PRIMARY KEY,
    name varchar(32) NOT NULL
);

CREATE TABLE user_role
(
    user_id BIGSERIAL REFERENCES Users (id) NOT NULL,
    role_id BIGSERIAL REFERENCES Roles (id) NOT NULL
);

CREATE TABLE Profile
(
    id       BIGSERIAL PRIMARY KEY,
    user_id  BIGSERIAL REFERENCES Users (id) NOT NULL,
    name     VARCHAR(128)                    NOT NULL,
    age      INT                             NOT NULL,
    gender   INT                             NOT NULL,
    git_link VARCHAR(128),
    info     TEXT
);

CREATE TABLE Lang
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL
);

CREATE TABLE Profile_Lang
(
    profile_id BIGSERIAL REFERENCES Profile (id) NOT NULL,
    lang_id    BIGSERIAL REFERENCES Lang (id)    NOT NULL
);

CREATE TABLE Project
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(256)                      NOT NULL,
    leader_id   BIGSERIAL REFERENCES Profile (id) NOT NULL,
    description TEXT                              NOT NULL,
    created_at  TIMESTAMP                         NOT NULL
);

CREATE TABLE Project_Member
(
    project_id BIGSERIAL REFERENCES Project (id) NOT NULL,
    user_id    BIGSERIAL REFERENCES Profile (id) NOT NULL
);

CREATE TABLE Project_Technology
(
    project_id BIGSERIAL REFERENCES Project (id) NOT NULL,
    lang_id    BIGSERIAL REFERENCES Lang (id) NOT NULL
);

CREATE TABLE Project_Candidate
(
    project_id BIGSERIAL REFERENCES Project (id) NOT NULL,
    user_id    BIGSERIAL REFERENCES Profile (id) NOT NULL
);