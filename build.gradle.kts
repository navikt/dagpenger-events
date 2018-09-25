plugins {
    id("java-library")
    id("com.diffplug.gradle.spotless") version "3.13.0"
    id("com.commercehub.gradle.plugin.avro") version "0.14.2"
}

apply {
    plugin("com.diffplug.gradle.spotless")
}

repositories {
    jcenter()
    maven(url = "http://packages.confluent.io/maven/")
}

group = "no.nav.dagpenger"
version = "0.0.1"

val kotlinLoggingVersion = "1.4.9"
val avroVersion = "1.8.2"

dependencies {
    api("org.apache.avro:avro:$avroVersion")
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
