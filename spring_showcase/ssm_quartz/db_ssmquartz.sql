SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for `qrtz_blob_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `sched_name`    VARCHAR(120) NOT NULL,
  `trigger_name`  VARCHAR(80)  NOT NULL,
  `trigger_group` VARCHAR(80)  NOT NULL,
  `blob_data`     BLOB,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for `qrtz_calendars`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `sched_name`    VARCHAR(120) NOT NULL,
  `calendar_name` VARCHAR(80)  NOT NULL,
  `calendar`      BLOB         NOT NULL,
  PRIMARY KEY (`calendar_name`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for `qrtz_cron_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `sched_name`      VARCHAR(120) NOT NULL,
  `trigger_name`    VARCHAR(80)  NOT NULL,
  `trigger_group`   VARCHAR(80)  NOT NULL,
  `cron_expression` VARCHAR(120) NOT NULL,
  `time_zone_id`    VARCHAR(80) DEFAULT NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers`
VALUES ('quartzScheduler', 'triggerName', 'triggerGroupName', '*/10 * * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers`
VALUES ('quartzScheduler', 'triggerName2', 'triggerGroupName2', '*/10 * * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers`
VALUES ('quartzScheduler', 'triggerName3', 'triggerGroupName3', '*/15 * * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers`
VALUES ('quartzScheduler', 'triggerName4', 'triggerGroupName4', '*/5 * * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('quartzScheduler', '测试触发', '测试触发组', '*/15 * * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for `qrtz_fired_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `sched_name`        VARCHAR(120) NOT NULL,
  `entry_id`          VARCHAR(95)  NOT NULL,
  `trigger_name`      VARCHAR(80)  NOT NULL,
  `trigger_group`     VARCHAR(80)  NOT NULL,
  `instance_name`     VARCHAR(80)  NOT NULL,
  `fired_time`        BIGINT(20)   NOT NULL,
  `sched_time`        BIGINT(20)   NOT NULL,
  `priority`          INT(11)      NOT NULL,
  `state`             VARCHAR(16)  NOT NULL,
  `job_name`          VARCHAR(80) DEFAULT NULL,
  `job_group`         VARCHAR(80) DEFAULT NULL,
  `is_nonconcurrent`  INT(11)     DEFAULT NULL,
  `requests_recovery` INT(11)     DEFAULT NULL,
  PRIMARY KEY (`sched_name`, `entry_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------
INSERT INTO `qrtz_fired_triggers` VALUES
  ('dufy_test', 'NON_CLUSTERED1487230171387', 'trigger1', 'group1', 'NON_CLUSTERED', '1487230212028', '1487230214000',
                '5', 'ACQUIRED', NULL, NULL, '0', '0');
INSERT INTO `qrtz_fired_triggers` VALUES
  ('quartzScheduler', 'slcf5015160860473291516086047378', 'triggerName', 'triggerGroupName', 'slcf501516086047329',
                      '1516086260152', '1516086260000', '5', 'EXECUTING', 'jobName', 'jobGroupName', '0', '0');
INSERT INTO `qrtz_fired_triggers` VALUES
  ('quartzScheduler', 'slcf5015160860473291516086047381', 'triggerName', 'triggerGroupName', 'slcf501516086047329',
                      '1516086270002', '1516086270000', '5', 'EXECUTING', 'jobName', 'jobGroupName', '0', '0');
INSERT INTO `qrtz_fired_triggers` VALUES
  ('quartzScheduler', 'slcf5015160860473291516086047384', 'triggerName', 'triggerGroupName', 'slcf501516086047329',
                      '1516086280001', '1516086280000', '5', 'EXECUTING', 'jobName', 'jobGroupName', '0', '0');
INSERT INTO `qrtz_fired_triggers` VALUES
  ('quartzScheduler', 'slcf5015160860473291516086047387', 'triggerName', 'triggerGroupName', 'slcf501516086047329',
                      '1516086290002', '1516086290000', '5', 'EXECUTING', 'jobName', 'jobGroupName', '0', '0');
INSERT INTO `qrtz_fired_triggers` VALUES
  ('quartzScheduler', 'slcf5015160860473291516086047390', 'triggerName', 'triggerGroupName', 'slcf501516086047329',
                      '1516086300004', '1516086300000', '5', 'EXECUTING', 'jobName', 'jobGroupName', '0', '0');
INSERT INTO `qrtz_fired_triggers` VALUES
  ('quartzScheduler', 'slcf5015160860473291516086047393', 'triggerName', 'triggerGroupName', 'slcf501516086047329',
                      '1516086310001', '1516086310000', '5', 'EXECUTING', 'jobName', 'jobGroupName', '0', '0');
INSERT INTO `qrtz_fired_triggers` VALUES
  ('quartzScheduler', 'slcf5015160860473291516086047396', 'triggerName', 'triggerGroupName', 'slcf501516086047329',
                      '1516086315030', '1516086320000', '5', 'ACQUIRED', NULL, NULL, '0', '0');

-- ----------------------------
-- Table structure for `qrtz_job_details`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `sched_name`        VARCHAR(120) NOT NULL,
  `job_name`          VARCHAR(80)  NOT NULL,
  `job_group`         VARCHAR(80)  NOT NULL,
  `description`       VARCHAR(120) DEFAULT NULL,
  `job_class_name`    VARCHAR(128) NOT NULL,
  `is_durable`        INT(11)      NOT NULL,
  `is_nonconcurrent`  INT(11)      NOT NULL,
  `is_update_data`    INT(11)      NOT NULL,
  `requests_recovery` INT(11)      NOT NULL,
  `job_data`          BLOB,
  PRIMARY KEY (`sched_name`, `job_name`, `job_group`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES
  ('quartzScheduler', 'jobName', 'jobGroupName', NULL, 'com.johnny.task.TestRunningJob', '0', '0', '0', '0',
   0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000017400076D657373616765740006E69EADE99B847800);
INSERT INTO `qrtz_job_details` VALUES
  ('quartzScheduler', 'jobName2', 'jobGroupName2', NULL, 'com.johnny.task.SecondJob', '0', '0', '0', '0',
   0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000017400076D657373616765740006E69EADE99B847800);
INSERT INTO `qrtz_job_details` VALUES
  ('quartzScheduler', 'jobName3', 'jobGroupName3', NULL, 'com.johnny.task.AlertJob', '0', '0', '0', '0',
   0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000017400076D657373616765740006E69EADE99B847800);
INSERT INTO `qrtz_job_details` VALUES
  ('quartzScheduler', 'jobName4', 'jobGroupName4', NULL, 'com.johnny.task.HelloWorldJob', '0', '0', '0', '0',
   0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000017400076D657373616765740006E69EADE99B847800);
INSERT INTO `qrtz_job_details` VALUES
  ('quartzScheduler', '测试', '测试组', NULL, 'com.johnny.task.AlertJob', '0', '0', '0', '0',
   0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000017400076D657373616765740006E69EADE99B847800);

-- ----------------------------
-- Table structure for `qrtz_locks`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `sched_name` VARCHAR(120) NOT NULL,
  `lock_name`  VARCHAR(40)  NOT NULL,
  PRIMARY KEY (`sched_name`, `lock_name`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('dufy_test', 'TRIGGER_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('quartzScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('quartzScheduler', 'TRIGGER_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('scheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for `qrtz_paused_trigger_grps`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `sched_name`    VARCHAR(120) NOT NULL,
  `trigger_group` VARCHAR(80)  NOT NULL,
  PRIMARY KEY (`sched_name`, `trigger_group`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for `qrtz_scheduler_state`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `sched_name`        VARCHAR(120) NOT NULL,
  `instance_name`     VARCHAR(80)  NOT NULL,
  `last_checkin_time` BIGINT(20)   NOT NULL,
  `checkin_interval`  BIGINT(20)   NOT NULL,
  PRIMARY KEY (`sched_name`, `instance_name`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('quartzScheduler', 'slcf501516086047329', '1516086317756', '15000');

-- ----------------------------
-- Table structure for `qrtz_simple_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `sched_name`      VARCHAR(120) NOT NULL,
  `trigger_name`    VARCHAR(80)  NOT NULL,
  `trigger_group`   VARCHAR(80)  NOT NULL,
  `repeat_count`    BIGINT(20)   NOT NULL,
  `repeat_interval` BIGINT(20)   NOT NULL,
  `times_triggered` BIGINT(20)   NOT NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for `qrtz_simprop_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `sched_name`    VARCHAR(120) NOT NULL,
  `TRIGGER_NAME`  VARCHAR(200) NOT NULL,
  `TRIGGER_GROUP` VARCHAR(200) NOT NULL,
  `STR_PROP_1`    VARCHAR(512)   DEFAULT NULL,
  `STR_PROP_2`    VARCHAR(512)   DEFAULT NULL,
  `STR_PROP_3`    VARCHAR(512)   DEFAULT NULL,
  `INT_PROP_1`    INT(11)        DEFAULT NULL,
  `INT_PROP_2`    INT(11)        DEFAULT NULL,
  `LONG_PROP_1`   BIGINT(20)     DEFAULT NULL,
  `LONG_PROP_2`   BIGINT(20)     DEFAULT NULL,
  `DEC_PROP_1`    DECIMAL(13, 4) DEFAULT NULL,
  `DEC_PROP_2`    DECIMAL(13, 4) DEFAULT NULL,
  `BOOL_PROP_1`   VARCHAR(1)     DEFAULT NULL,
  `BOOL_PROP_2`   VARCHAR(1)     DEFAULT NULL,
  PRIMARY KEY (`sched_name`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for `qrtz_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `sched_name`     VARCHAR(120) NOT NULL,
  `trigger_name`   VARCHAR(80)  NOT NULL,
  `trigger_group`  VARCHAR(80)  NOT NULL,
  `job_name`       VARCHAR(80)  NOT NULL,
  `job_group`      VARCHAR(80)  NOT NULL,
  `description`    VARCHAR(120) DEFAULT NULL,
  `next_fire_time` BIGINT(20)   DEFAULT NULL,
  `prev_fire_time` BIGINT(20)   DEFAULT NULL,
  `priority`       INT(11)      DEFAULT NULL,
  `trigger_state`  VARCHAR(16)  NOT NULL,
  `trigger_type`   VARCHAR(8)   NOT NULL,
  `start_time`     BIGINT(20)   NOT NULL,
  `end_time`       BIGINT(20)   DEFAULT NULL,
  `calendar_name`  VARCHAR(80)  DEFAULT NULL,
  `misfire_instr`  SMALLINT(6)  DEFAULT NULL,
  `job_data`       BLOB,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`),
  KEY `sched_name` (`sched_name`, `job_name`, `job_group`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES
  ('quartzScheduler', 'triggerName', 'triggerGroupName', 'jobName', 'jobGroupName', NULL, '1516086320000',
                      '1516086310000', '5', 'ACQUIRED', 'CRON', '1516086258000', '0', NULL, '0', '');
INSERT INTO `qrtz_triggers` VALUES
  ('quartzScheduler', 'triggerName2', 'triggerGroupName2', 'jobName2', 'jobGroupName2', NULL, '1516080910000',
                      '1516080900000', '5', 'PAUSED', 'CRON', '1516074903000', '0', NULL, '0', '');
INSERT INTO `qrtz_triggers` VALUES
  ('quartzScheduler', 'triggerName3', 'triggerGroupName3', 'jobName3', 'jobGroupName3', NULL, '1516086270000',
                      '1516086255000', '5', 'PAUSED', 'CRON', '1516081072000', '0', NULL, '0', '');
INSERT INTO `qrtz_triggers` VALUES
  ('quartzScheduler', 'triggerName4', 'triggerGroupName4', 'jobName4', 'jobGroupName4', NULL, '1516086320000',
                      '1516086315000', '5', 'WAITING', 'CRON', '1516084468000', '0', NULL, '0', '');
INSERT INTO `qrtz_triggers` VALUES
  ('quartzScheduler', '测试触发', '测试触发组', '测试', '测试组', NULL, '1516081365000', '1516081350000', '5', 'PAUSED', 'CRON',
   '1516081049000', '0', NULL, '0', '');

-- ----------------------------
-- Table structure for `tb_schedulejobs`
-- ----------------------------
DROP TABLE IF EXISTS `tb_schedulejobs`;
CREATE TABLE `tb_schedulejobs` (
  `job_id`          INT(11)      NOT NULL AUTO_INCREMENT,
  `create_time`     DATETIME              DEFAULT NULL,
  `update_time`     DATETIME              DEFAULT NULL,
  `job_name`        VARCHAR(255)          DEFAULT NULL,
  `job_group`       VARCHAR(255)          DEFAULT NULL,
  `job_status`      VARCHAR(255)          DEFAULT NULL,
  `cron_expression` VARCHAR(255) NOT NULL,
  `is_concurrent`   VARCHAR(255)          DEFAULT NULL,
  `description`     VARCHAR(255)          DEFAULT NULL,
  `bean_class`      VARCHAR(255)          DEFAULT NULL,
  `method_name`     VARCHAR(255)          DEFAULT NULL,
  `spring_id`       VARCHAR(255)          DEFAULT NULL,
  PRIMARY KEY (`job_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of tb_schedulejobs
-- ----------------------------

-- ----------------------------
-- Table structure for `user_t`
-- ----------------------------
DROP TABLE IF EXISTS `user_t`;
CREATE TABLE `user_t` (
  `id`        INT(11)      NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(40)  NOT NULL,
  `password`  VARCHAR(255) NOT NULL,
  `age`       INT(4)       NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of user_t
-- ----------------------------
INSERT INTO `user_t` VALUES ('1', 'admin', '123456', '24');
