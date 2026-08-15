# Templetry parent: jvm

JVM templates for [Templetry](https://github.com/Templetry). One **parent repo**, multiple **forms** — each form is a subdirectory that compiles on its own and carries its own `template.yml` ([ADR-0011](https://github.com/Templetry/wiki/blob/main/adr/0011-template-forms.md)).

| Form | What it is | Status |
|---|---|---|
| [`spring-boot/`](spring-boot/) | Spring Boot service — Kotlin, Gradle Kotlin DSL, web starter, RANDOM_PORT tests | ✅ ready |
| [`ktor/`](ktor/) | Ktor 3 service — routes as extensions, kotlinx.serialization, testApplication suite | ✅ ready |

## Usage

```sh
templetry init jvm/spring-boot --out ./my-svc \
  --set "project_name=My Service" --set "base_package=com.me.mysvc"
```

Forms are **chosen**, not combined. Inside a form, the manifest's features are freely combinable.
