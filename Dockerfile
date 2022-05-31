##
# Set up Debian Linux based container with necessary requirements.
##
FROM debian
RUN apt-get -y update
RUN apt-get -y install swi-prolog
RUN apt-get -y install openjdk-11-jdk
RUN apt-get -y install swi-prolog-java

##
# Configure our application.
#
WORKDIR /app
ADD Core.java /app/Core.java
ADD main.pl /app/main.pl

ENV SWI_HOME_DIR=/usr/lib/swi-prolog/
ENV LD_LIBRARY_PATH=/usr/lib/swi-prolog/lib/x86_64-linux/
ENV CLASSPATH=.:/usr/lib/swi-prolog/lib/jpl.jar
ENV LD_PRELOAD=/usr/lib/libswipl.so

##
# Execute
##
RUN javac -cp .:/usr/lib/swi-prolog/lib/jpl.jar Core.java
CMD ["java", "Core"]