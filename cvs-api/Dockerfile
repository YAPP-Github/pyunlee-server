FROM openjdk:17-alpine

EXPOSE 8080

ARG PROFILE=develop
ENV PROFILE=${PROFILE}
ENV TZ "Asia/Seoul"
ENV JAVA_OPTS="-Dspring.profiles.active=$PROFILE"

WORKDIR /app
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY ./build/libs/*.jar app.jar

ENTRYPOINT java $JAVA_OPTS -jar app.jar