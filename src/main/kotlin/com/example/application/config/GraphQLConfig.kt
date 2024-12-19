package com.example.application.config

import com.expediagroup.graphql.generator.SimpleTypeResolver
import com.expediagroup.graphql.generator.directives.KotlinDirectiveWiringFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnWebApplication
open class GraphQLConfig {
    /** Avoid class scanning for GraalVM */
    @Bean
    open fun typeResolver() = SimpleTypeResolver(mapOf())

    @Bean
    open fun wiringFactory() = KotlinDirectiveWiringFactory()
}
