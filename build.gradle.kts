plugins {
    `java-library`
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.serialization") version "2.0.21"
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.16.3"
    `maven-publish`
    signing
}

allprojects {
    group = "space.iseki.ktxser"
    version = properties["version"]?.let { it as String }?.takeIf { "unspecified" !in it } ?: "0.2-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

dependencies {
    testImplementation(kotlin("test"))
    api("org.jetbrains.kotlinx:kotlinx-serialization-core:1.7.3")
    testImplementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
}

tasks.test {
    useJUnitPlatform()
}

java {
    withSourcesJar()
}

kotlin {
    jvmToolchain(17)
}

tasks.withType<AbstractArchiveTask> {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}

val emptyJavadoc = tasks.create("emptyJavadoc", Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing {
    repositories {
        maven {
            name = "Central"
            afterEvaluate {
                url = if (version.toString().endsWith("SNAPSHOT")) {
                    // uri("https://s01.oss.sonatype.org/content/repositories/snapshots")
                    uri("https://oss.sonatype.org/content/repositories/snapshots")
                } else {
                    // uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2")
                    uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
                }
            }
            credentials {
                username = properties["ossrhUsername"]?.toString() ?: System.getenv("OSSRH_USERNAME")
                password = properties["ossrhPassword"]?.toString() ?: System.getenv("OSSRH_PASSWORD")
            }
        }
    }

    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(emptyJavadoc)
        }
        withType<MavenPublication> {
            pom {
                val projectUrl = "https://github.com/iseki0/ktxser"
                name = "PEFile"
                description = "Additional serializers for kotlinx.serialization"
                url = projectUrl
                licenses {
                    license {
                        name = "Apache-2.0"
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                    }
                }
                developers {
                    developer {
                        id = "iseki0"
                        name = "iseki zero"
                        email = "iseki@iseki.space"
                    }
                }
                inceptionYear = "2024"
                scm {
                    connection = "scm:git:$projectUrl.git"
                    developerConnection = "scm:git:$projectUrl.git"
                    url = projectUrl
                }
                issueManagement {
                    system = "GitHub"
                    url = "$projectUrl/issues"
                }
                ciManagement {
                    system = "GitHub"
                    url = "$projectUrl/actions"
                }
            }
        }
    }

}

signing {
    useGpgCmd()
    sign(publishing.publications)
}
