## Getting Started

### Prerequisites
- PostgreSQL DB locally or on cloud.
- Java
- Gradle

### Configuration
Update the `application.properties` with your DB credentials.
```agsl
spring.datasource.url=<DB_HOST_WITH_DB_URL>
spring.datasource.username=<DB_USER>
spring.datasource.password=<DB_PASSWORD>
```

### Run Application
1. Navigate to the root directory of the project in Terminal.
2. Run command `./gradlew bootRun`