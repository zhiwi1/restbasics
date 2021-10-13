CREATE TABLE `certificates`
(
    `id`               BIGINT       NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(100) NOT NULL,
    `price`            DECIMAL(10) NULL,
    `create_date`      TIMESTAMP NULL,
    `last_update_date` TIMESTAMP NULL,
    `duration`         INT NULL,
    `description`      varchar(200) NULL
);
