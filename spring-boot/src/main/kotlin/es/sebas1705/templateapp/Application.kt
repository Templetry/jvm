package es.sebas1705.templateapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

// TemplateApp service entry point.
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
