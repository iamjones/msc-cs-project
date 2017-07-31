#!/bin/bash

ESSOURCE="src/main/resources/elasticsearch/elasticsearch-5.5.1"

if [ ! -d "$ESSOURCE" ]; then
	# Download the Elasticsearch source
    wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-5.5.1.tar.gz -P src/main/resources/elasticsearch

	# Change into the resources Elasticsearch directory
	cd src/main/resources/elasticsearch

	# Verify the download
	sha1sum elasticsearch-5.5.1.tar.gz

	# Extract the files
	tar -xzf elasticsearch-5.5.1.tar.gz

	# Change into the extracted directory
	cd elasticsearch-5.5.1
else
	cd src/main/resources/elasticsearch/elasticsearch-5.5.1
fi

# Start Elasticsearch
./bin/elasticsearch -d

# Sleep for 60 seconds to allow Elasticsearch to start
sleep 60

# Remove the zipped directory as we no longer need it
# rm src/main/resources/elasticsearch/elasticsearch-5.5.1.tar.gz
