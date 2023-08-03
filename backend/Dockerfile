##
# Set up Debian Linux based container with necessary requirements.
##
FROM debian
RUN apt-get -y update
RUN apt-get -y install swi-prolog
RUN apt-get -y install openjdk-17-jdk
RUN apt-get -y install swi-prolog-java
RUN curl -L https://services.gradle.org/distributions/gradle-7.4.2-bin.zip -o gradle-7.4.2-bin.zip
RUN apt-get install -y unzip
RUN unzip gradle-7.4.2-bin.zip


##
# Configure our application.
#
WORKDIR /app
ADD src /app
ADD main.pl /app/main.pl

ENV SWI_HOME_DIR=/usr/lib/swi-prolog/
ENV LD_LIBRARY_PATH=/usr/lib/swi-prolog/lib/x86_64-linux/
ENV CLASSPATH=.:/usr/lib/swi-prolog/lib/jpl.jar
ENV LD_PRELOAD=/usr/lib/libswipl.so
ENV GRADLE_HOME=/app/gradle-7.4.2
ENV PATH=$PATH:$GRADLE_HOME/bin
ARG JAR_FILE=target/server-0.0.1-SNAPSHOT.jar

##
# Execute
##
COPY ${JAR_FILE} app.jar
ENTRYPOINT [ "java", "-jar", "app.jar" ]
# RUN javac -cp .:/usr/lib/swi-prolog/lib/jpl.jar Core.java
# CMD ["java", "Core"]

# to build 
# docker build -t tasktician .
# to RUN
# docker run -d -p 8080:8080 -t tasktician