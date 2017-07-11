package sentimentanalysis.mapper;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Before;

public class SentimentAnalysisMapperTest {

    private MapDriver<LongWritable, Text, Text, Text> webContentMapper;

    @Before
    public void setUp() {
        SentimentAnalysisMapper sentimentAnalysisMapper = new SentimentAnalysisMapper();

        webContentMapper = MapDriver.newMapDriver(sentimentAnalysisMapper);
    }
}