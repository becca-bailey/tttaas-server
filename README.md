# Becca and Tom's Tic Tac Toe Server [![Build Status](https://travis-ci.org/beccanelson/tttaas-server.svg?branch=master)](https://travis-ci.org/beccanelson/tttaas-server)

## Dependencies:

+ [Maven](https://maven.apache.org/)

+ [Java](http://www.oracle.com/technetwork/systems/index-jsp-138363.html)

To install Maven with homebrew: `brew install maven`

To install Java: Download the most recent JDK from [Oracle](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

## To run the server:

With dependencies installed:
```
git clone https://github.com/beccanelson/tic-tac-toe-as-a-service.git
cd http-server
mvn package
java -jar target/http-server-0.0.1.jar -p <port number> (default is 5000)
```

## To run tests:

In the `http-server` directory:
```
mvn test
```
