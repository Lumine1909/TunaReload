plugins {
    id("java")
    id("java-library")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(8)
    }
}

group = "io.github.lumine1909"
version = "1.1"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    compileOnly("org.spigotmc:spigot:1.12.2-R0.1-SNAPSHOT")
    implementation(project(":impl_1_7"))
    implementation(project(":impl_1_13"))
    implementation(project(":nms_1_7"))
    implementation(project(":nms_1_13"))
    implementation(project(":nms_1_17"))
    implementation(project(":nms_base"))
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }
    shadowJar {
        archiveFileName.set("TunaReload-1.1.jar")
    }
    test {
        useJUnitPlatform()
    }
}