> SpringDataJPA是Spring Data的一个子项目，通过提供基于JPA的Repository极大的减少了JPA作为数据访问方案的代码量，你仅仅需要编写一个接口集成下SpringDataJPA内部定义的接口即可完成简单的CRUD操作。

## 前言
本篇文章引导你通过`Spring Boot`，`Spring Data JPA`和`MySQL` 映射一对一外键、一对一主键、一对多，多对一，多对多、多对多额外的列的关系。

#### 一对一外键

##### 一对一关系
`book.book_detail_id` 和 `book_detail.id`

db.sql
```sql
CREATE DATABASE  IF NOT EXISTS `jpa_onetoone_foreignkey`;
USE `jpa_onetoone_foreignkey`;

--
-- Table structure for table `book_detail`
--

DROP TABLE IF EXISTS `book_detail`;
CREATE TABLE `book_detail` (
`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
`number_of_pages` int(11) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
`name` varchar(255) DEFAULT NULL,
`book_detail_id` int(11) unsigned DEFAULT NULL,
PRIMARY KEY (`id`),
KEY `fk_book_bookdetail` (`book_detail_id`),
CONSTRAINT `fk_book_bookdetail` FOREIGN KEY (`book_detail_id`) REFERENCES `book_detail` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
```
##### 实体类
- `@Table`声明此对象映射到数据库的数据表，通过它可以为实体指定表(talbe),目录(Catalog)和schema的名字。该注释不是必须的，如果没有则系统使用默认值(实体的短类名)。
- `@Id` 声明此属性为主键。该属性值可以通过应该自身创建，但是Hibernate推荐通过Hibernate生成
- `@GeneratedValue` 指定主键的生成策略。
	1. TABLE：使用表保存id值
	2. IDENTITY：identitycolumn
	3. SEQUENCR ：sequence
	4. AUTO：根据数据库的不同使用上面三个
	
- ` @Column` 声明该属性与数据库字段的映射关系。
- `@OneToOne` 一对一关联关系
- `@JoinColumn` 指定关联的字段

## 其它
剩下的一对一主键、一对多，多对一，多对多、多对多额外的列参考如上。
