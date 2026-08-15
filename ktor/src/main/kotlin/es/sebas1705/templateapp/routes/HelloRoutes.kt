package es.sebas1705.templateapp.routes

import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable

@Serializable
data class GreetRequest(val name: String)

@Serializable
data class GreetResponse(val message: String)

private const val MAX_NAME = 40

fun Application.helloRoutes() {
    routing {
        route("/api/hello") {
            get("/{name}") {
                val name = call.parameters["name"].orEmpty()
                call.respond(GreetResponse(greet(name)))
            }

            post {
                // receive() throws when the body does not deserialize into
                // GreetRequest; StatusPages turns that into a 400, so this
                // handler never checks for absence.
                val body = call.receive<GreetRequest>()
                call.respond(GreetResponse(greet(body.name)))
            }
        }
    }
}

private fun greet(name: String): String {
    require(name.isNotBlank()) { "name is required" }
    require(name.length <= MAX_NAME) { "name must be at most $MAX_NAME characters" }
    return "Hello, $name!"
}
