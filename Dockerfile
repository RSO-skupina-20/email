FROM adoptopenjdk:16-jre-openj9-focal

RUN mkdir /app

WORKDIR /app

ADD ./target/email-1.0-SNAPSHOT.jar /app

EXPOSE 8080

ENV FROM_DOMAIN=from_domain \
    TOKEN=token

ENTRYPOINT ["java", "-jar", "email-1.0-SNAPSHOT.jar"]