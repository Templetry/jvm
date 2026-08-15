package es.sebas1705.templateapp.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(val error: String)

/**
 * One place that turns exceptions into responses, so handlers can throw and
 * stay about the happy path — and so a stack trace never reaches a client.
 */
fun Application.configureErrorHandling() {
    install(StatusPages) {
        exception<BadRequestException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(cause.message ?: "bad request"))
        }
        exception<IllegalArgumentException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(cause.message ?: "bad request"))
        }
        exception<Throwable> { call, cause ->
            call.application.log.error("unhandled failure", cause)
            call.respond(HttpStatusCode.InternalServerError, ErrorResponse("internal error"))
        }
    }
}
