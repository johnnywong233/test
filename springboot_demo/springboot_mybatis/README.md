基于Maven的Springboot+Mybatis+Druid+Swagger2+mybatis-generator框架环境搭建
[项目说明](http://www.raye.wang/2016/10/11/ji-yu-mavende-springboot-mybatis-druid-swagger2-mybatis-generatorkuang-jia-huan-jing-da-jian/)

Postgres SQL:
```sql
CREATE TABLE public."user" (
	id varchar(11) NOT NULL,
	username varchar(20) NULL,
	psw varchar(20) NULL
)
WITH (
	OIDS=FALSE
);
```

Postgres SQL:
```sql
INSERT INTO public.employee(id, age, "name")VALUES(1, 23, 'Danial');
INSERT INTO public.employee(id, age, "name")VALUES(2, 37, 'Daniel');
INSERT INTO public.employee(id, age, "name")VALUES(3, 29, 'Alice');
INSERT INTO public.employee(id, age, "name")VALUES(4, 26, 'Johnny');
INSERT INTO public.employee(id, age, "name")VALUES(5, 34, 'Sunny');
INSERT INTO public.employee(id, age, "name")VALUES(6, 39, 'Perry');
INSERT INTO public.employee(id, age, "name")VALUES(7, 34, 'Ward');
INSERT INTO public.employee(id, age, "name")VALUES(8, 35, 'Johnny');
INSERT INTO public.employee(id, age, "name")VALUES(9, 22, 'Johnny');
```

## spring-data-jpa
see code under repository to check how to use spring-data-jpa interface Repository