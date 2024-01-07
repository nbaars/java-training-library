# Library Management System

Welcome to the Library Management System. This is a simple application that allows you to manage books in a library.

## Running the application

### Start PostgreSQL database

Before starting the application for the first time you will need to start the PostgreSQL database. You can do this by
running the following command:

```bash
docker-compose up -d postgresql
```

### Run with IntelliJ

**First start the PostgreSQL database as described above.**

In IntelliJ, you can run the application by right-clicking on the `Application` class and selecting `Run 'Application'`.

### Run with Maven

**First start the PostgreSQL database as described above.**

```bash
./mvnw spring-boot:run
```

### Run from command line

**First start the PostgreSQL database as described above.**

Build the application:

```bash
./mvnw clean package 
```

Then run the application:

```bash
    java -jar target/java-training-library-1.0-SNAPSHOT.jar
```

You can access the application at http://localhost:8080/libary

### Run with docker-compose

**Make sure no Docker containers are running before starting the application with docker-compose.**

First build the application:

```bash
./mvnw clean package
```

Then run the application with docker-compose:

```bash
docker-compose up --build 
```

The `--build` flag will make sure the Docker image is rebuilt before running the application.

The `Dockerfile` is located in `src/main/docker` and is filtered by Maven. This means that the `Dockerfile` will be
generated in the `target/docker` directory when you run `./mvnw clean package`.
As the `docker-compose.yml` file is located in the same directory as the `Dockerfile`, you can
run `docker-compose up --build` from the `target/docker` directory.

## Build the application

Run the following command to build the application:

```bash
./mvnw clean package
```

## OpenAPI Documentation

Browse to http://localhost:8080/swagger-ui/index.html to view the OpenAPI documentation for the application.

