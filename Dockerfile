FROM amazoncorretto:17

COPY ["./build/libs/lotteon-*.jar", "/home/root/app.jar"]

VOLUME ["/data"]

ENTRYPOINT ["java", "-jar", "/home/root/app.jar"]