package sentimentanalysis.mapper;

import domain.aspectwords.AspectWordsParser;
import domain.entity.AspectWords;
import domain.entity.Review;
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
public class SentimentAnalysisMapper extends org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, Text> {

    private Logger logger = Logger.getLogger("SentimentAnalysisMapper");

    final private String taggerModelSrc = "src/main/resources/tagger/english.tagger";

    private StopWords stopWords;

    private Punctuation punctuation;

    private PosTags posTags;

    public SentimentAnalysisMapper() {

    }

    @Override
    public void setup(Context context) {
        this.stopWords   = new StopWords();
        this.punctuation = new Punctuation();
        this.posTags     = new PosTags();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) {

        try {
            Configuration configuration = context.getConfiguration();
            String aspectWordFilePath   = configuration.get("aspectWordsFilePath");

            ObjectMapper objectMapper = new ObjectMapper();
            Review review = objectMapper.readValue(value.getBytes(), Review.class);

            // Parse the file containing the aspect words into an AspectWords entity
            AspectWordsParser aspectWordsParser = new AspectWordsParser();
            AspectWords aspectWords = aspectWordsParser.parse(aspectWordFilePath);

            String[] sentences = review.getReviewText().split("\\.");

            Map<String, String> data = new HashMap<>();
            // Remove all punctuation and stop words from that sentence
            for (String sentence : sentences) {
                String sentenceNoPunc = this.punctuation.removeAllPunctuation(sentence);
                List<String> sentenceNoStopWords = this.stopWords.removeStopWords(Arrays.asList(sentenceNoPunc.split(" ")));

                data.put(sentence, String.join(" ", sentenceNoStopWords));
            }

            MaxentTagger tagger = new MaxentTagger(this.taggerModelSrc);

            // Tag each word in each sentence.
            // If there is not an adjective or advert+adjective pair then discard the sentence
            // Otherwise keep it for further processing
            for (String s : data.values()) {

                // We don't care about sentences that do not contain aspect words


                String sentenceTagged = tagger.tagTokenizedString(s);

                String[] taggedWords = sentenceTagged.split(" ");

                for (String taggedWord : taggedWords) {
                    if (this.posTags.isAdverb(taggedWord) ||
                        this.posTags.isVerb(taggedWord)) {
                        context.write(new Text(s), new Text(s));
                    }
                }
            }

        } catch (IOException | InterruptedException e) {
            // @TODO - log this somewhere useful
            System.out.println(e.getMessage());
        }
    }
}
