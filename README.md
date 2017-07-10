# msc-cs-project
Using MapReduce to analyse product reviews from the electronic category data from Amazon.

The aims of this project is to concurrency analyse each review extracting any subjective statements towards the entity or aspects of the entity and storing them in a data store so it can be queried for aggregated data.

## Make commands

This project uses a makefile to provide an easy interface to run tasks.

### Build and run the dictionary builder

Run the dictionary builder job locally on a small set of real data ()
```
make build-dictionary-test
```

Run the dictionary builder job locally on the full data set
```
make build-dictionary
```
