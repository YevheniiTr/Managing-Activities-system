-- to do constraints
CREATE TABLE Application
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGSERIAL REFERENCES Users (id)    NOT NULL,
    status      varchar(128) DEFAULT 'EXPECT'      NOT NULL,
    org_name    VARCHAR(128)                       NOT NULL,
    org_phone   varchar(128)                       NOT NULL,
    title       varchar(128)                       not null,
    city        varchar(128)                       NOT NULL,
    st_location VARCHAR(128)                       NOT NULL,
    begin_date  timestamp                          NOT NULL,
    file_id     varchar(128) REFERENCES Files (id) NOT NULL
);
CREATE TABLE Files
(
    id        varchar(255) PRIMARY KEY,
--     tion_id BIGSERIAL REFERENCES Application (id) NOT NULL,
    f_name    varchar(128) not null,
    f_size    BIGINT       not null,
    mime_type varchar(256) not null,
    file_data oid          not null
);


CREATE TABLE Activity
(
    id           BIGSERIAL PRIMARY KEY,
    title        varchar(128)                    NOT NULL,
    description  text                            NOT NULL,
    amountSeats  int                             NOT NULL,
    organisation varchar(128)                    NOT NULL,
    genre        varchar(30)                     NOT NULL,
    activityType varchar                         NOT NULL,
    userID       BIGSERIAL REFERENCES users (id) NOT NULL
);

CREATE TABLE Street
(
    id         BIGSERIAL PRIMARY KEY,
    streetName VARCHAR(128)                   NOT NULL,
    cityID     BIGSERIAL REFERENCES City (id) NOT NULL
);
CREATE TABLE City
(
    id       BIGSERIAL PRIMARY KEY,
    cityName VARCHAR(128)
);

CREATE TABLE Venue
(
    id           BIGSERIAL PRIMARY KEY,
    cityID       BIGSERIAL REFERENCES City (id)  NOT NULL,
    userID       BIGSERIAL REFERENCES users (id) NOT NULL,
    title        VARCHAR(128)                    NOT NULL,
    maximumSeats int                             NOT NULL,
    rentPrice    int                             NOT NULL,
    description  text                            NOT NULL,
    adresIndex   varchar(128)                    NOT NULL
);
CREATE TYPE applicationStatus AS ENUM ('EXPECT', 'APPROVED', 'DECLINED');
CREATE TABLE applicationtogetvenue
(
    id         BIGSERIAL PRIMARY KEY,
    activityID BIGSERIAL REFERENCES Activity (id) NOT NULL,
    startDate  timestamp                          NOT NULL,
    endDate    timestamp                          NOT NULL,
    status     varchar(15)                        NOT NULL DEFAULT 'EXPECT'
);
CREATE TABLE Edge
(
    id          BIGSERIAL PRIMARY KEY,
    venueID     BIGSERIAL REFERENCES venue (id)                 NOT NULL,
    applVenueID BIGSERIAL REFERENCES ApplicationToGetVenue (id) NOT NULL,
    startDate   timestamp                                       NOT NULL,
    isMatching  bool DEFAULT 'false'                            NOT NULL,
    status      varchar(32) DEFAULT 'EXPECT',
    UNIQUE (venueID, applVenueID, startDate)
);
CREATE TABLE Profile
(
    id           BIGSERIAL PRIMARY KEY,
    userid       BIGSERIAL REFERENCES Users (id) NOT NULL,
    organisation varchar(50)                     NOT NULL,
    firstName    varchar(50)                     NOT NULL,
    surname      varchar(50)                     NOT NULL,
    phone        varchar(15)                     NOT NULL
);
CREATE TABLE userProfile
(
    profileID BIGSERIAL REFERENCES Profile (id) NOT NULL,
    userID    BIGSERIAL REFERENCES users (id)   NOT NULL
);
CREATE TABLE ArtistPage
(
    ArtistID   BIGSERIAL PRIMARY KEY,
    activityID BIGSERIAL REFERENCES Activity (id) NOT NULL,
    title      varchar(50)
    --photo
);
CREATE TABLE BanerPage
(
);
CREATE TABLE PlannedActivities
(
    id         BIGSERIAL PRIMARY KEY,
    activityId BIGSERIAL REFERENCES Activity (id) NOT NULL,
    venueId    BIGSERIAL REFERENCES Venue (id)    NOT NULL,
    endDate    TIMESTAMP                          NOT NULL,
    startDate  timestamp                          NOT NULL,
    status     varchar(15)                        NOT NULL DEFAULT 'PLANNED'
);


DROP TABLE ApplicationToGetVenue CASCADE;
DROP TABLE Edge;
DROP TABLE Venue CASCADE;
DROP TABLE City CASCADE;
DROP table street;
DROP TABLE Activity CASCADE;
DROP TABLE userProfile CASCADE;

DROP TABLE Profile CASCADE;

CREATE TABLE Edge2
(
    EID        BIGSERIAL PRIMARY KEY,
    VID        int       NOT NULL,
    AVID       int       NOT NULL,
    startDate  timestamp NOT NULL,
    isMatching bool DEFAULT 'false',
    UNIQUE (VID, AVID, startDate)
    --PRIMARY KEY (VID, AVID, startDate)
);
--vid = 1 venue id 1
--avid = 1  applTGV 1
--startDate = '2024-01-01 15:09:00'
--(YY-MM-DD HH:MM:SS)
-- Добавить первую запись
DROP TABLE Edge2;
INSERT INTO Edge2 (VID, AVID, startDate)
VALUES (1, 1, '2024-03-01 12:00:00');
INSERT INTO Edge2 (VID, AVID, startDate)
VALUES (2, 1, '2024-03-01 12:00:00');
-- Добавить вторую запись с теми же VID и AVID, но другим startDate
INSERT INTO Edge2 (VID, AVID, startDate)
VALUES (1, 1, '2024-03-02 12:00:00');
INSERT INTO City (cityName)
VALUES ('Вінниця'),          -- Вінницька область
       ('Луцьк'),            -- Волинська область
       ('Дніпро'),           -- Дніпропетровська область
       ('Донецьк'),          -- Донецька область
       ('Житомир'),          -- Житомирська область
       ('Ужгород'),          -- Закарпатська область
       ('Запоріжжя'),        -- Запорізька область
       ('Івано-Франківськ'), -- Івано-Франківська область
       ('Київ'),             -- Київська область
       ('Кропивницький'),    -- Кіровоградська область
       ('Луганськ'),         -- Луганська область
       ('Львів'),            -- Львівська область
       ('Миколаїв'),         -- Миколаївська область
       ('Одеса'),            -- Одеська область
       ('Полтава'),          -- Полтавська область
       ('Рівне'),            -- Рівненська область
       ('Суми'),             -- Сумська область
       ('Тернопіль'),        -- Тернопільська область
       ('Харків'),           -- Харківська область
       ('Херсон'),           -- Херсонська область
       ('Хмельницький'),     -- Хмельницька область
       ('Черкаси'),          -- Черкаська область
       ('Чернівці'),         -- Чернівецька область
       ('Чернігів'); -- Чернігівська область
SELECT *
FROM City;
SELECT *
FROM Users;
SELECT *
FROM Venue;
SELECT *
FROM users u
         LEFT JOIN venue v on u.id = v.userID;
SELECT *
FROM applicationtogetvenue;
INSERT INTO Venue (cityID, userID, title, maximumSeats,
                   rentPrice, description, adresIndex)
VALUES (14, 25, 'Конференц-зал "Еліт"', 100, 5000, 'Прекрасне місце для конференцій та зустрічей.', '02000'),
       (14, 25, 'Весільний ресторан "Роза"', 150, 10000, 'Ідеальне місце для весіль та великих заходів.', '43000'),
       (14, 25, 'Бізнес-центр "Сучасник"', 50, 3000, 'Оренда офісних приміщень для бізнесу.', '61001'),
       (14, 25, 'Кафе "Аромат кави"', 30, 2000, 'Затишне кафе для зустрічей та перерв.', '04050'),
       (14, 25, 'Тренажерний зал "СпортЛайф"', 50, 4000, 'Сучасний спортивний зал для тренувань.', '73000'),
       (9, 25, 'Готель "Комфорт"', 200, 12000, 'Зручні номери для комфортного перебування.', '58010'),
       (9, 25, 'Культурний центр "АртПростір"', 80, 6000, 'Місце для культурних подій та виставок.', '36007'),
       (9, 25, 'Ресторан "Смачна тарілка"', 40, 2500, 'Смачна їжа та затишна атмосфера.', '17032'),
       (9, 25, 'Концертний зал "Гармонія"', 300, 15000, 'Для великих музичних подій та концертів.', '49068'),
       (9, 25, '"КавоЗавод"', 20, 1500, 'Ароматна кава та вишукана атмосфера.', '01014');

INSERT INTO Activity (title, description, amountSeats, organisation, genre, activityType, userID)
VALUES ('Концерт "Музична гармонія"', 'Вечір прекрасної музики від талановитих виконавців.', 200, 'Музичний світ',
        'Класична', 'Концерт', 24),
       ('Театральна постановка "Ромео і Джульєтта"',
        'Захоплююча історія кохання, представлена в театральному виконанні.', 150, 'Театр "Емоція"', 'Драма', 'Театр',
        24);
INSERT INTO Activity (title, description, amountSeats, organisation, genre, activityType, userID)
VALUES ('Концерт "Весільна Симфонія"', 'Неймовірний концерт із участю відомих виконавців.', 300, 'Музична Компанія',
        'Симфонічна', 'Концерт', 24),
       ('Театральна постановка "Шекспір на Сцені"', 'Захоплююча інтерпретація класичних творів Вільяма Шекспіра.', 150,
        'Театральна Трупа "Драма"', 'Драма', 'Театр', 24);
INSERT INTO applicationtogetvenue (activityID, startDate, endDate)
VALUES (1, '2024-03-06 12:00:00', '2024-03-06 22:00:00'),
       (2, '2024-03-06 12:00:00', '2024-03-06 22:00:00'),
       (3, '2024-03-06 12:00:00', '2024-03-06 22:00:00');
INSERT INTO edge (venueID, applVenueID, startDate)
VALUES (1, 1, '2024-03-06 12:00'),
       (2, 1, '2024-03-06 12:00'),
       (3, 1, '2024-03-06 12:00'),
       (1, 2, '2024-03-06 12:00'),
       (2, 2, '2024-03-06 12:00'),
       (3, 3, '2024-03-06 12:00');

INSERT INTO edge (venueID, applVenueID, startDate)
VALUES (2, 3, '2024-03-06 12:00');

SELECT *
FROM Edge
WHERE startDate = '2024-03-06 12:00';
SELECT *
FROM Activity;
SELECT *
FROM applicationtogetvenue;
SELECT *
FROM Application;

SELECT v.*
FROM Venue v
         LEFT JOIN PlannedActivities pa
                   ON pa.venueId = v.id AND date(pa.startDate) <= now() AND date(pa.endDate) >= now()
WHERE pa.id IS NULL;

SELECT v.*
FROM Venue v
         LEFT JOIN PlannedActivities pa
                   ON pa.venueId = v.id AND pa.startDate::date <= CURRENT_DATE AND pa.endDate::date >= CURRENT_DATE
WHERE pa.id IS NULL;


SELECT *
FROM Edge e
         JOIN venue v on e.venueID = v.id
         JOIN applicationtogetvenue appl on e.applVenueID = appl.id
         JOIN activity a on appl.activityID = a.id
WHERE isMatching = true;
-- получить заявки по дате и для всех залов пользователя (теоретически)
SELECT DISTINCT applVenueID
FROM edge;

SELECT e.* FROM applicationtogetvenue a
JOIN Edge e on a.id = e.applvenueid
JOIN Venue v on e.venueid  = v.id
WHERE v.userid =25;

SELECT v.* FROM applicationtogetvenue a
                    JOIN Edge e on a.id = e.applvenueid
                    JOIN Venue v on e.venueid  = v.id
WHERE v.userid =25;

ALTER TABLE Edge ADD COLUMN  status varchar(32) not null default 'EXPECT';

SELECT
    e.*
FROM
    Edge e
        JOIN
    Venue v ON e.venueID = v.id
JOIN users  u on v.userid =u.id
WHERE
                v.userID = 25
  AND DATE(e.startDate) = '2024-05-02';


SELECT v.* FROM venue v
            LEFT JOIN PlannedActivities pa
              ON pa.venueId = v.id
              AND DATE(pa.startDate) <= '01-05-2024'
              AND DATE(pa.endDate) >= '01-05-2024'
            WHERE pa.id IS NULL;


SELECT v.* FROM venue v
                        LEFT JOIN PlannedActivities pa
                          ON pa.venueId = v.id
                          AND DATE(pa.startDate) <= '03-05-2024'
                          AND DATE(pa.endDate) >= '03-05-2024'
           LEFT JOIN Street s on v.streetid = s.id
           LEFT JOIN City c on s.cityid = c.id
                        WHERE pa.id IS NULL AND v.maximumseats >=1 AND c.cityName ='Львів';