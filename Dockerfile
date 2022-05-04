FROM maven:3.8.5-openjdk-11 as maven

WORKDIR /tmp/

COPY pom.xml /tmp/

RUN mvn -B dependency:resolve dependency:resolve-plugins

COPY src /tmp/src/

RUN mvn package -Dmaven.test.skip

FROM openjdk

COPY --from=maven /tmp/target/coltrolcoin-0.0.1-SNAPSHOT.jar /opt/backend/

ENTRYPOINT ["java","-jar","/opt/backend/coltrolcoin-0.0.1-SNAPSHOT.jar"]
