package aspectdiscovery.mapper;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class AspectDiscoverySortMapperTest {

    private MapDriver<LongWritable, Text, IntWritable, Text> aspectDiscoverySortMapper;

    private String testLine;

    @Before
    public void setUp() {
        AspectDiscoverySortMapper mapper = new AspectDiscoverySortMapper();
        aspectDiscoverySortMapper = MapDriver.newMapDriver(mapper);

        testLine = "word      2";
    }

    @Test
    public void it_should_remove_stop_words_from_text_and_return_a_list_of_words()
        throws IOException{

        LongWritable inputKey = new LongWritable(1);
        Text inputValue = new Text(testLine);

        aspectDiscoverySortMapper.withInput(inputKey, inputValue)
            .withOutput(new IntWritable(2), new Text("word"))
            .runTest();
    }
}