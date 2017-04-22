package project;

import com.google.inject.Guice;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import project.mapper.MapperModule;
import project.mapper.WebContentMapper;
import project.reducer.WebContentReducer;

import java.io.IOException;

/**
 * This class handles:
 *  - Running the hadoop tasks
 */
public class Project {

    public static void main(String[] args) throws
            IOException,
            InterruptedException,
            ClassNotFoundException {

        Configuration configuration = new Configuration();

        // Set up our dependency injection modules
        Guice.createInjector(new MapperModule());

        Job job = Job.getInstance(configuration, "project");
        job.setJarByClass(Project.class);
        job.setMapperClass(WebContentMapper.class);
        job.setCombinerClass(WebContentReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 1 : 0);
    }
}
