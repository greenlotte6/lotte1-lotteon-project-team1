FROM amazoncorretto:17

COPY ["./build/libs/lotteon-*.jar", "/home/root/app.jar"]

RUN mkdir -p /var/www/upload
RUN mkdir -p /var/www/upload/product
RUN mkdir -p /var/www/upload/banner

VOLUME ["/data"]

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/home/root/app.jar"]