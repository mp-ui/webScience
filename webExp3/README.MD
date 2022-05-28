# Web实验三

- 从数据库中读取内容动态显示在网页上
- 可以跳转到文章内容那个页面
- 登录功能
- 展示主页访问次数和登录次数
- 登录成功之后关闭音乐，退出登录之后又恢复音乐

## 数据库
```mysql
create database webExp3;
use webExp3;

create table user(
	id int primary key auto_increment,
	username varchar(20),
	password varchar(20)
);

create table article(
	id int primary key auto_increment,
	title varchar(128) not null,
	author_id int not null,
	content MEDIUMTEXT,
	FOREIGN key (author_id) REFERENCES user(id)
);create database webExp3;
use webExp3;

create table user(
                     id int primary key auto_increment,
                     username varchar(20),
                     password varchar(20)
);

create table article(
                        id int primary key auto_increment,
                        title varchar(128) not null,
                        author_id int not null,
                        content MEDIUMTEXT,
                        FOREIGN key (author_id) REFERENCES user(id)
);

create table info(
                     id int primary key,
                     visit_time int default 0,
                     login_time int default 0
)

insert into info values(1,0,0);

```