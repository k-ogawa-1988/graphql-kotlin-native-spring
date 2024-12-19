package com.example.application.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.reactive.config.EnableWebFlux

@Configuration
@EnableWebFlux
@ConditionalOnWebApplication
open class WebFluxConfig {
    @Bean
    open fun corsWebFilter(): CorsWebFilter {
        val source = UrlBasedCorsConfigurationSource()
            .apply {
                // GraphQL
                registerCorsConfiguration("/graphql", graphQLCorsConfig)

                // Add if required
            }

        return CorsWebFilter(source)
    }

    private val graphQLCorsConfig = CorsConfiguration()
        .apply {
            allowedMethods = listOf("GET", "POST")
            allowedHeaders = listOf(CorsConfiguration.ALL)
            allowedOriginPatterns = listOf(
                *listOf("localhost", "127.0.0.1").toAllowedOriginPatterns("http"),
            )
        }

    private fun List<String>?.toAllowedOriginPatterns(schema: String): Array<String> =
        orEmpty().map { "$schema://$it:[${CorsConfiguration.ALL}]" }.toTypedArray()
}
