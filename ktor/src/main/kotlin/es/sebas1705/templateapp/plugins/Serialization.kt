package es.sebas1705.templateapp.plugins

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json

/** JSON in and out, from `@Serializable` types rather than reflection. */
fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = false
                // A field the client sends that no @Serializable class
                // declares is an error, not something quietly dropped.
                ignoreUnknownKeys = false
                explicitNulls = false
            },
        )
    }
}
