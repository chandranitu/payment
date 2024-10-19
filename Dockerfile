FROM openjdk:17-jdk-slim

EXPOSE 8088

ADD /target/paymentservice-0.0.1-SNAPSHOT.jar paymentservice-0.0.1-SNAPSHOT.jar

# Pass the active Spring profile (dev or qa) at runtime
# Default profile is set to 'dev' but it can be overridden
ARG SPRING_PROFILES_ACTIVE=dev
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}


#ENTRYPOINT ["java","-jar","paymentservice-0.0.1-SNAPSHOT.jar"]
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -jar paymentservice-0.0.1-SNAPSHOT.jar"]


