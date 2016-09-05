java -Xdebug -Xrunjdwp:transport=dt_socket,address=5556,server=y,suspend=n -jar  ./target/agent-0.0.1-SNAPSHOT.jar --spring.config.location=file:///etc/csa/application.properties
