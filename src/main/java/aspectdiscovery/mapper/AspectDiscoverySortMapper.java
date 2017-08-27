package aspectdiscovery.mapper;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 *
 */
public class AspectDiscoverySortMapper extends Mapper<LongWritable, Text, IntWritable, Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context) {

        try {
            String[] parts = value.toString().split("\\s+");

            context.write(new IntWritable(Integer.parseInt(parts[1].trim())), new Text(parts[0].trim()));

        } catch (IOException | InterruptedException e) {
            // @TODO - log this to somewhere useful
            System.out.println(e.getMessage());
        }
    }
}


