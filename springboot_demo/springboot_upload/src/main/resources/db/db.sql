--postgres
DROP TABLE public.user

CREATE TABLE public.user (
  id   INTEGER PRIMARY KEY,
  username VARCHAR(30),
  password  VARCHAR(30),
  age INTEGER
);

INSERT INTO public.user (id, username, password, age)
VALUES (0, 'root', 'root', 16);

--注意，无论是Postgres还是mysql数据库，如果field定义成userName，则自动建表为user_name;
--field定义为username，则自动建表为username