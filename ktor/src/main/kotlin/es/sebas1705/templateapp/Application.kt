package es.sebas1705.templateapp

// tpl:if environments
import es.sebas1705.templateapp.config.loadConfig
// tpl:endif
import es.sebas1705.templateapp.plugins.configureErrorHandling
import es.sebas1705.templateapp.plugins.configureSerialization
import es.sebas1705.templateapp.routes.healthRoutes
import es.sebas1705.templateapp.routes.helloRoutes
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080

    // tpl:if environments
    // A broken profile stops the process here, before it accepts a request:
    // loadConfig throws, and an uncaught exception in main() crashes the JVM.
    val config = loadConfig()
    println("template-app starting in ${config.environment} (log level ${config.logLevel})")
    embeddedServer(Netty, port = port, host = "0.0.0.0") { module(config.environment) }.start(wait = true)
    // tpl:endif
    // tpl:if !environments
    embeddedServer(Netty, port = port, host = "0.0.0.0", module = Application::module).start(wait = true)
    // tpl:endif
}

/**
 * The TemplateApp application module.
 *
 * Everything the server is happens here, which is what lets `testApplication`
 * boot exactly this — not an approximation of it — without binding a port.
 */
fun Application.module(environment: String? = null) {
    configureSerialization()
    configureErrorHandling()

    healthRoutes(environment)
    helloRoutes()
}
