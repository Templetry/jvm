# Build stage
FROM eclipse-temurin:21 AS build
WORKDIR /src
COPY . .
# installDist, not a fat jar: the application plugin already produces a
# runnable distribution, and a shadow plugin would be one more version to
# keep in step with Kotlin and Ktor.
RUN ./gradlew installDist --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /src/build/install/template-app/ ./
EXPOSE 8080
ENTRYPOINT ["/app/bin/template-app"]
