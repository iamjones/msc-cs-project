package sentimentanalysis.mapper;

import domain.punctuation.Punctuation;
import domain.stopwords.StopWords;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import java.util.logging.Logger;

/**
 * SentimentAnalysisMapper which is responsible for:
 *
 * Normalising the unstructured text by:
 *  Removing punctuation
 *  Removing stopwords
 *
 *
 */
public class SentimentAnalysisMapper extends org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, Text> {

    private Logger logger = Logger.getLogger("SentimentAnalysisMapper");

    private StopWords stopWords;

    private Punctuation punctuation;

    public SentimentAnalysisMapper() {

    }

    @Override
    public void setup(Context context) {
        this.stopWords   = new StopWords();
        this.punctuation = new Punctuation();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) {


    }
}
