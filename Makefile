# Input directory for running locally
inputDir = src/main/resources/dictionarybuilder/input/

# Output directory for running locally
outputDir = src/main/resources/dictionarybuilder/output-$$RANDOM/

# Builds a jar of the project
build:
	mvn package

# Run the dictionary builder job locally on a small set of real data
build-dictionary-test: build
	hadoop jar target/msc-cs-project-1.0-SNAPSHOT.jar dictionarybuilder.DictionaryBuilder $(inputDir)reviews_for_test.json $(outputDir)

# Run the dictionary builder job locally on the full data set
build-dictionary: build
	hadoop jar target/msc-cs-project-1.0-SNAPSHOT.jar dictionarybuilder.DictionaryBuilder $(inputDir)all_reviews.json $(outputDir)