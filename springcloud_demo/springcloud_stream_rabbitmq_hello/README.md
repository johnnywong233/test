## 实例1
[参考原文](https://segmentfault.com/a/1190000011778631)

## 实例2
注释以下行：
```
virtual-host: dev
```
否则报错：
```
Caused by: com.rabbitmq.client.ShutdownSignalException: connection error; protocol method:
#method<connection.close>(reply-code=530, reply-text=NOT_ALLOWED - access to vhost 'dev' refused for user 'guest', class-id=10, method-id=40)
```

跑两个实例时，需要把另一个实例的代码注释或者删除！