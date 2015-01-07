CREATE TABLE `evo_passwords` (
    `psw_id` INT(11) NOT NULL AUTO_INCREMENT,
    `psw_usr_id` INT(11) NOT NULL,
    `psw_hash` VARCHAR(128) COLLATE UTF8_LITHUANIAN_CI DEFAULT NULL,
    `psw_salt` VARCHAR(128) COLLATE UTF8_LITHUANIAN_CI DEFAULT NULL,
    `psw_status` VARCHAR(100) COLLATE UTF8_LITHUANIAN_CI DEFAULT NULL,
    `psw_created` DATETIME DEFAULT NULL,
    `psw_next_change` DATETIME DEFAULT NULL,
    PRIMARY KEY (`psw_id`),
    KEY `fk_psw_usr_idx` (`psw_usr_id`),
    CONSTRAINT `fk_psw_usr_id` FOREIGN KEY (`psw_usr_id`)
        REFERENCES `evo_users` (`usr_id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=INNODB DEFAULT CHARSET=UTF8 COLLATE = UTF8_LITHUANIAN_CI;
