-- TODO: why this error in dBeaver, right in IDEA?
CREATE DATABASE city;
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `id`          INT(10) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '城市编号',
  `province_id` INT(10) UNSIGNED NOT NULL
  COMMENT '省份编号',
  `city_name`   VARCHAR(25)               DEFAULT NULL
  COMMENT '城市名称',
  `description` VARCHAR(99)               DEFAULT NULL
  COMMENT '描述',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

INSERT city VALUES (1, 1, 'SH', 'SH is a city that you cannot love.');