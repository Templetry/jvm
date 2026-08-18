package es.sebas1705.templateapp.config

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

/**
 * The settings this application reads, bound from the `app` section of the
 * active profile and validated at startup.
 *
 * One typed accessor instead of scattered `@Value` lookups: a missing or
 * nonsensical value fails when the context refreshes, not on the request
 * that first needed it.
 */
@Validated
@ConfigurationProperties(prefix = "app")
data class AppProperties(
    @field:NotBlank
    val environment: String = "development",
    val verboseErrors: Boolean = false,
    @field:Min(0)
    @field:Max(86_400)
    val cacheSeconds: Int = 0,
)
