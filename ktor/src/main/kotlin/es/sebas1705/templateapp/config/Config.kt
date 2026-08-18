package es.sebas1705.templateapp.config

import java.io.File

/**
 * The active environment profile.
 *
 * Ktor itself blesses no single mechanism for this — HOCON via
 * `application.conf` is common, but this form deliberately skips the Ktor
 * Gradle plugin and `EngineMain`, so the convention is the same one used for
 * Go and Rust: one `.env.<profile>` at the module root, selected by
 * `APP_ENV`, parsed into a validated data class. Nothing else in the module
 * reads `System.getenv` for settings.
 */

/** The closed set of profiles (ADR-0018). */
val ENVIRONMENTS = listOf("development", "staging", "production")

data class AppConfig(
    val environment: String,
    val logLevel: String,
    val verboseErrors: Boolean,
    val cacheSeconds: Int,
)

/**
 * Loads a profile by name, or the one `APP_ENV` selects.
 *
 * `.env.local` layers on top when present and is gitignored; a real
 * environment variable beats both, which is what lets a container run with
 * no profile file in the image.
 */
fun loadConfig(root: File = File("."), profile: String? = null): AppConfig {
    val name = profile ?: System.getenv("APP_ENV") ?: "development"

    val values = mutableMapOf<String, String>()
    for (fileName in listOf(".env.$name", ".env.local")) {
        readInto(values, File(root, fileName))
    }
    for (key in listOf("ENVIRONMENT", "LOG_LEVEL", "VERBOSE_ERRORS", "CACHE_SECONDS")) {
        System.getenv(key)?.let { values[key] = it }
    }

    val environment = values["ENVIRONMENT"]
        ?: error("config: ENVIRONMENT is missing from profile \"$name\"")
    require(environment in ENVIRONMENTS) {
        "config: unknown ENVIRONMENT \"$environment\" (want one of $ENVIRONMENTS)"
    }
    val logLevel = values["LOG_LEVEL"]
        ?: error("config: LOG_LEVEL is missing from profile \"$name\"")
    val cacheSeconds = values["CACHE_SECONDS"]?.toIntOrNull()
        ?: error("config: CACHE_SECONDS must be a non-negative integer, got ${values["CACHE_SECONDS"]?.let { "\"$it\"" }}")
    require(cacheSeconds >= 0) { "config: CACHE_SECONDS must be a non-negative integer" }

    return AppConfig(
        environment = environment,
        logLevel = logLevel,
        verboseErrors = values["VERBOSE_ERRORS"] == "true",
        cacheSeconds = cacheSeconds,
    )
}

/** Parses `KEY=VALUE` lines. A missing file is not an error: only the
 * selected profile has to exist, and `.env.local` usually does not. */
private fun readInto(values: MutableMap<String, String>, file: File) {
    if (!file.exists()) return
    file.forEachLine { rawLine ->
        val line = rawLine.trim()
        if (line.isEmpty() || line.startsWith("#")) return@forEachLine
        val eq = line.indexOf('=')
        if (eq > 0) {
            values[line.substring(0, eq).trim()] = line.substring(eq + 1).trim().trim('"', '\'')
        }
    }
}
