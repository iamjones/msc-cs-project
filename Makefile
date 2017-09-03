# Input directory for running locally
inputDir = src/main/resources/input/

# Output directory for running locally
outputDir = src/main/resources/

# Aspect words file for running locally
aspectWordsFile = src/main/resources/aspectwords/aspectwords-test.json

# 2500 of the most frequent aspect words for the whole data set
aspectWordsAll = src/main/resources/aspectwords/aspectwords-all.json

# Set a default value for the test job
c = 10

# Builds a jar of the project
.PHONY: build
build:
	mvn clean package

# Run the aspect discovery job locally on a small set of real data
# For example
# - make build-aspect-discovery-test c=10
# - make build-aspect-discovery-test c=25
# - make build-aspect-discovery-test c=100
# - make build-aspect-discovery-test c=500
# - make build-aspect-discovery-test c=100000
.PHONY: build-aspect-discovery-test
build-aspect-discovery-test: build
	hadoop jar target/msc-cs-project.jar aspectdiscovery.AspectDiscovery $(inputDir)$(c)_reviews.json $(outputDir)aspects-$$RANDOM $(c)

# Run the aspect discovery job locally on the full data set
.PHONY: build-aspect-discovery
build-aspect-discovery: build
	hadoop jar target/msc-cs-project.jar aspectdiscovery.AspectDiscovery $(inputDir)all_reviews.json $(outputDir)aspects-$$RANDOM 1689188

# Run the sentiment analyser job locally on a small set of real data
# For example
# - make build-sentiment-analyser-test c=10
# - make build-sentiment-analyser-test c=25
# - make build-sentiment-analyser-test c=100
# - make build-sentiment-analyser-test c=500
# - make build-sentiment-analyser-test c=100000
.PHONY: build-sentiment-analyser-test
build-sentiment-analyser-test: build
	./elasticsearch.sh
	hadoop jar target/msc-cs-project.jar sentimentanalysis.SentimentAnalysis $(inputDir)$(c)_reviews.json $(aspectWordsFile)

# Run the dictionary builder job locally on the full data set
.PHONY: build-sentiment-analyser
build-sentiment-analyser: build
	./elasticsearch.sh
	hadoop jar target/msc-cs-project.jar sentimentanalysis.SentimentAnalysis $(inputDir)all_reviews.json $(aspectWordsAll)

.PHONY: build-rmse-calculation
build-rmse-calculation: build
	mvn exec:java