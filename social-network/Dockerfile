FROM openjdk:17
EXPOSE 8080
VOLUME /tmp
ARG JAR_FILE=build/libs/social-network-4.0.0.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
