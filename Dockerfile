FROM rsunix/yourkit-openjdk17

WORKDIR /app

COPY target/products_service-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

#docker run -p 8080:8080 -v /path/to/your/application.properties:/app/application.properties your-image-name