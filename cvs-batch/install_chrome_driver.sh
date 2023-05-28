#!/usr/bin/env bash

CHROME_VERSION="google-chrome-stable"
CHROME_MAJOR_VERSION=$(google-chrome -version | sed -E "s/Google Chrome ([0-9]+)\..*/\1/")
CHROME_DRIVER_VERSION=$(wget -nv -O - "https://chromedriver.storage.googleapis.com/LATEST_RELEASE_${CHROME_MAJOR_VERSION}")
echo "Using chromedriver version: "$CHROME_DRIVER_VERSION
wget -nv -O /tmp/chromedriver_linux64.zip https://chromedriver.storage.googleapis.com/$CHROME_DRIVER_VERSION/chromedriver_linux64.zip
rm -rf /opt/selenium/chromedriver
unzip /tmp/chromedriver_linux64.zip -d /opt/selenium
rm /tmp/chromedriver_linux64.zip
mv /opt/selenium/chromedriver /opt/selenium/chromedriver-$CHROME_DRIVER_VERSION
chmod 755 /opt/selenium/chromedriver-$CHROME_DRIVER_VERSION
ln -fs /opt/selenium/chromedriver-$CHROME_DRIVER_VERSION /usr/bin/chromedriver