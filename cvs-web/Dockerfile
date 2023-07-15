FROM openjdk:17-alpine

EXPOSE 10002

ENV TZ "Asia/Seoul"

WORKDIR /app
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY ./build/libs/*.jar app.jar

ENTRYPOINT java -jar app.jar