drop database payment;

create database payment;

use payment;

CREATE TABLE IF NOT EXISTS `users` (
    `id`         bigint UNSIGNED primary key auto_increment,
    `first_name` varchar(191)  NOT NULL,
    `last_name`  varchar(191)  NOT NULL,
    `email`      varchar(191)  NOT NULL,
    `password`   varchar(191)  NOT NULL,
    `balance`    DECIMAL(9, 2) NOT NULL default 8.00,
    `created_at` timestamp              DEFAULT current_timestamp,
    `updated_at` timestamp              DEFAULT NULL

);

INSERT INTO `users` (`first_name`, `last_name`, `email`, `password`, `balance`) VALUES
('arthur', 'martirosyan', 'arthur@mail.ru','$2a$10$1IKBKP8QIEF6D2Vjb0drqOieb3k4UsAcyy9axapWanxKM9Yt41yta',8.00),
('john', 'smith', 'john@mail.ru','$2a$10$1IKBKP8QIEF6D2Vjb0drqOieb3k4UsAcyy9axapWanxKM9Yt41yta',8.00);


CREATE TABLE IF NOT EXISTS `orders` (
    `id`         int(10) UNSIGNED NOT NULL,
    `user_id`    int(10) UNSIGNED      DEFAULT NULL,
    `bought`     DECIMAL(9, 2)    NOT NULL,
    `created_at` timestamp        NULL DEFAULT NULL,
    `updated_at` timestamp        NULL DEFAULT NULL,
    constraint user_id_foreign_key foreign key (user_id) references users (id)
);

CREATE TABLE IF NOT EXISTS `token` (
    `id`         int(10) UNSIGNED NOT NULL,
    `token` varchar(191)  NOT NULL,
    `created_at` timestamp        NULL DEFAULT NULL,
    `updated_at` timestamp        NULL DEFAULT NULL
)
