FROM openjdk:8

RUN mkdir -p /opt
WORKDIR /opt

RUN mkdir -p /opt/logs

WORKDIR /app
ADD ./target/collector.jar /app/collector.jar

COPY docker/entrypoint.sh .

RUN chmod +x /app/entrypoint.sh

ENTRYPOINT ["sh", "-c", "/app/entrypoint.sh"]
