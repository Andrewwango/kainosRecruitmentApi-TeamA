FROM maven:latest
#set maven dependency to specific version?

WORKDIR /code

COPY . /code

ARG DB_HOST=academy2020.cpc8rvmbbd9k.eu-west-2.rds.amazonaws.com
ARG DB_PASSWORD=root
ARG DB_USERNAME=JennicaM


ENV DB_HOST ${DB_HOST}
ENV DB_PASSWORD ${DB_PASSWORD}
ENV DB_USERNAME ${DB_USERNAME}


RUN mvn clean install -DskipTests=true

EXPOSE 8080

CMD ["java","-jar", "/code/target/API-TeamA-1.0-SNAPSHOT.jar", "server", "/code/config.yml"]