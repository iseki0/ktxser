import java.util.*

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
    signing
    `maven-publish`
}

allprojects {
    group = "space.iseki.ktxser"
    version = properties["version"]?.let { it as String }?.takeIf { "unspecified" !in it } ?: "0.3-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

dependencies {
    commonMainApi(libs.kotlinx.serialization.core)
    commonTestImplementation(kotlin("test"))
    commonTestImplementation(libs.kotlinx.serialization.json)
}

kotlin {
    jvmToolchain(17)
    targets {
        jvm {
            withJava()
            compilerOptions {
                freeCompilerArgs.add("-Xjvm-default=all-compatibility")
            }
        }
    }
}

tasks.withType<AbstractArchiveTask> {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}

tasks.getByName("jvmJar", Jar::class) {
    into("/") {
        from("/LICENSE")
        from("/NOTICE")
    }
}

tasks.withType<Test> {
    testLogging {
        events("passed", "skipped", "failed")
    }
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
        withType<MavenPublication> {
            val pubName = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else "$it" }
            val emptyJavadocJar by tasks.register<Jar>("emptyJavadocJar$pubName") {
                archiveClassifier = "javadoc"
                archiveBaseName = artifactId
            }
            artifact(emptyJavadocJar)
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
