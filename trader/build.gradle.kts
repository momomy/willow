plugins {
	war
	id("org.springframework.boot") version "3.3.2"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
}

group = "org.momony"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

extra["springShellVersion"] = "3.3.1"

val auth0Version = "1.44.2"
val auth0JavaJwtVersion = "4.2.2"
val springDocOpenapiWebmvcUiVersion = "2.2.0"

dependencies {
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.springframework.boot:spring-boot-starter-quartz")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocOpenapiWebmvcUiVersion")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.shell:spring-shell-starter")
	implementation("com.auth0:auth0:$auth0Version")
	implementation("com.auth0:java-jwt:$auth0JavaJwtVersion")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.shell:spring-shell-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")


}

dependencyManagement {
	imports {
		mavenBom("org.springframework.shell:spring-shell-dependencies:${property("springShellVersion")}")
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
