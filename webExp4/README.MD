# Web实验四

> 这个已废弃，最新的是`blog`那个文件夹

实验三：
- 从数据库中读取内容动态显示在网页上
- 可以跳转到文章内容那个页面
- 登录功能
- 展示主页访问次数和登录次数
- 登录成功之后关闭音乐，退出登录之后又恢复音乐

实验四在实验三的基础上，增加：
- 分页功能（每10个一页）
- 增加、修改、删除文章

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