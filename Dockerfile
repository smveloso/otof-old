#### UBER JAR
FROM java:openjdk-8-jdk
ADD ${project.build.directory}/${project.artifactId}-${project.version}-thorntail.jar /opt
# COPY standalone.xml /opt/jboss/wildfly/standalone/configuration/standalone.xml
ENV JAVA_OPTS="-Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses=true"
EXPOSE 8080
ENTRYPOINT java $JAVA_OPTS -jar /opt/${project.artifactId}-${project.version}-thorntail.jar