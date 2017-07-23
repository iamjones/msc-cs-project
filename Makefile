# Input directory for running locally
inputDir = src/main/resources/input/

# Output directory for running locally
outputDir = src/main/resources/

# Aspect words file for running locally
aspectWordsFile = src/main/resources/aspectwords/aspectwords-test.json

# Set a default value for the test job
c = 10

# Builds a jar of the project
build:
	mvn package

# Run the aspect discovery job locally on a small set of real data
# For example
# - make build-aspect-discovery-test c=10
# - make build-aspect-discovery-test c=25
# - make build-aspect-discovery-test c=100
build-aspect-discovery-test: build
	hadoop jar target/msc-cs-project.jar aspectdiscovery.AspectDiscovery $(inputDir)$(c)_reviews.json $(outputDir)aspects-$$RANDOM

# Run the aspect discovery job locally on the full data set
build-aspect-discovery: build
	hadoop jar target/msc-cs-project.jar aspectdiscovery.AspectDiscovery $(inputDir)5000_reviews.json $(outputDir)aspects-$$RANDOM

# Run the sentiment analyser job locally on a small set of real data
# For example
# - make build-sentiment-analyser-test c=10
# - make build-sentiment-analyser-test c=25
# - make build-sentiment-analyser-test c=100
build-sentiment-analyser-test: build
	hadoop jar target/msc-cs-project.jar sentimentanalysis.SentimentAnalysis $(inputDir)$(c)_reviews.json $(outputDir)sentiment-$$RANDOM $(aspectWordsFile)

# Run the dictionary builder job locally on the full data set
build-sentiment-analyser: build
	hadoop jar target/msc-cs-project.jar sentimentanalysis.SentimentAnalysis $(inputDir)5000_reviews.json $(outputDir)sentiment-$$RANDOM $(aspectWordsFile)