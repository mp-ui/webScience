# 使用JSP搭建博客页面

内容：用Jsp技术完善博客站点
功能须进一步包括：
- 博主新增、预览、发表博客文章
- 博主修改、删除博客文章
- 搜索添加好友、接收好友请求
- 博友/游客浏览博文列表（须用分页技术）
- 博友/游客点击并阅读单片博客文章
- 博友对博客评论（游客不具备评论权限）

其他选做功能：
- 批量删除博文
- 博主设置主题背景音乐
- 自由发挥


## 数据表

```mysql
create table user(
	id int primary key auto_increment,
	username varchar(20) not null unique,
	alias varchar(20),
	password varchar(20) not null,
	ctime TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP
);

create table article(
	id int primary key auto_increment,
	title varchar(128) not null,
	description varchar(128),
	content MEDIUMTEXT,
	uid int not null,
	pageView int not null default 0,
	ctime TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT u FOREIGN key (uid) REFERENCES user(id)
);

create table comment(
	id int primary key auto_increment,
	uid int not null,
	aid int not null,
    text MEDIUMTEXT,
	ctime TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT ui FOREIGN key (uid) REFERENCES user(id),
	CONSTRAINT ai FOREIGN key (aid) REFERENCES article(id)
);

create table friend(
	aid int not null,
	bid int not null,
	ctime TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
	FOREIGN key (aid) REFERENCES user(id),
	FOREIGN key (bid) REFERENCES user(id)
);

create table request(
	aid int not null,
	bid int not null,
	status int not null default 0,
	ctime TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
	FOREIGN key (aid) REFERENCES user(id),
	FOREIGN key (bid) REFERENCES user(id)
)
```