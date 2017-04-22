package project.reducer;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;

public class WebContentReducerTest {

    private ReduceDriver<Text, Text, Text, DoubleWritable> reduceDriver;

    @Before
    public void setUp() {
        WebContentReducer webContentReducer = new WebContentReducer();

        reduceDriver = ReduceDriver.newReduceDriver(webContentReducer);
    }
}
