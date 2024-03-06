# 使用Java 17的官方Alpine Linux镜像作为基础镜像
FROM openjdk:17-alpine

# 指定维护者信息
MAINTAINER NeckBozia <Neckbozia@outlook.com>

# 添加一个存储层，将打包好的jar文件复制到镜像中
ARG JAR_FILE=target/WolService-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# 指定容器启动时要执行的命令
ENTRYPOINT ["java","-jar","/app.jar"]
docker save -o wolservice.tar wolservice:1.0
