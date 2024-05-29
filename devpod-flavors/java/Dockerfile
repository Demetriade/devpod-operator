FROM maven:3.9.5-eclipse-temurin-11-focal

USER root
WORKDIR /root

COPY settings.xml /root/.m2/settings.xml

RUN echo "Acquire::Check-Valid-Until \"false\";\nAcquire::Check-Date \"false\";" | cat > /etc/apt/apt.conf.d/10no--check-valid-until
RUN apt update -qqy
RUN apt install -qqy git iputils-ping curl vim
RUN rm -rf /var/lib/apt/lists/*

CMD ["sleep", "infinity"]
