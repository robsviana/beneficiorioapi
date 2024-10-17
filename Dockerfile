FROM amazoncorretto:21.0.4
RUN mkdir /app
WORKDIR /app
COPY target/*.jar /app/app.jar
CMD ["java","-jar","/app/app.jar"]
EXPOSE 8080