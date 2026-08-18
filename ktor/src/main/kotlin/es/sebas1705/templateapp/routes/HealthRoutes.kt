package es.sebas1705.templateapp.routes

import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable

// `environment` stays null (and is dropped by explicitNulls = false in
// Serialization.kt) when this project has no profiles — one response shape
// serves both feature combinations without a directive in this file.
@Serializable
data class HealthResponse(val status: String, val environment: String? = null)

/** Liveness probe. A route group is an extension on Application. */
fun Application.healthRoutes(environment: String? = null) {
    routing {
        get("/healthz") {
            call.respond(HealthResponse(status = "ok", environment = environment))
        }
    }
}
