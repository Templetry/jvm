package es.sebas1705.templateapp

import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ApplicationTest {

    @Test
    fun `healthz responds ok`() = testApplication {
        application { module() }

        val res = client.get("/healthz")
        assertEquals(HttpStatusCode.OK, res.status)
        assertTrue(res.bodyAsText().contains("\"status\":\"ok\""))
    }

    @Test
    fun `hello greets by name`() = testApplication {
        application { module() }

        val res = client.get("/api/hello/Ktor")
        assertEquals(HttpStatusCode.OK, res.status)
        assertTrue(res.bodyAsText().contains("Hello, Ktor!"))
    }

    @Test
    fun `posting a body greets too`() = testApplication {
        application { module() }

        val res = client.post("/api/hello") {
            contentType(ContentType.Application.Json)
            setBody("""{"name":"Ktor"}""")
        }
        assertEquals(HttpStatusCode.OK, res.status)
        assertTrue(res.bodyAsText().contains("Hello, Ktor!"))
    }

    @Test
    fun `an over-long name is rejected`() = testApplication {
        application { module() }

        val res = client.post("/api/hello") {
            contentType(ContentType.Application.Json)
            setBody("""{"name":"${"x".repeat(41)}"}""")
        }
        assertEquals(HttpStatusCode.BadRequest, res.status)
    }

    @Test
    fun `a malformed body is a 400, not a 500`() = testApplication {
        application { module() }

        val res = client.post("/api/hello") {
            contentType(ContentType.Application.Json)
            setBody("""{"nombre":"Ktor"}""")
        }
        assertEquals(HttpStatusCode.BadRequest, res.status)
    }
}
