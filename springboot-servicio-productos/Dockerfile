FROM openjdk:12
VOLUME /tmp
ADD ./target/springboot-servicio-productos-0.0.1-SNAPSHOT.jar servicio-productos-1.0.jar
ENTRYPOINT ["java", "-jar", "/servicio-productos-1.0.jar"]