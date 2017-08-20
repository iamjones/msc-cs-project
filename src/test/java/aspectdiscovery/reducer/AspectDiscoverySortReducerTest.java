package aspectdiscovery.reducer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AspectDiscoverySortReducerTest {

    private ReduceDriver<IntWritable, Text, Text, IntWritable> reduceDriver;

    @Before
    public void setup() {

        Reducer aspectDiscoverySortReducer = new AspectDiscoverySortReducer();
        reduceDriver = ReduceDriver.newReduceDriver(aspectDiscoverySortReducer);
    }

    @Test
    public void it_should_reduce_a_list_of_words() throws IOException {

        IntWritable inputKey = new IntWritable(100);
        List<Text> inputValues = Arrays.asList(
            new Text("test")
        );

        IntWritable outputValue = new IntWritable(100);

        reduceDriver.withInput(inputKey, inputValues)
            .withOutput(new Text("test"), outputValue)
            .runTest();
    }
}
