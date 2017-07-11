# msc-cs-project
Using MapReduce to analyse product reviews from the electronic category data from Amazon.

The aims of this project is to concurrency analyse each review extracting any subjective statements towards the entity or aspects of the entity and storing them in a data store so it can be queried for aggregated data.

## Make commands

This project uses a makefile to provide an easy interface to run tasks.

### Build and run the dictionary builder

Run the dictionary builder job locally on a small set of real data
This takes an optional parameter (c) at runtime. C can be equal to 10, 25 or 100. It will default to 10.

```
make build-dictionary-test
make build-dictionary-test c=25
make build-dictionary-test c=100
```

Run the dictionary builder job locally on the full data set

```
make build-dictionary
```

### Build and run the sentiment analyser

In order to run the sentiment analyser there needs to be a file containing aspect words present in the resources
directory of the project. This is in addition of the input data that will be the Amazon review data.

Run the sentiment analyser job locally on a small set of real data
This takes an optional parameter (c) at runtime. C can be equal to 10, 25 or 100. It will default to 10.

```
make build-sentiment-analyser-test
make build-sentiment-analyser-test c=25
make build-sentiment-analyser-test c=100
```

Run the sentiment analyser job locally on the full set of data

```
make build-sentiment-analyser
```