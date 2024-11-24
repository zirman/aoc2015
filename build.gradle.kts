plugins {
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.serialization") version "2.0.21"
}
sourceSets {
    main {
        kotlin.srcDir("src")
    }
}
dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.8")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
}
tasks {
    wrapper {
        gradleVersion = "8.11"
    }
}
