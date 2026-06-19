# Common

Common is a reusable Java library for Spring Boot services that provides a small set of shared building blocks for web APIs, persistence, and error handling.

It is designed to help applications avoid duplicating common infrastructure code such as auditing fields, pagination DTOs, standardized exception responses, and utility components.

## Features

- Shared JPA base entity support with auditing fields and optimistic locking
- Pagination DTOs for clean API responses
- Centralized exception handling for business errors and validation issues
- Spring Boot auto-configuration support for web and JPA-related components
- Small utility helpers for Spring context access and transaction handling

## Project structure

- `src/main/java/io/github/mehranmirkhan/common/model` – base entities and DTOs
- `src/main/java/io/github/mehranmirkhan/common/error` – error models and exception handlers
- `src/main/java/io/github/mehranmirkhan/common/util` – reusable utility classes
- `src/main/java/io/github/mehranmirkhan/common/CommonAutoConfiguration.java` – auto-configuration entry points

## Requirements

- Java 25
- Maven 3.9+
- Spring Boot 4.x compatible environment

## Installation

Add the dependency to your Maven project:

```xml
<dependency>
  <groupId>io.github.mehranmirkhan</groupId>
  <artifactId>common</artifactId>
  <version>0.0.1</version>
</dependency>
```

## Usage examples

### Base entity for JPA models

Extend the provided base entity to inherit common auditing and versioning fields:

```java
public class Book extends BaseEntity<UUID> {
  private String title;
}
```

### Standardized pagination response

Use `PageDto` to expose paged results in a consistent format:

```java
PageDto<BookDto> response = PageDto.of(page);
```

### Exception handling

The library provides global exception handling for common web and validation failures. When included in a Spring Boot application, it will register handlers for business exceptions and bad requests automatically.

## Build and test

Run the following commands from the project root:

```bash
mvn clean test
mvn package
```

## License

This project is licensed under the Apache License 2.0.
