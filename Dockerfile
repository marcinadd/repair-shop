FROM openjdk:8-jdk-alpine
EXPOSE 8080
WORKDIR /usr/local/bin
COPY ./build/libs/repair-shop-0.0.1-SNAPSHOT.jar repair-shop.jar
CMD ["java", "-jar", "repair-shop.jar"]