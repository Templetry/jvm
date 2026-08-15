# TemplateApp

Ktor 3 service in Kotlin: routes are extension functions on `Application`, JSON goes through `kotlinx.serialization`, and errors become responses in one place.

## Run

```sh
./gradlew run
```

Listens on `:8080`, or `$PORT`.

## Test

```sh
./gradlew build      # compiles and runs the suite
./gradlew test
```

Tests use `testApplication`, which boots the **same** `module()` the real server does, without binding a port. There is no separate wiring for tests to drift from.

## Layout

```
src/main/kotlin/…/
  Application.kt          main() and module() — the whole server, declared once
  plugins/
    Serialization.kt      JSON, strict about unknown keys
    ErrorHandling.kt      exceptions → status codes
  routes/
    HealthRoutes.kt       /healthz
    HelloRoutes.kt        /api/hello
```

## Adding routes

Write an extension and call it from `module()`:

```kotlin
fun Application.thingRoutes() {
    routing {
        route("/api/things") {
            get { call.respond(emptyList<Thing>()) }
        }
    }
}
```

Request and response types are `@Serializable` data classes — not `Map`s. That is what makes the JSON contract visible in the type system rather than in the handler body.

## Notes

- **Handlers throw, they do not branch on validity.** `require(...)` failures and bodies that will not deserialize both come back as `400` via `StatusPages`, so the happy path stays readable.
- **`ignoreUnknownKeys = false`** on purpose: a client sending a field no type declares gets an error rather than having it silently dropped. Relax it only when you mean to.
- Packaging uses the `application` plugin's distribution rather than a fat jar — no shadow plugin, one less version to keep in step with Kotlin and Ktor.
