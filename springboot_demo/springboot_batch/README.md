## 自动建表
一个BatchConfig配置类，一个最简单的启动类，执行main方法之后，就会在数据库batch建表：
batch_job_execution
batch_job_execution_context
batch_job_execution_params
batch_job_execution_seq
batch_job_instance
batch_job_seq
batch_step_execution
batch_step_execution_context
batch_step_execution_seq
至于启动之后就停掉，是因为没有添加spring-boot-starter-web这个dependency。事实上，batch 应用程序 也不需要保持启动状态，batch 批处理完成任务就行。

BatchConfig 和 BatchConfig1 完成不同的任务；

## 文件转换
实现功能：csv -> txt -> xml，并没有成功：
```
o.s.batch.core.job.SimpleStepHandler : Step already complete or not restartable, so no action to execute: StepExecution: id=4, version=3, name=stepCsv2Txt, status=COMPLETED, exitStatus=COMPLETED, readCount=5, filterCount=0, writeCount=5 readSkipCount=0, writeSkipCount=0, processSkipCount=0, commitCount=1, rollbackCount=0, exitDescription=
```

## spring-boot-admin
基于 spring-boot-starter-actuator提供、暴露的端点 endpoint，更加智能的监控组件。

详情见 application.properties 配置文件。

actuator 提供 13 个接口 endpoint(未来版本会有增加和调整)：

http://localhost:8088/monitor/autoconfig