CREATE TABLE users
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT,
    email            VARCHAR(100) UNIQUE,
    username         VARCHAR(50),
    passwords        VARCHAR(150),
    themes           ENUM ('IT', 'Marketing', 'Traveling', 'Education', 'Healthcare', 'Finance', 'Engineering', 'Hospitality', 'Sales', 'Art and Design', 'Legal', 'Science', 'Customer Service', 'Human Resources', 'Manufacturing', 'Agriculture', 'Media and Journalism', 'Fashion', 'Real Estate', 'Nonprofit', 'Consulting', 'Research', 'Retail', 'Construction', 'Government', 'Automotive', 'Telecommunications', 'Music', 'Sports', 'Entertainment', 'Architecture', 'Pharmaceutical', 'Aerospace'),
    country          VARCHAR(50),
    level_of_english ENUM ('Beginner', 'Intermediate', 'Upper-Intermediate', 'Advanced', 'Mastery')
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



INSERT INTO users (email, passwords, themes, country, level_of_english)
VALUES ('example@email.com', 'password123', 'IT', 'USA', 'Advanced'),
       ('another@email.com', 'securepassword', 'Marketing', 'Canada', 'Intermediate');



INSERT INTO clubs (current_date_creation, time_start, time_end, club_descriptions, themes, level_of_english, own_user_id)
VALUES ('2023-09-14', '2023-09-14 14:00:00', '2023-09-14 16:00:00', 'Club for IT enthusiasts', 'IT', 'Advanced', 1),
       ('2023-09-15', '2023-09-15 18:00:00', '2023-09-15 20:00:00', 'Marketing club meeting', 'Marketing',
        'Intermediate', 2);



INSERT INTO exist_club_in_user (club_id, user_id)
VALUES (1, 1);


INSERT INTO creating_club_in_user (club_id, user_id)
VALUES (2, 2);