all: run-jar-mannualy


build:
	./gradlew run

jar: build
	./gradlew fatJar


run-jar: jar
	./gradlew runFatJar

run-jar-mannualy: jar
	/usr/lib/jvm/java-25-openjdk/bin/java -jar ./app/build/libs/application-catalog-0.0.1-SNAPSHOT.jar --data data/custom-data.yaml --output data/custom-Application-Catalog.html
	#/usr/lib/jvm/java-25-openjdk/bin/java -jar ./app/build/libs/application-catalog-0.0.1-SNAPSHOT.jar --data data/custom-data.yaml --stages development,test,integration,production
	#/usr/lib/jvm/java-25-openjdk/bin/java -jar ./app/build/libs/application-catalog-0.0.1-SNAPSHOT.jar --data data/custom-data.yaml --stages development,test,integration,production --view ./app/src/main/resources/view.ftl
	# /usr/lib/jvm/java-25-openjdk/bin/java -jar ./app/build/libs/application-catalog-0.0.1-SNAPSHOT.jar --data data/custom-data.yaml --stages development,test,integration,production --view data/custom-view.ftl
	#/usr/lib/jvm/java-25-openjdk/bin/java -jar ./app/build/libs/application-catalog-0.0.1-SNAPSHOT.jar --data data/custom-data.yaml --stages development,test,integration,production --view data/custom-view.ftl --output data/custom-Application-Catalog.html
	# help
	#/usr/lib/jvm/java-25-openjdk/bin/java -jar ./app/build/libs/application-catalog-0.0.1-SNAPSHOT.jar -h
	#/usr/lib/jvm/java-25-openjdk/bin/java -jar ./app/build/libs/application-catalog-0.0.1-SNAPSHOT.jar --help

run: build
	./gradlew generateCatalog

