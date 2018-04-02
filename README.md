\[!\[Build Status](https://travis-ci.org/johnnywong233/test.svg?branch=master)](https://travis-ci.org/johnnywong233/test)

## These are some silly test/demo, including 
- spring boot demo modules, see [here](https://github.com/johnnywong233/test/blob/master/springboot_demo/pom.xml)
- spring cloud demo modules, see [here](https://github.com/johnnywong233/test/blob/master/springcloud_demo/pom.xml)
- java 8 learning demo, see [here](https://github.com/johnnywong233/test/blob/master/demo/src/main/java/java8/)
- mockito test framework demo, see [here](https://github.com/johnnywong233/test/blob/master/demo/src/test/java/mockito/)
- all kinds of JSON library demo, see [here](https://github.com/johnnywong233/test/blob/master/demo/src/main/java/json/)
- all kinds of XML library demo, see [here](https://github.com/johnnywong233/test/blob/master/demo/src/main/java/file/xml/)
- all kinds of YAML library demo, see [here](https://github.com/johnnywong233/test/blob/master/demo/src/main/java/yaml/)
- Jsch(ssh tool for java) demo, see [here](https://github.com/johnnywong233/test/blob/master/demo/src/main/java/jsch/)
- JDK mail tool(javax.mail) and spring mail demo, see [here](https://github.com/johnnywong233/test/blob/master/demo/src/main/java/mail/)
- some Util class, see [here](https://github.com/johnnywong233/test/blob/master/demo/src/main/java/utils/)
- JDBC, SQL related, see [here](https://github.com/johnnywong233/test/blob/master/demo/src/main/java/sql/)
- Socket learning, see [here](https://github.com/johnnywong233/test/blob/master/demo/src/main/java/socket/)
- thread related learning, see [here](https://github.com/johnnywong233/test/blob/master/demo/src/main/java/thread/)
- java concurrent package learning, see [here](https://github.com/johnnywong233/test/blob/master/demo/src/main/java/concurrent/)
- JDK grammar, see [here](https://github.com/johnnywong233/test/blob/master/demo/src/main/java/grammar/)
- plant UML language, see [here](https://github.com/johnnywong233/test/blob/master/demo/src/main/resources/uml/)
- jsoup(html parser), see [here](https://github.com/johnnywong233/test/blob/master/demo/src/main/java/jsoup/) 


## standalone maven jar
```
mvn install:install-file -Dfile=jbarcode-0.2.8.jar -DgroupId=org.jbarcode -DartifactId=jbarcode -Dversion=0.2.8 -Dpackaging=jar
mvn install:install-file -Dfile=cpdetector-1.0.7.jar -DgroupId=info.monitorenter -DartifactId=cpdetector -Dversion=1.0.7 -Dpackaging=jar
mvn install:install-file -Dfile=qrcode-1.0.jar -DgroupId=com.swetake -DartifactId=qrcode -Dversion=1.0 -Dpackaging=jar
mvn install:install-file -Dfile=qrcode-0.1.jar -DgroupId=jp.sourceforge -DartifactId=qrcode -Dversion=0.1 -Dpackaging=jar
mvn install:install-file -Dfile=jdk.tools-1.8.jar -DgroupId=jdk -DartifactId=tools -Dversion=1.8 -Dpackaging=jar
mvn install:install-file -Dfile=jmxtools-1.2.1.jar -DgroupId=com.sun.jdmk -DartifactId=jmxtools -Dversion=1.2.1 -Dpackaging=jar
mvn install:install-file -Dfile=jms-1.1.jar -DgroupId=javax.jms -DartifactId=jms -Dversion=1.1 -Dpackaging=jar
 
mvn install:install-file -Dfile=mybatis-pagination-1.0.0.jar -DgroupId=org.mybatis -DartifactId=mybatis-pagination -Dversion=1.0.0 -Dpackaging=jar



``` 
