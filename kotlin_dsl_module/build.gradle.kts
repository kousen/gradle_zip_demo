plugins {
    id("java")
}

group = "com.kousenit"
version = "1.0"

val zip by tasks.registering(Zip::class) {
    from("src/main/java")
    from("src/main/resources")
    into("dist") {
        from("README")
    }
}

tasks.register<Copy>("unzip") {
    //dependsOn("zip")
//    from(zipTree(tasks.named<Zip>("zip").get()
//            .archiveFile.get().asFile))
    // from Cedric Champeau
    from(zipTree(zip.map(Zip::getArchiveFile)))
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
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.assertj:assertj-core:3.21.0")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}