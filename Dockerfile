FROM openjdk:17-jdk-slim
EXPOSE 8088:8088
ADD /target/paymentservice-0.0.1-SNAPSHOT.jar paymentservice-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","paymentservice-0.0.1-SNAPSHOT.jar"]
