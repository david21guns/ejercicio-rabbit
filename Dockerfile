FROM openjdk:17
EXPOSE 8086
ADD /target/ejercicio-rabbit.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]