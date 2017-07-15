package sentimentanalysis.mapper;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class SentimentAnalysisMapperTest {

    private MapDriver<LongWritable, Text, Text, Text> sentimentAnalysisMapper;

    private String testJson;

    @Before
    public void setUp() {
        SentimentAnalysisMapper mapper = new SentimentAnalysisMapper();

        sentimentAnalysisMapper = MapDriver.newMapDriver(mapper);

        Mapper.Context context = sentimentAnalysisMapper.getContext();
        Configuration configuration = context.getConfiguration();

        configuration.set("aspectWordsFilePath", "src/test/resources/aspectwords/aspect-words.json");

        testJson = "{"
            + "\"reviewerID\": \"AO94DHGC771SJ\","
            + "\"asin\": \"0528881469\","
            + "\"reviewerName\": \"amazdnu\","
            + "\"helpful\": [0, 0],"
            + "\"reviewText\": \""
                + "I thought the battery was good."
                + "I thought the screen was too small."
                + "I liked the size of the phone.\","
            + "\"overall\": 5.0,"
            + "\"summary\": \"Gotta have GPS!\","
            + "\"unixReviewTime\": 1370131200,"
            + "\"reviewTime\": \"06 2, 2013\""
            + "}";
    }

    @Test
    public void it_should_extract_all_phrases_containing_aspect_words_and_sentiment_words()
        throws IOException {

        LongWritable inputKey = new LongWritable(1);
        Text inputValue = new Text(testJson);

        sentimentAnalysisMapper.withInput(inputKey, inputValue)
            .withOutput(new Text("battery"),  new Text("I thought the battery was good"))
            .withOutput(new Text("screen"),  new Text("I thought the screen was too small."))
            .runTest();
    }
}