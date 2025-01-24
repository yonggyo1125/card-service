FROM yonggyo00/ubuntu:card2
ARG JAR_FILE=build/libs/cardservice-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENV DDL_AUTO=update
ENV PYTHON_PATH=/bin/python3.9
ENV PYTHON_SCRIPT=/card/
ENV SPRING_PROFILES_ACTIVE=default,ml

ENTRYPOINT ["java", "-jar", "-Ddb.host=${DB_HOST}", "-Ddb.password=${DB_PASSWORD}", "-Ddb.username=${DB_USERNAME}", "-Dddl.auto=${DDL_AUTO}", "-Dconfig.server=${CONFIG_SERVER}", "-Deureka.server=${EUREKA_SERVER}", "-Dhostname=${HOSTNAME}", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-Dpython.run.path=${PYTHON_PATH}", "-Dpython.script.path=${PYTHON_SCRIPT}", "app.jar"]

EXPOSE 3005