FROM openjdk:12
VOLUME /tmp
EXPOSE 8761
ADD ./target/springboot-servicio-eureka-server-0.0.1-SNAPSHOT.jar config-eureka-1.0.jar
ENTRYPOINT ["java", "-jar", "/config-eureka-1.0.jar"]