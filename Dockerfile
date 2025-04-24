FROM amazoncorretto:17

COPY ["./build/libs/lotteon-*.jar", "/home/root/app.jar"]

RUN mkdir -p /var/www/upload

VOLUME ["/data"]

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/home/root/app.jar"]