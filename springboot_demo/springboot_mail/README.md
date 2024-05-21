# spring boot with mail

# spring boot with Sentry
## 简介
Sentry,开源实时错误报告工具，支持 web 前后端、移动应用以及游戏，支持主流编程语言和框架,还提供 GitHub、Slack、Trello 等常见开发工具的集成。Sentry 服务支持多用户、多团队、多应用管理，每个应用都对应一个 PROJECT_ID，以及用于身份认证的 PUBLIC_KEY 和 SECRET_KEY。由此组成一个这样的 DSN：  
`{PROTOCOL}://{PUBLIC_KEY}:{SECRET_KEY}@{HOST}/{PATH}{PROJECT_ID}`  
PROTOCOL 通常会是 http 或者 https，HOST 为 Sentry 服务的主机名和端口，PATH 通常为空。 为方便管理，每个应用生成一个 DSN(Data Source Name)。  
Sentry 的 SDK 通常在各语言的包管理器中称为 Raven.
## 集成步骤
logback输出日志到sentry, 
pom.xml添加：
```xml
<!--deprecated: 最下面给出的文档地址-->
<dependency>
    <groupId>com.getsentry.raven</groupId>
    <artifactId>raven-logback</artifactId>
    <version>8.0.3</version>
</dependency>

<!--use this-->
<dependency>
    <groupId>io.sentry</groupId>
    <artifactId>sentry-logback</artifactId>
    <version>1.7.16</version>
</dependency>
```
新增配置文件 logback.xml, 可以很简洁：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <include resource="org/springframework/boot/logging/logback/base.xml"/>
    <jmxConfigurator/>

    <appender name="SENTRY" class="io.sentry.logback.SentryAppender">
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>ERROR</level>
        </filter>
    </appender>

    <root level="INFO">
        <appender-ref ref="SENTRY" />
    </root>
</configuration>
```
SDN 配置信息可以放在 application.yml 或者 logback.xml 文件中。
```yml
SENTRY_ENVIRONMENT=test
SENTRY_DSN=http://1d59201178e540ec8172870df4413aa2:20131e8bb98c4ab48693e4f9cd6ef98a@192.168.99.100:9000/2
```
TODO
申请自己的 sentry 账户并登录 sentry web 平台查看监控日志。

完整的 logback.xml 配置文件参考：
https://docs.sentry.io/clients/java/integrations/#logback

# Apache Calcite
