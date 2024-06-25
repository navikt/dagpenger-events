import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-library")
    kotlin("jvm") version Kotlin.version
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
    id("maven-publish")
}

repositories {
    mavenCentral()
    maven("https://packages.confluent.io/maven/")
}

group = "com.github.navikt"

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    implementation(Ulid.ulid)

    implementation(Moshi.moshi)
    implementation(Moshi.moshiAdapters)
    implementation(Moshi.moshiKotlin) {
        exclude(module = "kotlin-stdlib") // Brings in Kotlin 1.2.71
        exclude(module = "kotlin-reflect") // Brings in Kotlin 1.2.71 - https://github.com/square/moshi/pull/825 fixes this when released
    }
    testImplementation(kotlin("test-junit5"))
    testImplementation(Junit5.api)
    testImplementation(KoTest.runner)
    testImplementation(Json.library)
    testRuntimeOnly(Junit5.engine)

    implementation(Prometheus.common)
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
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

val githubUser: String? by project
val githubPassword: String? by project

publishing {

    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/navikt/dagpenger-events")
            credentials {
                username = githubUser
                password = githubPassword
            }
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(sourcesJar.get())

            pom {
                name.set("dagpenger-events")
                description.set(
                    """Holder definisjonen av dagpenger inntekt (brukt av [dp-inntekt] og 'packet'. 
                        |Packet er dagpengers gamle svar på JsonMessage på [Rapid and rivers]. Brukes stort sett bare av [dp-regel*] riggen. 
                    """.trimMargin(),
                )
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

tasks.withType<KotlinCompile>().configureEach {
    dependsOn("ktlintFormat")
}
