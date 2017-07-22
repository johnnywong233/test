### docker build command:
```docker build --build-arg http_proxy=http://web-proxy.sgp.hpecorp.net:8080 --build-arg https_proxy=https://web-proxy.sgp.hpecorp.net:8080 -t johnny-docker-demo .```

### docker run command:
```docker run -it --entrypoint="/bin/bash" hub.docker.hpecorp.net/shared/johnny-docker-demo:1.0```

After entering into container, run ```java -jar springboot_docker-0.0.1-SNAPSHOT.jar``` is also OK.

### docker run command with port:
```docker run -it --entrypoint="/bin/bash" -p 8086:8080 hub.docker.hpecorp.net/shared/johnny-docker-demo:1.0```
