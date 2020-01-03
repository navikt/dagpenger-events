import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-library")
    kotlin("jvm") version Kotlin.version
    id("com.commercehub.gradle.plugin.avro") version "0.16.0"
    id(Spotless.spotless) version Spotless.version
    id("maven-publish")
}

repositories {
    jcenter()
    maven("http://packages.confluent.io/maven/")
    maven("https://jitpack.io")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

group = "com.github.navikt"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    implementation(Avro.avro)

    implementation(Ulid.ulid)

    implementation(Moshi.moshi)
    implementation(Moshi.moshiAdapters)
    implementation(Moshi.moshiKotlin) {
        exclude(module = "kotlin-stdlib") // Brings in Kotlin 1.2.71
        exclude(module = "kotlin-reflect") // Brings in Kotlin 1.2.71 - https://github.com/square/moshi/pull/825 fixes this when released
    }
    testImplementation(kotlin("test-junit5"))
    testImplementation(Junit5.api)
    testImplementation(Junit5.kotlinRunner)
    testImplementation(Json.library)
    testRuntimeOnly(Junit5.engine)

    implementation(Prometheus.common)
    implementation(Prometheus.hotspot)
    implementation(Micrometer.prometheusRegistry)
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

tasks.named("compileKotlin") {
    dependsOn("spotlessCheck")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        showExceptions = true
        showStackTraces = true
        exceptionFormat = TestExceptionFormat.FULL
        events = setOf(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
    }
}

publishing {
    publications {
        create("mavenJava", MavenPublication::class.java) {
            from(components["java"])
            artifact(sourcesJar.get())

            pom {
                name.set("dagpenger-events")
                description.set("Avro schemas for dagpenger events")
                url.set("https://github.com/navikt/dagpenger-events")
                withXml {
                    asNode().appendNode("packaging", "jar")
                }
                licenses {
                    license {
                        name.set("MIT License")
                        name.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        organization.set("NAV (Arbeids- og velferdsdirektoratet) - The Norwegian Labour and Welfare Administration")
                        organizationUrl.set("https://www.nav.no")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/navikt/dagpenger-events.git")
                    developerConnection.set("scm:git:git://github.com/navikt/dagpenger-events.git")
                    url.set("https://github.com/navikt/dagpenger-events")
                }
            }
        }
    }
}

spotless {
    kotlin {
        ktlint(Klint.version)
    }
    kotlinGradle {
        target("*.gradle.kts", "additionalScripts/*.gradle.kts")
        ktlint(Klint.version)
    }
}
