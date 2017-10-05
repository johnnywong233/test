## run
先把server，即producer跑起来，通过rest client工具发送post请求，request body见注释，然后再运行client，即consumer。
否则报错：
```
Caused by: com.rabbitmq.client.ShutdownSignalException: channel error; protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND - no queue 'email_queue' in vhost '/', class-id=50, method-id=10)
```

## 知识点
### 构建消息生产者
定义队列queue、交换器Exchange，以及绑定inding。事实上，通过这种方式当队列或交换器不存在的时候，Spring AMQP 会自动创建它。（如果你不希望自动创建，可以在 RabbitMQ 的管理后台开通队列和交换器，并注释掉 queue() 方法和 exchange() 方法）。此外为了更好地扩展，将创建队列或交换器的配置信息抽离到配置文件application.properties。其中，还包括 RabbitMQ 的配置信息。  
此外，假设一个生产者发送到一个交换器，而一个消费者从一个队列接收消息。此时，将队列绑定到交换器对于连接这些生产者和消费者至关重要。在 Spring AMQP 中，定义一个 Binding 类来表示这些连接，使用 BindingBuilder 来构建 “流式的 API” 风格。

### 构建消息消费者
增加一个 listenerContainer() 方法，监听器容器，用来监听消息队列进行消息处理的。这里设置手动 ACK 的方式。默认情况下，它采用自动应答，这种方式中消息队列会发送消息后立即从消息队列中删除该消息。  
通过手动 ACK 方式，如果消费者因宕机或链接失败等原因没有发送 ACK，RabbitMQ 会将消息重新发送给其他监听在队列的下一个消费者，保证消息的可靠性。