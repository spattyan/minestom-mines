plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.0"
    id("io.freefair.lombok") version "8.10"

}

group = "com.yanspatt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("net.minestom:minestom-snapshots:d0754f2a15")

    implementation("redis.clients:jedis:5.2.0")
    implementation("com.google.guava:guava:33.3.1-jre")


}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21)) // Minestom has a minimum Java version of 21
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks {
    jar {
        manifest {
            attributes["Main-Class"] = "com.yanspatt.Main" // Change this to your main class
        }
    }

    build {
        dependsOn(shadowJar)
    }
    shadowJar {
        mergeServiceFiles()
        archiveClassifier.set("") // Prevent the -all suffix on the shadowjar file.
    }
}