CREATE TABLE `geographic`
(
    `id`       bigint(20)          NOT NULL COMMENT '主键',
    `pid`      bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '父ID',
    `adcode`   varchar(50)         NOT NULL DEFAULT '' COMMENT '区域编码',
    `citycode` varchar(50)         NOT NULL DEFAULT '' COMMENT '城市编码',
    `name`     varchar(255)                 DEFAULT '' COMMENT '行政区名称',
    `center`   varchar(255)                 DEFAULT '' COMMENT '区域中心点',
    `level`    varchar(20)                  DEFAULT NULL COMMENT '行政区划级别\r\ncountry:国家\r\nprovince:省份（直辖市会在province和city显示）\r\ncity:市（直辖市会在province和city显示）\r\ndistrict:区县\r\nstreet:街道',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC;

CREATE TABLE `user`
(
    `id`          bigint(20) unsigned NOT NULL COMMENT '用户ID',
    `account`     varchar(50)         NOT NULL COMMENT '登录账号名',
    `avatar`      varchar(255)        NOT NULL DEFAULT '' COMMENT '头像',
    `country_id`  bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '国家ID',
    `city_id`     bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '城市ID',
    `province_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '省份ID',
    `district_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '区县ID',
    `street`      varchar(100)        NOT NULL DEFAULT '' COMMENT '街道',
    `email`       varchar(50)         NOT NULL DEFAULT '' COMMENT '邮箱',
    `prefix`      varchar(5)          NOT NULL DEFAULT '' COMMENT '区域编码',
    `mobile`      char(15)            NOT NULL DEFAULT '' COMMENT '手机号',
    `invalid`     tinyint(1) unsigned          DEFAULT '0' COMMENT '是否禁用， 0 正常 1 已禁用',
    `nickname`    varchar(50)         NOT NULL DEFAULT '' COMMENT '昵称',
    `password`    varchar(255)        NOT NULL DEFAULT '' COMMENT '密码',
    `profile`     varchar(255)        NOT NULL DEFAULT '' COMMENT '个人简介',
    `title`       varchar(100)        NOT NULL DEFAULT '' COMMENT '头衔',
    `create_at`   datetime(3)                  DEFAULT NULL COMMENT '创建时间',
    `create_by`   bigint(20) unsigned          DEFAULT '0' COMMENT '创建人ID',
    `update_at`   datetime(3)                  DEFAULT NULL COMMENT '更新时间',
    `update_by`   bigint(20) unsigned          DEFAULT NULL COMMENT '更新人ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC;


CREATE TABLE `SPRING_SESSION`
(
    `PRIMARY_ID`            char(36)   NOT NULL,
    `SESSION_ID`            char(36)   NOT NULL,
    `CREATION_TIME`         bigint(20) NOT NULL,
    `LAST_ACCESS_TIME`      bigint(20) NOT NULL,
    `MAX_INACTIVE_INTERVAL` int(11)    NOT NULL,
    `EXPIRY_TIME`           bigint(20) NOT NULL,
    `PRINCIPAL_NAME`        varchar(100) DEFAULT NULL,
    PRIMARY KEY (`PRIMARY_ID`),
    UNIQUE KEY `SPRING_SESSION_IX1` (`SESSION_ID`),
    KEY `SPRING_SESSION_IX2` (`EXPIRY_TIME`),
    KEY `SPRING_SESSION_IX3` (`PRINCIPAL_NAME`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `SPRING_SESSION_ATTRIBUTES`
(
    `SESSION_PRIMARY_ID` char(36)     NOT NULL,
    `ATTRIBUTE_NAME`     varchar(191) NOT NULL,
    `ATTRIBUTE_BYTES`    blob         NOT NULL,
    PRIMARY KEY (`SESSION_PRIMARY_ID`, `ATTRIBUTE_NAME`),
    CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `SPRING_SESSION` (`PRIMARY_ID`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;