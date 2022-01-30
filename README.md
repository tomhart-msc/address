# PagerDuty Address Book

An address book application using the PagerDuty API, Spring Boot, Thymeleaf, and jQuery.

## Prerequisites

This application requires JDK 11.

## Building

Build this application using Gradle:

```
$ ./gradlew build
```

In addition to compiling and running unit tests, the build verifies that:
* All Java files have 100% line coverage via unit tests.
* All Java files are correctly formatted.

The build uses Spotless and JaCoCo to enforce the above requirements.

If your code has format violations, fix it with this command:

```
$ ./gradlew spotlessApply
```

If you are missing unit test coverage, generate a report to see which lines are not covered:

```
$ ./gradlew jacocoTestReport
```

You can find the report in `build/reports/jacoco/test/html/index.html`.

## Running

You can run the application in Gradle:

```
./gradlew bootRun
```

Alternatively you can run the generated executable JAR directly:

```
$ java -jar build/libs/address-0.0.1-SNAPSHOT.jar
```

Navigate to http://localhost:8080 to explore the application.

## Monitoring

This application exposes a simple heath check, usable for purposes like a Kubernetes liveness check, at `/actuator/health`.

The Spring Actuator can also export Prometheus metrics that can be exported into a Grafana dashboard.