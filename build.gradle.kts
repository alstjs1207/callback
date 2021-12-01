import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.0"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.0"
	kotlin("plugin.spring") version "1.5.0"
}

group = "com.day1"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	//jackson
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.projectlombok:lombok")
	//Logging
	implementation("io.github.microutils:kotlin-logging:1.12.5")
	//kotlinx
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")
	//jpa
	//implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	//test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test")
	//testImplementation("com.h2database:h2")
	//mockk
	testImplementation("com.ninja-squad:springmockk:3.0.1") {
		exclude(module = "mockito-core")
	}
	// embedded-redis
	implementation("it.ozimov:embedded-redis:0.7.2")

	implementation("org.springframework.cloud:spring-cloud-gcp-starter-pubsub:1.2.5.RELEASE")
	implementation("org.springframework.integration:spring-integration-core")

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
