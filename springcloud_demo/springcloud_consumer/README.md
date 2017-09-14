### Three steps: 
- run springcloud_eureka.main method to open the service registry center, see nothing in http://localhost:1111/
- install springcloud_client1 to generate executable jar, then run command in two windows to register two services
    ```
    java -jar springcloud_client1-0.0.1-SNAPSHOT.jar --server.port=8080
    java -jar springcloud_client1-0.0.1-SNAPSHOT.jar --server.port=8081
    ```
check http://localhost:1111/, will find 2 COMPUTE-SERVICE running on http://localhost:8080/ and http://localhost:8081/
- run springcloud_consumer as a service client, will find a RIBBON-CONSUMER running on http://localhost:9000/ribbon-consumer registered on http://localhost:1111/, check result of http://localhost:9000/ribbon-consumer 
[reference](https://mp.weixin.qq.com/s?__biz=MzI1NDY0MTkzNQ==&mid=2247483914&idx=1&sn=f984275923667e16b996866df0cadba1&scene=21#wechat_redirect)
     