package aspectdiscovery;

import com.google.inject.Guice;
import aspectdiscovery.mapper.AspectDiscoveryMapper;
import aspectdiscovery.reducer.AspectDiscoveryReducer;
import domain.validation.TaskParameterValidator;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import sentimentanalysis.mapper.SentimentAnalysisMapperModule;

import java.io.IOException;

/**
 * This is the miain configuration and runnable method for the
 * hadoop job.
 */
public class AspectDiscovery {

    public static void main(
        String[] args
    ) throws
        IOException,
        InterruptedException,
        ClassNotFoundException {

        TaskParameterValidator taskParameterValidator = new TaskParameterValidator();

        // Check if an input path has been supplied
        taskParameterValidator.checkInputPath(args);

        // Check if an output path has been supplied
        taskParameterValidator.checkOutputPath(args);

        Configuration configuration = new Configuration();

        // Set up our dependency injection modules
        Guice.createInjector(new SentimentAnalysisMapperModule());

        Job job = Job.getInstance(configuration, "dictionarybuilder");
        job.setJarByClass(AspectDiscovery.class);
        job.setMapperClass(AspectDiscoveryMapper.class);
//        job.setCombinerClass();
        job.setReducerClass(AspectDiscoveryReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 1 : 0);
    }
}