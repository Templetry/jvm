package es.sebas1705.templateapp

import es.sebas1705.templateapp.plugins.configureErrorHandling
import es.sebas1705.templateapp.plugins.configureSerialization
import es.sebas1705.templateapp.routes.healthRoutes
import es.sebas1705.templateapp.routes.helloRoutes
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    embeddedServer(Netty, port = port, host = "0.0.0.0", module = Application::module).start(wait = true)
}

/**
 * The TemplateApp application module.
 *
 * Everything the server is happens here, which is what lets `testApplication`
 * boot exactly this — not an approximation of it — without binding a port.
 */
fun Application.module() {
    configureSerialization()
    configureErrorHandling()

    healthRoutes()
    helloRoutes()
}
