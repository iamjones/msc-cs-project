package sentimentanalysis.mapper;

import domain.aspectwords.AspectWordsMatcher;
import domain.entity.Review;
import domain.entity.ReviewWritable;
import domain.postags.PosTags;
import domain.punctuation.Punctuation;
import domain.stopwords.StopWords;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
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
public class SentimentAnalysisMapper extends org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, ReviewWritable> {

    private Logger logger = Logger.getLogger("SentimentAnalysisMapper");

    final private String taggerModelSrc = "src/main/resources/tagger/english.tagger";

    private StopWords stopWords;

    private Punctuation punctuation;

    private PosTags posTags;

    private AspectWordsMatcher aspectWordsMatcher;

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

            MaxentTagger tagger = new MaxentTagger(this.taggerModelSrc);

            for (String s : data.keySet()) {

                // Get the aspect words that are included in the sentence
                List<String> foundAspectWords = this.aspectWordsMatcher.getMatchedAspectWords(s);

                // We don't need to process sentences that do not contain aspect words
                if (foundAspectWords.size() == 0) {
                    continue;
                }

                String sentenceTagged = tagger.tagTokenizedString(s);

                for (String aspectWord : foundAspectWords) {
                    if (this.posTags.isAdverbInRangeOfAspectWord(sentenceTagged, aspectWord, 5)) {
                        ReviewWritable reviewWritable = new ReviewWritable(
                            new Text(review.getAsin()),
                            new Text(review.getReviewText()),
                            new Text(data.get(s)),
                            new Text(s),
                            new Text(aspectWord)
                        );

                        context.write(new Text(review.getAsin()), reviewWritable);
                    }
                }
            }

        } catch (IOException | InterruptedException e) {
            // @TODO - log this somewhere useful
            System.out.println(e.getMessage());
        }
    }
}
