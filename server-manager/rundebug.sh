java -Xdebug -Xrunjdwp:transport=dt_socket,address=5555,server=y,suspend=n -jar  target/server-manager-0.0.1-SNAPSHOT.jar --spring.config.location=file:///etc/csm/application.properties
