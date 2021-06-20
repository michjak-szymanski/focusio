import com.google.protobuf.gradle.*

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("com.google.protobuf") version "0.8.16"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(16))
    }
}

protobuf {
    protoc {
        protoc {
            artifact = "com.google.protobuf:protoc:3.12.0"
        }
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.15.1"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc")
            }
        }
    }
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "focusio.FocusioCli"
    }
}

sourceSets {
    main {
        java {
            srcDirs("build/generated/source/proto/main/grpc")
            srcDirs("build/generated/source/proto/main/java")
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.vavr:vavr:0.10.3")
    implementation("info.picocli:picocli:4.6.1")

    //lombok
    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")
    testCompileOnly("org.projectlombok:lombok:1.18.20")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.20")

    //gRPC
    compileOnly("com.google.protobuf:protobuf-java:3.6.1")
    implementation("io.grpc:grpc-netty-shaded:1.38.0")
    implementation("io.grpc:grpc-protobuf:1.38.0")
    implementation("io.grpc:grpc-stub:1.38.0")
    compileOnly("org.apache.tomcat:annotations-api:6.0.53")
}