package dictionarybuilder.reducer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class DictionaryReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    /**
     * This reducer should combine all data with the same key into a key-value
     * pair where the key is the key and the value is the count of all the values.
     *
     * @param key Text
     * @param values Iterable<Text>
     * @param context Context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void reduce(
        Text key,
        Iterable<IntWritable> values,
        Context context
    ) throws
        IOException,
        InterruptedException {

        int totalOccurrances = 0;

        for (IntWritable v : values) {
            totalOccurrances += v.get();
        }

        context.write(key, new IntWritable(totalOccurrances));
    }
}