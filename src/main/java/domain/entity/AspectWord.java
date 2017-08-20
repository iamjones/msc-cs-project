package domain.entity;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 *
 */
public class AspectWord implements WritableComparable<AspectWord> {

    private Text word;

    private IntWritable count;

    public AspectWord() {
        this.word = new Text();
        this.count = new IntWritable();
    }

    public AspectWord(
        Text word,
        IntWritable count
    ) {
        this.word  = word;
        this.count = count;
    }

    public Text getWord() {
        return word;
    }

    public void setWord(Text word) {
        this.word = word;
    }

    public IntWritable getCount() {
        return count;
    }

    public void setCount(IntWritable count) {
        this.count = count;
    }

    @Override
    public int compareTo(AspectWord aspectWord) {
        if (aspectWord.getCount().get() < this.count.get()) {
            return 1;
        }

        return -1;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        this.word.write(dataOutput);
        this.count.write(dataOutput);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.word.readFields(dataInput);
        this.count.readFields(dataInput);
    }

    @Override
    public String toString() {
        return "[" + this.getWord() + ": " + this.getCount() + "]";
    }
}
