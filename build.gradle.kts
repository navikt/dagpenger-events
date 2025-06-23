import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-library")
    kotlin("jvm") version "2.2.0"
    id("org.jlleitschuh.gradle.ktlint") version "12.3.0"
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
    implementation("de.huxhorn.sulky:de.huxhorn.sulky.ulid:8.3.0")
    implementation("io.prometheus:simpleclient_common:0.16.0")

    val moshiVersion = "1.15.2"
    implementation("com.squareup.moshi:moshi:$moshiVersion")
    implementation("com.squareup.moshi:moshi-adapters:$moshiVersion")
    implementation("com.squareup.moshi:moshi-kotlin:$moshiVersion")

    testImplementation(kotlin("test"))
    testImplementation("org.json:json:20250107")
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.9.1")
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
