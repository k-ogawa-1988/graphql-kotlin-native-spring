plugins {
    kotlin("jvm") version "2.1.0"
    id("com.expediagroup.graphql") version "8.2.1"
    id("io.sentry.jvm.gradle") version "4.14.1"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.graalvm.buildtools.native") version "0.10.4"
    id("org.springframework.boot") version "3.2.5"
    application
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("io.github.classgraph:classgraph:4.8.179")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    compileOnly("org.springframework.boot:spring-boot-devtools")

    implementation("com.expediagroup:graphql-kotlin-hooks-provider:8.2.1")
    implementation("com.expediagroup:graphql-kotlin-spring-server:8.2.1")
    implementation("com.graphql-java:graphql-java-extended-scalars:21.0")
}

configurations.all {
    resolutionStrategy {
        eachDependency {
            // Fix jackson version
            if (requested.group.startsWith("com.fasterxml.jackson")) {
                useVersion("2.18.2")
            }
        }
    }
}

sourceSets {
    create("graphqlGraalVm")
}

application {
    mainClass.set("com.example.application.ApplicationKt")
}

graalvmNative {
    toolchainDetection.set(false)
    binaries {
        metadataRepository.enabled = true
        all {
            resources.autodetect()
            verbose = true
            quickBuild = true
            richOutput = true
            buildArgs.add("-H:+UnlockExperimentalVMOptions")
            buildArgs.add("-march=native")
            buildArgs.add("-O4")
            buildArgs.add("--enable-https")
            buildArgs.add("--strict-image-heap")
            buildArgs.add(
                buildString {
                    append("--initialize-at-build-time=")
                    append(
                        listOf(
                            "ch.qos.logback.",
                            "com.alibaba.fastjson2.",
                            "org.slf4j.",
                            "org.springframework.boot.ansi.",
                            "org.springframework.boot.logging.logback.ColorConverter",
                        ).joinToString(",")
                    )
                }
            )
            buildArgs.add(
                buildString {
                    append("--initialize-at-run-time=")
                    append(
                        listOf(
                            "io.netty.",
                        ).joinToString(",")
                    )
                }
            )
        }
        named("main") {
            imageName = "application"
            mainClass = "com.example.application.ApplicationKt"
        }
    }
}

graphql {
    graalVm {
        packages = listOf(
            "com.example.application.controller.graphql",
            "com.example.domain.enum_",
        )
    }
}
