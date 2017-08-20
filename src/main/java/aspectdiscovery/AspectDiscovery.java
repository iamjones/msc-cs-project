package aspectdiscovery;

import aspectdiscovery.mapper.AspectDiscoveryMapper;
import aspectdiscovery.mapper.AspectDiscoverySortMapper;
import aspectdiscovery.mapper.AspectDiscoverySortWritableComparitor;
import aspectdiscovery.reducer.AspectDiscoveryReducer;
import aspectdiscovery.reducer.AspectDiscoverySortReducer;
import domain.validation.TaskParameterValidator;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

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

        Job job = Job.getInstance(configuration, "aspectDiscovery");
        job.setJarByClass(AspectDiscovery.class);
        job.setMapperClass(AspectDiscoveryMapper.class);
        job.setReducerClass(AspectDiscoveryReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        if (job.waitForCompletion(true)) {

            Configuration configuration2 = new Configuration();
            Job job2 = Job.getInstance(configuration2, "aspectDiscoverySort");

            job2.setJarByClass(AspectDiscovery.class);
            job2.setMapperClass(AspectDiscoverySortMapper.class);
            job2.setReducerClass(AspectDiscoverySortReducer.class);
            job2.setOutputKeyClass(IntWritable.class);
            job2.setOutputValueClass(Text.class);
            job2.setSortComparatorClass(AspectDiscoverySortWritableComparitor.class);

            FileInputFormat.addInputPath(job2, new Path(args[1]));
            FileOutputFormat.setOutputPath(job2, new Path(args[1] + "sorted"));

            System.exit(job2.waitForCompletion(true) ? 1 : 0);
        }

    }
}