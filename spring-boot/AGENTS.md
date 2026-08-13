# AGENTS

Operating contract for AI agents and automation helpers working in this project.

## Mission

- Keep this service focused: Spring Boot web starter + Kotlin; add starters (data, security) only when the app actually needs them.

## Core Rules

- One package root (`es.sebas1705.templateapp`); controllers thin, logic in services when it appears.
- Kotlin idioms over Java ones: data classes, null-safety, no field injection (constructor only).
- Every endpoint gets a RANDOM_PORT test in `ApplicationTests` (or a sibling test class).
- Update docs in the same change when behavior or process changes.

## Required Checks Before Finishing

- `./gradlew build` passes (compiles + tests).

## Safe Change Workflow

1. Read the affected files fully before editing.
2. Make the smallest change that solves the task.
3. Build, then review the diff with git before committing.
