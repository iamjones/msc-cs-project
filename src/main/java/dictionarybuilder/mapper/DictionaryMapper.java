package dictionarybuilder.mapper;

import model.Review;
import utils.Punctuation;
import utils.StopWords;
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
public class DictionaryMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private StopWords stopWords;

    private Punctuation punctuation;

    public DictionaryMapper() {

    }

    @Override
    public void setup(Context context) {
        this.stopWords   = new StopWords();
        this.punctuation = new Punctuation();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Review review = objectMapper.readValue(value.getBytes(), Review.class);

            String reviewNoPunctuation = this.punctuation.removeAllPunctuation(review.getReviewText());

            List<String> words             = Arrays.asList(reviewNoPunctuation.split(" "));
            List<String> reviewNoStopWords = this.stopWords.removeStopWords(words);

            for (String word : reviewNoStopWords) {
                String wordTrim = word.trim().toLowerCase();

                // Ignore word if word is empty
                if (wordTrim.isEmpty()) {
                    continue;
                }

                // Ignore word if word is two or less characters
                if (wordTrim.length() < 3) {
                    continue;
                }

                // Ignore word if word contains numbers
                if (wordTrim.matches("\\b\\w*\\d\\w*")) {
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
