##用户表
DROP TABLE IF EXISTS user;
CREATE TABLE user
(
user_id     VARCHAR(10) NOT NULL,
user_name   VARCHAR(16) NOT NULL,
pwd         VARCHAR(32) NOT NULL,
login_times INTEGER DEFAULT 0 NOT NULL, ##登录次数
last_time   VARCHAR(20),                ##最后登录时间
remark      VARCHAR(64),
PRIMARY KEY (user_id)
);

##用户数据
DELETE FROM user WHERE 1=1;

##测试用户
INSERT INTO user (user_id,user_name,pwd) VALUES ('0123456789','test','D419FF6833C35F018659BBAAF0F31C32');

##查询数据
SELECT user_id,user_name FROM user;
