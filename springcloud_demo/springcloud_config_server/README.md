## spring-cloud-config
三部分组成：
- config-repo，是实际上存放配置文件的地方；
- config-server，配置中心的服务端，由它来管理config-repo，并接受每个client获取配置的请求；
- config-client，通常是微服务应用，它会被告知server的位置，并在初始化的过程中向server请求配置文件。

为此，需要在GitHub上面创建一个repository作为config-repo，没有考虑使用本项目，选择使用[first_blog](https://github.com/johnnywong233/first_blog)这个纯粹的MD文件的repository。  
原本只有gh-pages这一个branch，提交几个properties文件，以profile来区别；  
为了测试版本控制功能，新建一个分支v2.0，并把几个properties文件的版本信息加以更改；
这中间有报错：
```
There was an unexpected error (type=Not Found, status=404).
Cannot clone or checkout repository: https://github.com/johnnywong233/first_blog
```
关于这个问题，[参考](https://github.com/johnnywong233/first_blog/blob/master/_posts/2017-07-06-git-note.md#git-clone报错warning-remote-head-refers-to-nonexistent-ref-unable-to-checkout)  
实际上这是因为这个项目并没有默认的master分支，在GitHub页面创建一个master分钟之后，报错消失，看到测试效果。

端点与配置文件的映射规则如下：
```
/{application}/{profile}[/{label}]
/{application}-{profile}.yml
/{label}/{application}-{profile}.yml
/{application}-{profile}.properties
/{label}/{application}-{profile}.properties
```

测试URL：
```
http://localhost:9091/config-repo.properties
http://localhost:9091/config-repo-dev.properties/
http://localhost:9091/config-repo.properties/dev
http://localhost:9091/v2.0/config-repo-dev.properties/
```
