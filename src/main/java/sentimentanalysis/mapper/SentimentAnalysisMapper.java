package sentimentanalysis.mapper;

import domain.aspectwords.AspectWordsMatcher;
import domain.entity.Review;
import domain.postags.PosTags;
import domain.punctuation.Punctuation;
import domain.stopwords.StopWords;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.htrace.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class SentimentAnalysisMapper extends Mapper<LongWritable, Text, Text, MapWritable> {

    private Logger logger = Logger.getLogger("SentimentAnalysisMapper");

    final private String taggerModelSrc = "src/main/resources/tagger/english.tagger";

    private StopWords stopWords;

    private Punctuation punctuation;

    private PosTags posTags;

    private AspectWordsMatcher aspectWordsMatcher;

    private MaxentTagger tagger;

    public SentimentAnalysisMapper() {

    }

    @Override
    public void setup(Context context) {

        Configuration configuration = context.getConfiguration();
        String aspectWordFilePath   = configuration.get("aspectWordsFilePath");

        this.stopWords          = new StopWords();
        this.punctuation        = new Punctuation();
        this.posTags            = new PosTags();
        this.aspectWordsMatcher = new AspectWordsMatcher(aspectWordFilePath);
        this.tagger             = new MaxentTagger(this.taggerModelSrc);
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) {

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            Review review = objectMapper.readValue(value.getBytes(), Review.class);

            String[] sentences = review.getReviewText().split("\\.");

            Map<String, String> data = new HashMap<>();
            // Remove all punctuation and stop words from that sentence
            for (String sentence : sentences) {
                String sentenceNoPunc = this.punctuation.removeAllPunctuation(sentence);
                List<String> sentenceNoStopWords = this.stopWords.removeStopWords(Arrays.asList(sentenceNoPunc.split(" ")));

                data.put(String.join(" ", sentenceNoStopWords), sentence);
            }

            for (String s : data.keySet()) {

                // Get the aspect words that are included in the sentence
                List<String> foundAspectWords = this.aspectWordsMatcher.getMatchedAspectWords(s);

                // We don't need to process sentences that do not contain aspect words
                if (foundAspectWords.size() == 0) {
                    continue;
                }

                String sentenceTagged = this.tagger.tagTokenizedString(s);

                for (String aspectWord : foundAspectWords) {

                    String phrase = this.posTags.isAdverbInRangeOfAspectWord(sentenceTagged, aspectWord, 5);

                    if (phrase != null) {
                        MapWritable mapWritable = new MapWritable();
                        mapWritable.put(new Text("asin"), new Text(review.getAsin()));
                        mapWritable.put(new Text("sentence"), new Text(data.get(s)));
                        mapWritable.put(new Text("normalisedSentence"), new Text(s));
                        mapWritable.put(new Text("sentenceTagged"), new Text(phrase));
                        mapWritable.put(new Text("aspectWord"), new Text(aspectWord));

                        context.write(new Text(review.getAsin()), mapWritable);
                    }
                }
            }

        } catch (IOException | InterruptedException e) {
            // @TODO - log this somewhere useful
            System.out.println(e.getMessage());
        }
    }
}
