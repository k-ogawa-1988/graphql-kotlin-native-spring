package com.example.application

import io.github.classgraph.ClassGraph
import org.springframework.aot.hint.MemberCategory
import org.springframework.aot.hint.RuntimeHints
import org.springframework.aot.hint.RuntimeHintsRegistrar
import org.springframework.aot.hint.registerType

class CustomRuntimeHints : RuntimeHintsRegistrar {
    companion object {
        private val classGraph = ClassGraph()
    }

    override fun registerHints(hints: RuntimeHints, classLoader: ClassLoader?) {
        hints.reflection().apply {
            // GraphQL server
            registerType<com.expediagroup.graphql.server.types.GraphQLServerRequestDeserializer>(
                MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS,
                MemberCategory.INVOKE_PUBLIC_METHODS,
            )
            registerType<com.expediagroup.graphql.server.types.GraphQLServerResponse>(
                MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS,
                MemberCategory.INVOKE_PUBLIC_METHODS,
            )
            // GraphQL (application)
            classGraph.acceptPackages("com.example.application.controller.graphql")
                .enableClassInfo()
                .scan()
                .use { it.allClasses.loadClasses() }
                .forEach {
                    println("registerType: ${it.name}")
                    registerType(
                        it,
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS,
                        MemberCategory.INVOKE_PUBLIC_METHODS,
                    )
                }
            // GraphQL (domain enum)
            classGraph.acceptPackages("com.example.domain.enum_")
                .enableClassInfo()
                .scan()
                .use { it.allEnums.loadClasses() }
                .forEach {
                    println("registerType: ${it.name}")
                    registerType(it, MemberCategory.PUBLIC_FIELDS)
                }
            // Kotlin value class
            classGraph.acceptPackages("com.example.domain.query.entity")
                .enableClassInfo()
                .enableAnnotationInfo()
                .scan()
                .use { it.getClassesWithAnyAnnotation(JvmInline::class.java).loadClasses() }
                .forEach {
                    println("registerType: ${it.name}")
                    registerType(it, *MemberCategory.entries.toTypedArray())
                }
        }
    }
}
