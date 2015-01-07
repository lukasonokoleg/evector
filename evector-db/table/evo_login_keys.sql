CREATE TABLE IF NOT EXISTS `evector`.`evo_login_keys` (
    `lgk_id` INT(11) NOT NULL,
    `lgk_usr_id` INT(11) NOT NULL,
    `lgk_key` VARCHAR(128) NOT NULL,
    `lgk_workplace` VARCHAR(128) NULL,
    `lgk_created` DATETIME NULL,
    `lgk_updated` DATETIME NULL,
    `lgk_expired` DATETIME NULL,
    PRIMARY KEY (`lgk_id`),
    INDEX `fk_lgk_usr_idx` (`lgk_usr_id` ASC),
    CONSTRAINT `fk_lgk_usr_id` FOREIGN KEY (`lgk_usr_id`)
        REFERENCES `evector`.`evo_users` (`usr_id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=INNODB