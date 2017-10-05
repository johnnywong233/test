## run
先把server，即producer跑起来，通过rest client工具发送post请求，request body见注释，然后再运行client，即consumer。
否则报错：
```
Caused by: com.rabbitmq.client.ShutdownSignalException: channel error; protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND - no queue 'email_queue' in vhost '/', class-id=50, method-id=10)
```