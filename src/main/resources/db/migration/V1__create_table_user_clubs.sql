CREATE TABLE users
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT,
    email            VARCHAR(100) UNIQUE NOT NULL,
    username         VARCHAR(50) UNIQUE  NOT NULL,
    passwords        VARCHAR(150)        NOT NULL,
    themes           ENUM ('IT', 'Marketing', 'Traveling', 'Education', 'Healthcare', 'Finance', 'Engineering', 'Hospitality', 'Sales', 'Art and Design', 'Legal', 'Science', 'Customer Service', 'Human Resources', 'Manufacturing', 'Agriculture', 'Media and Journalism', 'Fashion', 'Real Estate', 'Nonprofit', 'Consulting', 'Research', 'Retail', 'Construction', 'Government', 'Automotive', 'Telecommunications', 'Music', 'Sports', 'Entertainment', 'Architecture', 'Pharmaceutical', 'Aerospace'),
    country          VARCHAR(50),
    level_of_english ENUM ('Beginner', 'Intermediate', 'Upper-Intermediate', 'Advanced', 'Mastery'),
    role             varchar(45)         NOT NULL,
    enabled          tinyint(4)          NOT NULL
);
CREATE TABLE clubs
(
    id                    BIGINT PRIMARY KEY AUTO_INCREMENT,
    current_date_creation DATE,
    time_start            DATETIME,
    time_end              DATETIME,
    club_descriptions     VARCHAR(255),
    themes                ENUM ('IT', 'Marketing', 'Traveling', 'Education', 'Healthcare', 'Finance', 'Engineering', 'Hospitality', 'Sales', 'Art and Design', 'Legal', 'Science', 'Customer Service', 'Human Resources', 'Manufacturing', 'Agriculture', 'Media and Journalism', 'Fashion', 'Real Estate', 'Nonprofit', 'Consulting', 'Research', 'Retail', 'Construction', 'Government', 'Automotive', 'Telecommunications', 'Music', 'Sports', 'Entertainment', 'Architecture', 'Pharmaceutical', 'Aerospace'),
    level_of_english      ENUM ('Beginner', 'Intermediate', 'Upper-Intermediate', 'Advanced', 'Mastery'),
    own_user_id           BIGINT,
    FOREIGN KEY (own_user_id) REFERENCES users (id)
);

CREATE TABLE exist_club_in_user
(
    id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    club_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (club_id) REFERENCES clubs (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE creating_club_in_user
(
    id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    club_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (club_id) REFERENCES clubs (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);



INSERT INTO users (email, username, passwords, themes, country, level_of_english, role, enabled)
VALUES ('user1@example.com', 'user1', '$2a$10$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJdCr2v2QbVFzu', 'IT', 'USA',
        'Intermediate', 'ROLE_ADMIN', 1),
       ('user2@example.com', 'user2', '$2a$10$zh/BHZ8.z9UWm0pCUysewejllLXRDRdpoYjhIbRVLVRCrFrgrocPG', 'Marketing',
        'Canada', 'Advanced', 'ROLE_ADMIN', 1),
       ('user3@example.com', 'user3', 'password3', 'Education', 'UK', 'Intermediate', 'ROLE_ADMIN', 1),
       ('user4@example.com', 'user4', 'password4', 'Finance', 'Australia', 'Mastery', 'ROLE_ADMIN', 1),
       ('user5@example.com', 'user5', 'password5', 'Healthcare', 'Germany', 'Beginner', 'ROLE_ADMIN', 1);


INSERT INTO clubs (current_date_creation, time_start, time_end, club_descriptions, themes, level_of_english,
                   own_user_id)
VALUES ('2023-09-15', '2023-09-20 15:00:00', '2023-09-20 17:00:00', 'Club 1 description', 'IT', 'Intermediate', 1),
       ('2023-09-16', '2023-09-21 14:00:00', '2023-09-21 16:00:00', 'Club 2 description', 'Marketing', 'Advanced', 2),
       ('2023-09-17', '2023-09-22 13:00:00', '2023-09-22 15:00:00', 'Club 3 description', 'Education', 'Intermediate',
        3),
       ('2023-09-18', '2023-09-23 12:00:00', '2023-09-23 14:00:00', 'Club 4 description', 'Finance', 'Mastery', 4),
       ('2023-09-19', '2023-09-24 11:00:00', '2023-09-24 13:00:00', 'Club 5 description', 'Healthcare', 'Beginner', 5);


INSERT INTO exist_club_in_user (club_id, user_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5);


INSERT INTO creating_club_in_user (club_id, user_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5);

