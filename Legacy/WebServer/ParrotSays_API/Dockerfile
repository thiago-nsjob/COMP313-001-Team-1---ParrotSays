FROM java:8-jdk-alpine

COPY ./target/ParrotSaysAPI-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch ParrotSaysAPI-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","ParrotSaysAPI-0.0.1-SNAPSHOT.jar"]
