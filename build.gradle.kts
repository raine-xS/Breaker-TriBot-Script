repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":libraries:my-library"))

    // Include the script's framework
    implementation(project(":libraries:framework"))

    // Include JavaFX modules with the appropriate classifier for your OS
    val javafxVersion = "17.0.12"
    implementation("org.openjfx:javafx-base:$javafxVersion")
    implementation("org.openjfx:javafx-controls:$javafxVersion")
    implementation("org.openjfx:javafx-fxml:$javafxVersion")
    implementation("org.openjfx:javafx-graphics:$javafxVersion")
}

