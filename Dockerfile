FROM openjdk:17
WORKDIR /usr/src/app
ARG JAR_FILE=target/*.jar
COPY ./target/HospitalBtask-v1.jar app.jar
CMD ["java","-jar","app.jar"]