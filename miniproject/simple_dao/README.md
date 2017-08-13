## Configure database
Using postgres, add pom.xml, run SQL:
```sql
create table myemp ( id serial, username varchar(32), password varchar(32));
```
to avoid error: org.postgresql.util.PSQLException: relation "myemp" does not exists.