# 使用基于 JDK 21 的基础镜像
FROM openjdk:21-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制应用的 JAR 文件到容器中
COPY target/TransactionManager.jar /app/TransactionManager.jar

RUN apt - get update && apt - get install - y maven && mvn clean package

# 暴露应用运行的端口，假设应用运行在 8080 端口，可按需修改
EXPOSE 8080

# 定义启动命令
CMD ["java", "-jar", "TransactionManager.jar"]