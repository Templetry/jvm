package es.sebas1705.templateapp.routes

import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable

@Serializable
data class HealthResponse(val status: String)

/** Liveness probe. A route group is an extension on Application. */
fun Application.healthRoutes() {
    routing {
        get("/healthz") {
            call.respond(HealthResponse(status = "ok"))
        }
    }
}
