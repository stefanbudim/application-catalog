all: build run


build:
	./gradlew run

jar: build
	./gradlew fatJar


run-jar: jar
	./gradlew runFatJar

run-jar-mannualy: jar
	/usr/lib/jvm/java-25-openjdk/bin/java -jar ./app/build/libs/application-catalog-1.0.0.jar

run: build
	./gradlew generateCatalog

