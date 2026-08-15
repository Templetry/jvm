# AGENTS

Operating contract for AI agents and automation helpers working in this project.

## Mission

- Keep this a plain Ktor service: routes as extensions on `Application`, plugins for cross-cutting concerns, no framework layers until the app actually needs them.

## Core Rules

- A route group is a `fun Application.xxxRoutes()` in `routes/`, called from `module()`. `main()` stays a shell that reads the port.
- **Everything the server is lives in `module()`.** Tests boot that same function, so configuration added anywhere else is configuration the tests do not cover.
- Request and response bodies are `@Serializable` data classes, never `Map<String, String>`.
- Handlers **throw** on invalid input (`require`) and let `StatusPages` map it. Do not return error payloads by hand.
- Do not set `ignoreUnknownKeys = true` to make a request pass. If a field should be accepted, declare it.
- Every route gets a `testApplication` test.
- Update docs in the same change when behavior or process changes.

## Required Checks Before Finishing

- `./gradlew build` compiles clean and the suite passes.

## Safe Change Workflow

1. Read the affected files fully before editing.
2. Make the smallest change that solves the task.
3. Build and test, then review the diff with git before committing.
