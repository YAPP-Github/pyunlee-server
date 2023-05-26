FROM openjdk:17-alpine
# Timezone
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# chrome driver
RUN apt update -y
RUN apt install -y wget unzip
ARG DEBIAN_FRONTEND=noninteractive
RUN apt-get install -y tzdata
COPY google-chrome-stable_current_amd64.deb google-chrome-stable_current_amd64.deb
RUN dpkg -i google-chrome-stable_current_amd64.deb; exit 0
RUN apt -f install -y
COPY install_chrome_driver.sh install_chrome_driver.sh
RUN chmod +x install_chrome_driver.sh
RUN ./install_chrome_driver.sh
# log
RUN mkdir -p /logs
# jar
COPY ${JAR_FILE:-build/libs/*.jar} app.jar
ENTRYPOINT exec java ${JAVA_OPTS} -jar /app.jar ${PARAM}