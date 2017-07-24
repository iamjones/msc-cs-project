package aspectdiscovery.mapper;

import domain.entity.Review;
import domain.postags.PosTags;
import domain.punctuation.Punctuation;
import domain.stopwords.StopWords;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.htrace.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * This mapper is used to take a document in JSON format.
 * Parse the json into an object.
 * Take the review body and remove any stop words and punctuation.
 * Split the remaining review body into words.
 */
public class AspectDiscoveryMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private StopWords stopWords;

    private Punctuation punctuation;

    private PosTags posTags;

    private MaxentTagger tagger;

    final private String taggerModelSrc = "src/main/resources/tagger/english.tagger";

    @Override
    public void setup(Context context) {
        this.stopWords   = new StopWords();
        this.punctuation = new Punctuation();
        this.posTags     = new PosTags();
        this.tagger      = new MaxentTagger(this.taggerModelSrc);
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Review review = objectMapper.readValue(value.getBytes(), Review.class);

            String reviewNoPunctuation = this.punctuation.removeAllPunctuation(review.getReviewText());

            List<String> words             = Arrays.asList(reviewNoPunctuation.split(" "));
            List<String> reviewNoStopWords = this.stopWords.removeStopWords(words);

            String sentenceTagged = this.tagger.tagTokenizedString(String.join(" ", reviewNoStopWords));

            for (String word : sentenceTagged.split(" ")) {

                String[] parts = word.split("_");

                String wordTrim = parts[0].trim().toLowerCase();

                // Ignore word if word is less than 3 characters
                if (wordTrim.length() < 3) {
                    continue;
                }

                // Ignore word if word contains numbers
                if (wordTrim.matches("\\b\\w*\\d\\w*")) {
                    continue;
                }

                // Ignore if the word is not a Noun
                if (!this.posTags.isNoun(word)) {
                    continue;
                }

                context.write(new Text(wordTrim), new IntWritable(1));
            }

        } catch (IOException | InterruptedException e) {
            // @TODO - log this to somewhere useful
            System.out.println(e.getMessage());
        }
    }
}
