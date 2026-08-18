package es.sebas1705.templateapp.config

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

// Proves the profiles are wired, not decorative: each one is loaded and read
// back, so renaming a file or a key breaks here rather than in whatever
// environment happened to depend on it.
class ConfigTest {

    @Test
    fun `each profile declares its own name`() {
        for (name in ENVIRONMENTS) {
            assertEquals(name, loadConfig(profile = name).environment)
        }
    }

    @Test
    fun `development keeps detail on and caching off`() {
        val config = loadConfig(profile = "development")
        assertTrue(config.verboseErrors)
        assertEquals(0, config.cacheSeconds)
    }

    @Test
    fun `production turns detail off and caches longest`() {
        val config = loadConfig(profile = "production")
        assertFalse(config.verboseErrors)
        assertEquals(300, config.cacheSeconds)
    }

    @Test
    fun `staging differs from both neighbours`() {
        // Staging exists to be production-like while still debuggable, so
        // it is the one profile whose values must not equal either
        // neighbour's.
        val config = loadConfig(profile = "staging")
        assertTrue(config.verboseErrors)
        assertEquals(30, config.cacheSeconds)
    }

    @Test
    fun `an unknown profile fails loudly`() {
        assertFailsWith<IllegalStateException> { loadConfig(profile = "qa") }
    }

    @Test
    fun `no profile argument falls back to development`() {
        // APP_ENV is not set in the test process, so the default applies.
        assertEquals("development", loadConfig().environment)
    }
}
