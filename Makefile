all: build run


build:
	./gradlew run

run: build
	./gradlew generateCatalog

