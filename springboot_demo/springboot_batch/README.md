## 建表
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
至于启动之后就停掉，是因为没有添加spring-boot-starter-web这个dependency。

## 文件转换
实现功能：csv -> txt -> xml，并没有成功：
```
o.s.batch.core.job.SimpleStepHandler : Step already complete or not restartable, so no action to execute: StepExecution: id=4, version=3, name=stepCsv2Txt, status=COMPLETED, exitStatus=COMPLETED, readCount=5, filterCount=0, writeCount=5 readSkipCount=0, writeSkipCount=0, processSkipCount=0, commitCount=1, rollbackCount=0, exitDescription=
```
