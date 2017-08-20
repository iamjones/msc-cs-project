package sentimentanalysis;

import domain.validation.TaskParameterValidator;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.elasticsearch.hadoop.mr.EsOutputFormat;
import sentimentanalysis.mapper.SentimentAnalysisMapper;
import sentimentanalysis.reducer.SentimentAnalysisReducer;

import java.io.IOException;

/**
 * This class handles:
 *  - Running the sentiment analysis hadoop task
 */
public class SentimentAnalysis {

    public static void main(String[] args) throws
            IOException,
            InterruptedException,
            ClassNotFoundException {

        TaskParameterValidator taskParameterValidator = new TaskParameterValidator();

        // Check if an input path has been supplied
        taskParameterValidator.checkInputPath(args);

        // If is a path to the aspect words file has been supplied
        taskParameterValidator.checkAspectWordsPath(args);

        Configuration configuration = new Configuration();

        configuration.setBoolean("mapred.map.tasks.speculative", false);
        configuration.setBoolean("mapred.reduce.tasks.speculative", false);
        configuration.set("es.nodes", "localhost:9200");
        configuration.set("es.resource", "reviews/review"); // index or indices used for storing data
        configuration.set("es.index.auto.create", "yes");

        configuration.set("aspectWordsFilePath", args[1]);

        Job job = Job.getInstance(configuration, "sentimentanalysis");
        job.setJarByClass(SentimentAnalysis.class);
        job.setMapperClass(SentimentAnalysisMapper.class);
        job.setReducerClass(SentimentAnalysisReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(MapWritable.class);

        job.setMapOutputValueClass(MapWritable.class);
        job.setOutputFormatClass(EsOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));

        System.exit(job.waitForCompletion(true) ? 1 : 0);
    }
}
