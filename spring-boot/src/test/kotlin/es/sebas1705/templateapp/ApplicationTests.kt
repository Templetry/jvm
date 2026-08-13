package es.sebas1705.templateapp

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests(@Autowired val rest: TestRestTemplate) {

    @Test
    fun `healthz responds ok`() {
        val res = rest.getForEntity("/healthz", String::class.java)
        assertEquals(200, res.statusCode.value())
        assertTrue(res.body!!.contains("ok"))
    }

    @Test
    fun `hello greets by name`() {
        val res = rest.getForEntity("/api/hello/Kotlin", String::class.java)
        assertEquals(200, res.statusCode.value())
        assertTrue(res.body!!.contains("Hello, Kotlin!"))
    }
}
