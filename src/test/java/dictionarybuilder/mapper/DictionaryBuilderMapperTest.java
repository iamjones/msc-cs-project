package dictionarybuilder.mapper;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class DictionaryBuilderMapperTest {

    private MapDriver<LongWritable, Text, Text, IntWritable> dictionaryMapper;

    private String testJson;

    @Before
    public void setUp() {
        DictionaryBuilderMapper mapper = new DictionaryBuilderMapper();
        dictionaryMapper = MapDriver.newMapDriver(mapper);

        testJson = "{"
            + "\"reviewerID\": \"AO94DHGC771SJ\","
            + "\"asin\": \"0528881469\","
            + "\"reviewerName\": \"amazdnu\","
            + "\"helpful\": [0, 0],"
            + "\"reviewText\": \"I thought the battery was good. I thought the screen was too small.\","
            + "\"overall\": 5.0,"
            + "\"summary\": \"Gotta have GPS!\","
            + "\"unixReviewTime\": 1370131200,"
            + "\"reviewTime\": \"06 2, 2013\""
        + "}";
    }

    @Test
    public void it_should_remove_stop_words_from_text_and_return_a_list_of_words()
        throws IOException{

        LongWritable inputKey = new LongWritable(1);
        Text inputValue = new Text(testJson);

        dictionaryMapper.withInput(inputKey, inputValue)
                .withOutput(new Text("battery"),  new IntWritable(1))
                .withOutput(new Text("thought"),  new IntWritable(1))
                .withOutput(new Text("screen"),  new IntWritable(1))
                .runTest();
    }

    @Test
    public void it_should_ignore_words_containing_numbers()
        throws IOException {

        LongWritable inputKey = new LongWritable(1);
        Text inputValue = new Text("{"
            + "\"reviewText\": \"123foo 456bar789 hello0world foobar\""
        + "}");

        dictionaryMapper.withInput(inputKey, inputValue)
            .withOutput(new Text("foobar"),  new IntWritable(1))
            .runTest();
    }

    @Test
    public void it_should_ignore_empty_strings()
        throws IOException {

        LongWritable inputKey = new LongWritable(1);
        Text inputValue = new Text("{"
            + "\"reviewText\": \"Screen        Keyboard\""
            + "}");

        dictionaryMapper.withInput(inputKey, inputValue)
            .withOutput(new Text("screen"),  new IntWritable(1))
            .withOutput(new Text("keyboard"),  new IntWritable(1))
            .runTest();
    }
}