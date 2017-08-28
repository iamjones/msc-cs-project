package aspectdiscovery.reducer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class AspectDiscoveryReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

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

        Configuration conf = context.getConfiguration();
        String numberOfDocuments = conf.get("numberOfDocuments");

        Integer totalOccurrences   = 0;
        Double occurrenceThreshold = 0.0;

        if (numberOfDocuments != null) {
            occurrenceThreshold = Math.ceil(Integer.parseInt(numberOfDocuments) / 100);
        }

        for (IntWritable v : values) {
            totalOccurrences += v.get();
        }

        if (totalOccurrences > occurrenceThreshold) {
            context.write(key, new IntWritable(totalOccurrences));
        }
    }
}