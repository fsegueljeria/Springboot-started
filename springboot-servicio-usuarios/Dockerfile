FROM openjdk:12
VOLUME /tmp
ADD ./target/springboot-servicio-usuarios-0.0.1-SNAPSHOT.jar servicio-usuarios-1.0.jar
ENTRYPOINT ["java", "-jar", "/servicio-usuarios-1.0.jar"]