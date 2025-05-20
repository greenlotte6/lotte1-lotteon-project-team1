FROM amazoncorretto:17

COPY ["./build/libs/lotteon-*.jar", "/home/root/app.jar"]

RUN mkdir -p /var/www/upload
RUN mkdir -p /var/www/upload/data
RUN mkdir -p /var/www/upload/product
RUN mkdir -p /var/www/upload/banner
RUN rm /etc/localtime
RUN ln -s /usr/share/zoninfo/Asia/Seoul /etc/localtime

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/home/root/app.jar"]