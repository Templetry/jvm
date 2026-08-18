package es.sebas1705.templateapp

import es.sebas1705.templateapp.config.AppProperties
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

// Proves the profiles are wired, not decorative: each one boots its own
// Spring context and AppProperties is read back from it. Three contexts
// rather than one parametrized test, because @ActiveProfiles is fixed per
// class — this is what "each profile actually boots" looks like in Spring.

@SpringBootTest
@ActiveProfiles("development")
class DevelopmentProfileTests(@Autowired val appProperties: AppProperties) {

    @Test
    fun `declares its own name`() {
        assertEquals("development", appProperties.environment)
    }

    @Test
    fun `keeps detail on and caching off`() {
        assertTrue(appProperties.verboseErrors)
        assertEquals(0, appProperties.cacheSeconds)
    }
}

@SpringBootTest
@ActiveProfiles("staging")
class StagingProfileTests(@Autowired val appProperties: AppProperties) {

    @Test
    fun `declares its own name`() {
        assertEquals("staging", appProperties.environment)
    }

    @Test
    fun `differs from both neighbours`() {
        // Staging exists to be production-like while still debuggable, so
        // it is the one profile whose values must not equal either
        // neighbour's.
        assertTrue(appProperties.verboseErrors)
        assertEquals(30, appProperties.cacheSeconds)
    }
}

@SpringBootTest
@ActiveProfiles("production")
class ProductionProfileTests(@Autowired val appProperties: AppProperties) {

    @Test
    fun `declares its own name`() {
        assertEquals("production", appProperties.environment)
    }

    @Test
    fun `turns detail off and caches longest`() {
        assertFalse(appProperties.verboseErrors)
        assertEquals(300, appProperties.cacheSeconds)
    }
}
