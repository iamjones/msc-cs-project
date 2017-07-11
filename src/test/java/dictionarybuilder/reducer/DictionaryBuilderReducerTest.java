package dictionarybuilder.reducer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DictionaryBuilderReducerTest {

    private ReduceDriver<Text, IntWritable, Text, IntWritable> reduceDriver;

    @Before
    public void setup() {

        Reducer dictionaryReducer = new DictionaryBuilderReducer();
        reduceDriver = ReduceDriver.newReduceDriver(dictionaryReducer);
    }

    @Test
    public void it_should_reduce_a_list_of_words() throws IOException {

        Text inputKey = new Text("test");
        List<IntWritable> inputValues = Arrays.asList(
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1),
            new IntWritable(1)
        );

        IntWritable outputValue = new IntWritable(30);

        reduceDriver.withInput(inputKey, inputValues)
                    .withOutput(inputKey, outputValue)
                    .runTest();
    }

    @Test
    public void it_should_reduce_a_list_of_words_and_be_able_to_handle_zero() throws IOException {

        Text inputKey = new Text("test with zero");
        List<IntWritable> inputValues = Arrays.asList(new IntWritable(5), new IntWritable(0));

        IntWritable outputValue = new IntWritable(5);

        reduceDriver.withInput(inputKey, inputValues)
                    .withOutput(inputKey, outputValue)
                    .runTest();
    }
}
