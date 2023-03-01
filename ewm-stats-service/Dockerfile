FROM amazoncorretto:11
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY  ${JAR_FILE} app.jar
ENTRYPOINT ["sh","-c","/app.jar", "java ${JAR_FILE} -jar /app.jar ${0} ${@}"]