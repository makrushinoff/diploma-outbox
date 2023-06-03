FROM openjdk:17-jdk-slim as build
WORKDIR /workspace/app

ARG JAR_FILE=target/user-management*.jar
COPY ${JAR_FILE} target/application.jar
RUN java -Djarmode=layertools -jar target/application.jar extract --destination target/extracted

FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG EXTRACTED=/workspace/app/target/extracted
WORKDIR application
COPY --from=build ${EXTRACTED}/dependencies/ ./
COPY --from=build ${EXTRACTED}/spring-boot-loader/ ./
COPY --from=build ${EXTRACTED}/snapshot-dependencies/ ./
COPY --from=build ${EXTRACTED}/application/ ./
ENV TZ UTC
RUN apt-get -y update; apt-get -y install curl
ENTRYPOINT exec java ${JAVA_OPTS} org.springframework.boot.loader.JarLauncher
