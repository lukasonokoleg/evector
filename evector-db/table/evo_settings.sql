CREATE TABLE IF NOT EXISTS `evector`.`evo_settings` (
    `set_id` INT(11) NOT NULL,
    `set_guid` VARCHAR(48) NULL,
    `set_name` VARCHAR(250) NULL,
    `set_value` VARCHAR(512) NULL,
    `set_type` VARCHAR(45) NULL,
    PRIMARY KEY (`set_id`),
    UNIQUE INDEX `guid_name_value_type_uqx` (`set_guid` ASC , `set_name` ASC)
)  ENGINE=INNODB