FROM amazoncorretto:17

COPY ["./build/libs/lotteon-*.jar", "/home/root/app.jar"]

CMD ["mkdir", "-p", "/var/www/uploads"]

VOLUME ["/data"]

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/home/root/app.jar"]