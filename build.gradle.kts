import com.diffplug.spotless.LineEnding
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("java-library")
    kotlin("jvm") version Kotlin.version
    id("com.diffplug.spotless") version "6.20.0"
    id("maven-publish")
}

repositories {
    mavenCentral()
    maven("https://packages.confluent.io/maven/")
}

group = "com.github.navikt"

kotlin {
    jvmToolchain(17)
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
                description.set("Holder definisjonen av dagpenger inntekt (brukt av [dp-inntekt] og 'packet'. Packet er dagpengers gamle svar på JsonMessage på [Rapid and rivers]. Brukes stort sett bare av [dp-regel*] riggen. ")
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
        ktlint()
    }
    kotlinGradle {
        target("*.gradle.kts", "buildSrc/**/*.kt*")
        ktlint()
    }

    // Workaround for <https://github.com/diffplug/spotless/issues/1644>
    // using idea found at
    // <https://github.com/diffplug/spotless/issues/1527#issuecomment-1409142798>.
    lineEndings = LineEnding.PLATFORM_NATIVE // or any other except GIT_ATTRIBUTES
}
