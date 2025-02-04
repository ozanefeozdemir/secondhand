plugins {
	java
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("jvm") version "1.9.0"  // Kotlin sürümü eklemeyi unutmayın
}

group = "com.project.secondhand"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	// implementation("org.springframework.boot:spring-boot-starter-security") // Eğer gerekliyse aktif edin
	implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.0")
	implementation("org.springframework.boot:spring-boot-starter-web")
	runtimeOnly("com.mysql:mysql-connector-j")

	// Test bağımlılıkları
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation(kotlin("stdlib-jdk8"))

	// Mockito ve JUnit bağımlılıkları
	testImplementation("org.mockito:mockito-core:4.8.0")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.0")
	testImplementation("org.mockito:mockito-junit-jupiter:4.8.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
