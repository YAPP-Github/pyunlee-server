FROM openjdk:17-jdk-slim
# Timezone
ARG PROFILE=dev
ENV PROFILE=${PROFILE}
ENV JAVA_OPTS="-Dspring.profiles.active=$PROFILE"
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# chrome driver
RUN apt update -y
RUN apt install -y wget unzip
ARG DEBIAN_FRONTEND=noninteractive
RUN apt-get install -y tzdata
# log
RUN mkdir -p /logs
# jar
COPY ${JAR_FILE:-build/libs/*.jar} app.jar
ENTRYPOINT exec java ${JAVA_OPTS} -jar /app.jar ${PARAM}