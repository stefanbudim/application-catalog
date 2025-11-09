# Project setup

# structure
app-catalog/
├── build.gradle
├── settings.gradle
├── src/
│   └── main/
│       ├── java/
│       │   └── org/example/AppCatalogGenerator.java
│       └── resources/
│           ├── view.ftl
│           └── data.yaml



## try 1
gradle init --type java-application  --dsl groovy
  erg: Gradle requires JVM 17 or later to run. Your build is currently configured to use JVM 8.

gradle.properties
org.gradle.java.home=/usr/lib/jvm/java-25-openjdk

## try 2
gradle init --type java-application  --dsl groovy
 erg: gradle.properties overwritten

### gradle
Starting a Gradle Daemon, 1 incompatible Daemon could not be reused, use --status for details
Gradle requires JVM 17 or later to run. Your build is currently configured to use JVM 8.

### add again
gradle.properties
org.gradle.java.home=/usr/lib/jvm/java-25-openjdk

## show java version
gradle -v

------------------------------------------------------------
Gradle 9.1.0
------------------------------------------------------------

Build time:    2025-09-18 21:55:32 UTC
Revision:      <unknown>

Kotlin:        2.2.0
Groovy:        4.0.28
Ant:           Apache Ant(TM) version 1.10.15 compiled on August 25 2024
Launcher JVM:  1.8.0_462 (Arch Linux 25.462-b08)
Daemon JVM:    /usr/lib/jvm/java-25-openjdk (from org.gradle.java.home)
OS:            Linux 5.10.244-1-MANJARO amd64


