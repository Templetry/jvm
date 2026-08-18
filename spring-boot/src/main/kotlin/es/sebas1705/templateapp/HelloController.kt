package es.sebas1705.templateapp

// tpl:if environments
import es.sebas1705.templateapp.config.AppProperties
// tpl:endif
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController(
    // tpl:if environments
    private val appProperties: AppProperties,
    // tpl:endif
) {

    @GetMapping("/healthz")
    fun healthz(): Map<String, String> =
        // tpl:if environments
        mapOf("status" to "ok", "environment" to appProperties.environment)
        // tpl:endif
        // tpl:if !environments
        mapOf("status" to "ok")
        // tpl:endif

    @GetMapping("/api/hello/{name}")
    fun hello(@PathVariable name: String): Map<String, String> =
        mapOf("message" to "Hello, $name!")
}
