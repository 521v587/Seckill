-- 数据初始化脚本
-- 创建数据库
CREATE database seckill;
-- 使用数据库
use seckill;
-- 创建数据表
CREATE TABLE seckill(
	seckill_id BIGINT NOT NULL auto_increment COMMENT '商品库存id',
	name VARCHAR(120) NOT NULL COMMENT '商品名称',
	number int NOT NULL COMMENT '商品数量',
	start_time TIMESTAMP NOT NULL COMMENT '开始时间',
	end_time TIMESTAMP NOT NULL COMMENT '结束时间',
	create_time TIMESTAMP NOT NULL COMMENT '创建时间',
	PRIMARY KEY (seckill_id),
	KEY idx_start_time(start_time),
	KEY idx_end_time(end_time),
	KEY idx_create_time(create_time)
)ENGINE=INNODB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT '商品秒杀表';

-- 初始化数据
INSERT INTO
seckill(name,number,start_time,end_time)
VALUES
('666元秒杀血糖仪',200,'2018-3-19 00:00:00','2018-3-20 00:00:00'),
('777元秒杀液晶电视',700,'2018-3-19 00:00:00','2018-3-20 00:00:00'),
('222元秒杀绝版皮肤',240,'2018-3-19 00:00:00','2018-3-20 00:00:00'),
('999元秒杀海尔空调',250,'2018-3-19 00:00:00','2018-3-20 00:00:00');

-- 秒杀成功明细表
CREATE TABLE success_killed(
seckill_id bigint NOT NULL COMMENT '秒杀商品id',
user_phone bigint NOT NULL COMMENT '用户手机号',
state tinyint NOT NULL DEFAULT -1 COMMENT '状态提示：-1：无效，0：成功 1：已付款 2：已发货',
create_time TIMESTAMP NOT NULL COMMENT '创建时间',
PRIMARY KEY (seckill_id, user_phone),/*联合主键*/
KEY idx_create_time(create_time)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '秒杀成功明细表';

-- 连接数据库控制台
mysql -uroot -p123