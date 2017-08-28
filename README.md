# MSc Computer Science 2016 / 2017
# Mining sentiment towards aspects of products from large amounts of review data

Using MapReduce to analyse product reviews from the electronic category data from Amazon.

The aims of this project is to analyse in parallel each review extracting any subjective statements towards the entity or aspects of the entity and storing them in a data store so it can be queried for aggregated data.

## Make commands

This project uses a makefile to provide an easy interface to run tasks.

### Build and run the aspect discovery task

Run the aspect discovery task locally on a small set of real data.
This takes an optional parameter (c) at runtime. C can be equal to 10, 25, 100, 500 or 100000. It will default to 10.

```
make build-aspect-discovery-test
make build-aspect-discovery-test c=25
make build-aspect-discovery-test c=100
make build-aspect-discovery-test c=500
make build-aspect-discovery-test c=100000
```

Run the aspect discovery task locally on the full data set

```
make build-aspect-discovery
```

### Build and run the sentiment analyser

In order to run the sentiment analyser there needs to be a file containing aspect words present in the resources
directory of the project. This is in addition of the input data that will be the Amazon review data.

Run the sentiment analyser job locally on a small set of real data
This takes an optional parameter (c) at runtime. C can be equal to 10, 25, 100, 500 or 100000. It will default to 10.

```
make build-sentiment-analyser-test
make build-sentiment-analyser-test c=25
make build-sentiment-analyser-test c=100
make build-sentiment-analyser-test c=500
make build-sentiment-analyser-test c=100000
```

Run the sentiment analyser job locally on the full set of data

```
make build-sentiment-analyser
```

### Elasticsearch

There is a bash file in the root of this project called ./elasticsearch.sh.

The responsibility of this script is to download, extract and start Elasticsearch on the localhost port 9200.

### Root-mean-square error calculator

After the sentiment analysis task has been run for any number of documents the root-mean-square error script will be ready to run.

The script can be run with the following command:

```
make build-rmse-calculation
```

The script will output the results into the terminal and will look something like this.

```

Total number of documents: 
1457580

Root-mean-square Error is: 
2.263

```