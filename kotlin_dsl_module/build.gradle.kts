plugins {
    id("java")
}

group = "com.kousenit"
version = "1.0"

tasks.register<Zip>("zip") {
    from("src/main/java")
    from("src/main/resources")
    into("dist") {
        from("README")
    }
}

tasks.register<Copy>("unzip") {
    dependsOn("zip")
    from(zipTree(tasks.named<Zip>("zip").get()
            .archiveFile.get().asFile))
    into(layout.buildDirectory.dir("expanded"))
}

tasks.register<Copy>("unzipJar") {
    dependsOn("jar")
    from(zipTree(tasks.jar.get().archiveFile))
    into(layout.buildDirectory.dir("expandedJar"))
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}