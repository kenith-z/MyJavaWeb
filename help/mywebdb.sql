
##创建数据库
CREATE DATABASE IF NOT EXISTS mywebdb;

##创建用户（该用户只能从本机访问数据库，权限为INSERT、DELETE、UPDATE、SELECT）
GRANT ALL ON mywebdb.* TO myweb@"localhost" IDENTIFIED BY "password";

##创建用户（该用户可以从其他机器远程访问数据库，权限为SELECT操作）
GRANT SELECT ON mywebdb.* TO student@"%" IDENTIFIED BY "password";

##进入数据库
USE mywebdb;

##特别注意：下面语句在导入成语数据后再执行。
##向成语表（cn_idiom）添加一个图片文件名的字段（img_name）
ALTER TABLE cn_idiom ADD img_name varchar(255) AFTER example;