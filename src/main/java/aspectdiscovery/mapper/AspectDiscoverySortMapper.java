package aspectdiscovery.mapper;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Sorts the aspect words by the number of occurrences in the corpus.
 */
public class AspectDiscoverySortMapper extends Mapper<LongWritable, Text, IntWritable, Text> {

    /**
     * Maps each word and number of occurrence value to the context.
     * The number of occurrences will be the key so in the merge sort phase
     * of Hadoop the words will be ordered by the number of occurrences.
     *
     * @param key LongWritable
     * @param value Text
     * @param context Context
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) {

        try {
            String[] parts = value.toString().split("\\s+");

            context.write(new IntWritable(Integer.parseInt(parts[1].trim())), new Text(parts[0].trim()));

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
