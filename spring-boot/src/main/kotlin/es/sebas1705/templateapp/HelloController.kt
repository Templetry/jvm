package es.sebas1705.templateapp

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    @GetMapping("/healthz")
    fun healthz(): Map<String, String> = mapOf("status" to "ok")

    @GetMapping("/api/hello/{name}")
    fun hello(@PathVariable name: String): Map<String, String> =
        mapOf("message" to "Hello, $name!")
}
