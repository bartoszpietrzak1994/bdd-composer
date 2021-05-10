FROM openjdk:11.0.9-jre-slim-buster
ENV WORKDIR_PATH /app
RUN addgroup --system java && adduser --system --home $WORKDIR_PATH --group java
WORKDIR $WORKDIR_PATH
USER java:java
ARG JAR_FILE
COPY "${JAR_FILE}" /app/app.jar
CMD java -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar