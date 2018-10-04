plugins {
    id("java-library")
    id("com.diffplug.gradle.spotless") version "3.13.0"
    id("com.commercehub.gradle.plugin.avro") version "0.14.2"
    id("maven-publish")
}

buildscript {
    dependencies {
        classpath("com.cinnober.gradle:semver-git:2.2.0")
    }
}

apply {
    plugin("com.diffplug.gradle.spotless")
    plugin("com.cinnober.gradle.semver-git")
}

repositories {
    jcenter()
    maven(url = "http://packages.confluent.io/maven/")
}

group = "no.nav.dagpenger"
version = "0.1.5-SNAPSHOT"

val avroVersion = "1.8.2"

dependencies {
    api("org.apache.avro:avro:$avroVersion")
}

publishing {
    publications {
        create("default", MavenPublication::class.java) {
            from(components["java"])
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
