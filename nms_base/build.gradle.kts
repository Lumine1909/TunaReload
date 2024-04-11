plugins {
    id("java")
    id("java-library")
}
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(8)
    }
}
group = "io.github.lumine1909"
version = "unspecified"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    compileOnly("org.spigotmc:spigot-api:1.13.2-R0.1-SNAPSHOT")
}


tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}