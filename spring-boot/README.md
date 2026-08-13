# TemplateApp

Spring Boot service generated with [Templetry](https://github.com/Templetry): Kotlin, Gradle Kotlin DSL, web starter, RANDOM_PORT integration tests, optional Dockerfile.

```sh
./gradlew bootRun          # :8080
./gradlew build            # compile + tests
docker build -t template-app .   # docker feature
```

Routes: `GET /healthz` · `GET /api/hello/{name}`.
