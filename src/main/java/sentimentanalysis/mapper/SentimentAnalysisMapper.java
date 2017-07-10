package sentimentanalysis.mapper;

import com.google.inject.Inject;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.util.logging.Logger;

/**
 * SentimentAnalysisMapper to extract text and map url to sentiment score.
 */
public class SentimentAnalysisMapper extends org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, Text> {

    private Logger logger = Logger.getLogger("SentimentAnalysisMapper"); // @TODO use class not string

    @Inject
    public SentimentAnalysisMapper() {

    }

    protected void map(LongWritable key, Text value, Context context) {


    }
}
