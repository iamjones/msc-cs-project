package dictionarybuilder;

import com.google.inject.Guice;
import dictionarybuilder.mapper.DictionaryMapper;
import dictionarybuilder.reducer.DictionaryReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import project.mapper.MapperModule;

import java.io.IOException;

/**
 * This is the miain configuration and runnable method for the
 * hadoop job.
 */
public class DictionaryBuilder {

    public static void main(
        String[] args
    ) throws
        IOException,
        InterruptedException,
        ClassNotFoundException {

        Configuration configuration = new Configuration();

        // Set up our dependency injection modules
        Guice.createInjector(new MapperModule());

        Job job = Job.getInstance(configuration, "dictionarybuilder");
        job.setJarByClass(DictionaryBuilder.class);
        job.setMapperClass(DictionaryMapper.class);
//        job.setCombinerClass();
        job.setReducerClass(DictionaryReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        if (args.length == 0) {
            throw new IllegalArgumentException("Arguments are needed to run a job.");
        }

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 1 : 0);
    }
}