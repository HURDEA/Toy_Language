plugins {
    application
    java
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.junit.jupiter)
    testImplementation("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass = "interpreter.Main"
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
    workingDir = rootDir
    // keyboard input for the text menu
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
