package domain.entity;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * A custom hadoop writable object to
 */
public class ReviewWritable implements Writable {

    private Text asin;

    private Text reviewText;

    private Text sentence;

    private Text cleanSentence;

    private Text aspectWord;

    /**
     * Default constructor which instantiates each object as blank objects.
     */
    public ReviewWritable() {
        this.asin          = new Text();
        this.reviewText    = new Text();
        this.sentence      = new Text();
        this.cleanSentence = new Text();
        this.aspectWord    = new Text();
    }

    /**
     * Constructor to set all the properties with values.
     *
     * @param asin Text
     * @param reviewText Text
     * @param sentence Text
     * @param cleanSentence Text
     */
    public ReviewWritable(
        Text asin,
        Text reviewText,
        Text sentence,
        Text cleanSentence,
        Text aspectWord
    ) {
        this.asin          = asin;
        this.reviewText    = reviewText;
        this.sentence      = sentence;
        this.cleanSentence = cleanSentence;
        this.aspectWord    = aspectWord;
    }

    /**
     * @param dataOutput DataOutput
     * @throws IOException issue with output
     */
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        this.asin.write(dataOutput);
        this.reviewText.write(dataOutput);
        this.sentence.write(dataOutput);
        this.cleanSentence.write(dataOutput);
        this.aspectWord.write(dataOutput);
    }

    /**
     * @param dataInput DataInput
     * @throws IOException issue with input
     */
    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.asin.readFields(dataInput);
        this.reviewText.readFields(dataInput);
        this.sentence.readFields(dataInput);
        this.cleanSentence.readFields(dataInput);
        this.aspectWord.readFields(dataInput);
    }

    public Text getAsin() {
        return asin;
    }

    public void setAsin(Text asin) {
        this.asin = asin;
    }

    public Text getReviewText() {
        return reviewText;
    }

    public void setReviewText(Text reviewText) {
        this.reviewText = reviewText;
    }

    public Text getSentence() {
        return sentence;
    }

    public void setSentence(Text sentence) {
        this.sentence = sentence;
    }

    public Text getCleanSentence() {
        return cleanSentence;
    }

    public void setCleanSentence(Text cleanSentence) {
        this.cleanSentence = cleanSentence;
    }

    public Text getAspectWord() {
        return aspectWord;
    }

    public void setAspectWord(Text aspectWord) {
        this.aspectWord = aspectWord;
    }

    public String toString() {
        return "Asin: " + this.getAsin() + ", \n"
            + "Review Text: " + this.getReviewText() + ", \n"
            + "Sentence: " + this.getSentence() + ", \n"
            + "Clean Sentence: " + this.getCleanSentence() + ", \n"
            + "Aspect Word: " + this.getAspectWord() + "\n";
    }
}
