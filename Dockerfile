FROM openjdk:14-alpine
COPY target/tonet-*.jar tonet.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "tonet.jar"]