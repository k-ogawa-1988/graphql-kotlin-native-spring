package com.example.application.controller.graphql.hooks

import com.expediagroup.graphql.generator.hooks.SchemaGeneratorHooks
import com.expediagroup.graphql.plugin.schema.hooks.SchemaGeneratorHooksProvider
import graphql.scalars.ExtendedScalars
import graphql.schema.GraphQLType
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.net.URL
import java.time.LocalDate
import java.time.OffsetDateTime
import kotlin.reflect.KType

class CustomSchemaGeneratorHooksProvider : SchemaGeneratorHooksProvider {
    override fun hooks() = CustomSchemaGeneratorHooks()
}

@Component
class CustomSchemaGeneratorHooks : SchemaGeneratorHooks {
    /**
     * Register additional GraphQL scalar types.
     */
    override fun willGenerateGraphQLType(type: KType): GraphQLType? = when (type.classifier) {
        LocalDate::class -> ExtendedScalars.Date
        OffsetDateTime::class -> ExtendedScalars.DateTime
        Long::class -> ExtendedScalars.GraphQLLong
        URL::class -> ExtendedScalars.Url
        BigDecimal::class -> ExtendedScalars.GraphQLBigDecimal
        else -> super.willGenerateGraphQLType(type)
    }
}
