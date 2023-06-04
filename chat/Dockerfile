FROM openjdk:17
EXPOSE 9090
VOLUME /tmp
ARG JAR_FILE=build/libs/chat-4.0.0.jar
COPY ${JAR_FILE} chat-app.jar
ENTRYPOINT ["java","-jar","/chat-app.jar"]
