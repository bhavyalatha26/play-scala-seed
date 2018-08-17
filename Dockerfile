#
# Play Scala Demo Docker image
#

# pull base image.
FROM bigtruedata/sbt:latest

MAINTAINER Bhavya Latha Bandaru "bhavyalathab@gmail.com"

ADD target/universal/play-scala-demo-1.0.0.tgz /apps
VOLUME /logs
VOLUME /config

EXPOSE 80

ENTRYPOINT ["/apps/play-scala-demo-1.0.0/bin/play-scala-demo"]

CMD ["-mem", "-J-Xms512m", "-Dhttp.port=80"]