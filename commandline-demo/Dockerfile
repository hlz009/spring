FROM java:8
EXPOSE 8080
ARG JAR_FILE
ADD target/${JAR_FILE} /commandline-demo.jar
ENTRYPOINT ["java", "-jar","/commandline-demo.jar"]