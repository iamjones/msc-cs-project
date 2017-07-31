package sentimentanalysis.mapper;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SentimentAnalysisMapperTest {

    private MapDriver<LongWritable, Text, Text, MapWritable> sentimentAnalysisMapper;

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
            + "\"reviewText\": \"I bought the phone but I must state that I have never had "
            + "an electrical device with a battery that performs so poorly.\","
            + "\"overall\": 5.0,"
            + "\"summary\": \"Gotta have GPS!\","
            + "\"unixReviewTime\": 1370131200,"
            + "\"reviewTime\": \"06 2, 2013\""
            + "}";
    }

    @Test
    public void it_should_extract_a_single_sentence_containing_one_aspect_word_and_sentiment_word()
        throws IOException {

        LongWritable inputKey = new LongWritable(1);
        Text inputValue = new Text(testJson);

        List<Pair<Text, MapWritable>> output = sentimentAnalysisMapper.withInput(inputKey, inputValue).run();

        assertThat(output.get(0).getFirst(), is(new Text("0528881469")));

        MapWritable rsp1 = output.get(0).getSecond();

        assertThat(rsp1.get(new Text("asin")), is(new Text("0528881469")));
//        assertThat(rsp1.getCleanSentence(), is(new Text("bought_VBN phone_NN must_MD state_VB never_RB electrical_JJ device_NN battery_NN performs_VBZ poorly_RB")));
//        assertThat(rsp1.getSentence(), is(new Text("I bought the phone but I must state that I have never had "
//            + "an electrical device with a battery that performs so poorly")));
//        assertThat(rsp1.getAspectWord(), is(new Text("battery")));
    }

    @Test
    public void it_should_extract_all_phrases_containing_aspect_words_and_sentiment_words_with_large_review()
        throws IOException {

//        String testJson = "{"
//            + "\"reviewerID\": \"AO94DHGC771SJ\","
//            + "\"asin\": \"0528881469\","
//            + "\"reviewerName\": \"amazdnu\","
//            + "\"helpful\": [0, 0],"
//            + "\"reviewText\": \"I bought the phone but I must state that I have never had "
//            + "an electrical device with a battery that performs so poorly."
//            + "I really feel that the display and screen were perfectly implemented.\","
//            + "\"overall\": 5.0,"
//            + "\"summary\": \"Gotta have GPS!\","
//            + "\"unixReviewTime\": 1370131200,"
//            + "\"reviewTime\": \"06 2, 2013\""
//            + "}";
//
//        LongWritable inputKey = new LongWritable(1);
//        Text inputValue = new Text(testJson);
//
//        List<Pair<Text, ReviewWritable>> output = sentimentAnalysisMapper.withInput(inputKey, inputValue).run();
//
//        assertThat(output.get(0).getFirst(), is(new Text("0528881469")));
//        assertThat(output.get(1).getFirst(), is(new Text("0528881469")));
//        assertThat(output.get(2).getFirst(), is(new Text("0528881469")));
//
//        // Battery
//        ReviewWritable rsp1 = output.get(0).getSecond();
//
//        assertThat(rsp1.getAsin(), is(new Text("0528881469")));
//        assertThat(rsp1.getReviewText(), is(new Text("I bought the phone but I must state that I have never had "
//            + "an electrical device with a battery that performs so poorly."
//            + "I really feel that the display and screen were perfectly implemented.")));
//        assertThat(rsp1.getCleanSentence(), is(new Text("bought_VBN phone_NN must_MD state_VB never_RB electrical_JJ device_NN battery_NN performs_VBZ poorly_RB")));
//        assertThat(rsp1.getSentence(), is(new Text("I bought the phone but I must state that I have never had "
//            + "an electrical device with a battery that performs so poorly")));
//        assertThat(rsp1.getAspectWord(), is(new Text("battery")));
//
//        // Display
//        ReviewWritable rsp2 = output.get(1).getSecond();
//
//        assertThat(rsp2.getAsin(), is(new Text("0528881469")));
//        assertThat(rsp2.getReviewText(), is(new Text("I bought the phone but I must state that I have never had "
//            + "an electrical device with a battery that performs so poorly."
//            + "I really feel that the display and screen were perfectly implemented.")));
//        assertThat(rsp2.getCleanSentence(), is(new Text("really_RB feel_VB display_NN screen_NN perfectly_RB implemented_VBD")));
//        assertThat(rsp2.getSentence(), is(new Text("I really feel that the display and screen were perfectly implemented")));
//        assertThat(rsp2.getAspectWord(), is(new Text("display")));
//
//        // Screen
//        ReviewWritable rsp3 = output.get(2).getSecond();
//
//        assertThat(rsp3.getAsin(), is(new Text("0528881469")));
//        assertThat(rsp3.getReviewText(), is(new Text("I bought the phone but I must state that I have never had "
//            + "an electrical device with a battery that performs so poorly."
//            + "I really feel that the display and screen were perfectly implemented.")));
//        assertThat(rsp3.getCleanSentence(), is(new Text("really_RB feel_VB display_NN screen_NN perfectly_RB implemented_VBD")));
//        assertThat(rsp3.getSentence(), is(new Text("I really feel that the display and screen were perfectly implemented")));
//        assertThat(rsp3.getAspectWord(), is(new Text("screen")));
    }
}