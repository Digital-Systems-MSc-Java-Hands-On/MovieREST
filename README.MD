# Introduction
Hands-on demo (Part 3) project to showcase the building of a simple Javalin REST API

# Dependencies

 - JUnit (for writing Unit Tests)
 ``` 
<dependency>
	<groupId>junit</groupId>
	<artifactId>junit</artifactId>
	<version>4.13.1</version>
	<scope>test</scope>
</dependency>
```
- MariaDB JDBC Driver
 ``` 
<dependency>
    <groupId>org.mariadb.jdbc</groupId>
    <artifactId>mariadb-java-client</artifactId>
    <version>2.7.1</version>
</dependency>
```
- Jackson JSON
```
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.10.3</version>
</dependency>
```

- SLF4J Logging (Required by Javalin)
```
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-simple</artifactId>
    <version>1.8.0-beta4</version>
</dependency>
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.8.0-beta4</version>
</dependency>
```

- Javalin Web Framework 
```
<dependency>
    <groupId>io.javalin</groupId>
    <artifactId>javalin</artifactId>
    <version>3.13.0</version>
</dependency>
```

# Maven Coordinates

To add this library as a dependency add the following maven coordinates into your pom.xml file
```
<dependency>
    <groupId>eu.unipi</groupId>
    <artifactId>MovieREST</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```