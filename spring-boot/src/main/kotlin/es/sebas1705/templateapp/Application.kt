package es.sebas1705.templateapp

// tpl:if environments
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
// tpl:endif
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

// TemplateApp service entry point.
@SpringBootApplication
// tpl:if environments
@ConfigurationPropertiesScan
// tpl:endif
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
