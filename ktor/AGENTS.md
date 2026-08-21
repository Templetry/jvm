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

```sh templetry:checks
./gradlew build --no-daemon
```

## Safe Change Workflow

1. Read the affected files fully before editing.
2. Make the smallest change that solves the task.
3. Build and test, then review the diff with git before committing.

## This project came from a template

Four facts you cannot infer from the code in front of you:

- **Never hand-edit `.templetry-answers.yml`.** It records what generated this project. Editing it makes the next update merge against a state that never existed.
- **Before writing a capability by hand, run `templetry pieces`.** Auth, RBAC, audit trails, API keys and whole CRUD resources may already exist as pieces for this template. Adopting one is `templetry add <name>`, and it brings its own tests.
- **`templetry update` pulls improvements from the template** through a three-way merge that keeps your edits. Use it instead of copying files from the template by hand.
- **Directives like `tpl:if` belong to the template, not here.** If you find one in this project, it is a rendering bug worth reporting — do not try to interpret it.
