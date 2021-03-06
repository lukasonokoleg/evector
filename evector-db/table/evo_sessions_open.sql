CREATE TABLE IF NOT EXISTS `evector`.`evo_sessions_open` (
    `ses_id` INT(11) NOT NULL AUTO_INCREMENT,
    `ses_usr_id` INT(11) NOT NULL,
    `ses_key` VARCHAR(128) NOT NULL,
    PRIMARY KEY (`ses_id`),
    INDEX `fk_ses_open_usr_idx` (`ses_usr_id` ASC),
    UNIQUE INDEX `ses_open_key_uqx` (`ses_key` ASC),
    CONSTRAINT `fk_evo_sessions_open_evo_users` FOREIGN KEY (`ses_usr_id`)
        REFERENCES `evector`.`evo_users` (`usr_id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=INNODB DEFAULT CHARACTER SET=UTF8 COLLATE = UTF8_LITHUANIAN_CI