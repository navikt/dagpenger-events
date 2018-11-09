plugins {
    id("java-library")
    id("com.diffplug.gradle.spotless") version "3.13.0"
    id("com.commercehub.gradle.plugin.avro") version "0.14.2"
    id("maven-publish")
}

buildscript {
    repositories {
        maven("https://repo.adeo.no/repository/maven-central")
    }
    dependencies {
        classpath("com.cinnober.gradle:semver-git:2.2.0")
    }
}

apply {
    plugin("com.diffplug.gradle.spotless")
    plugin("com.cinnober.gradle.semver-git")
}

repositories {
    mavenCentral()
    maven("http://packages.confluent.io/maven/")
}

group = "no.nav.dagpenger"
version = "0.1.7-SNAPSHOT"

val avroVersion = "1.8.2"

dependencies {
    api("org.apache.avro:avro:$avroVersion")
}

val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets["main"].allSource)
}

publishing {


    publications {
        create("default", MavenPublication::class.java) {
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
                    connection.set("scm:git:git://github.com/navikt/kafka-embedded-env.git")
                    developerConnection.set("scm:git:git://github.com/navikt/kafka-embedded-env.git")
                    url.set("https://github.com/navikt/kafka-embedded-env")
                }

            }


        }
    }

    repositories {
        maven {
            val version = project.version as String

            url = if (version.endsWith("-SNAPSHOT")) {
                uri("https://repo.adeo.no/repository/maven-snapshots/")
            } else {
                uri("https://repo.adeo.no/repository/maven-releases/")
            }
        }
    }
}

spotless {
    kotlin {
        ktlint()
    }
    kotlinGradle {
        target("*.gradle.kts", "additionalScripts/*.gradle.kts")
        ktlint()
    }
}
