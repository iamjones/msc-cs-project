package sentimentanalysis;

import com.google.inject.Guice;
import domain.aspectwords.AspectWordsParser;
import domain.entity.AspectWords;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import sentimentanalysis.mapper.SentimentAnalysisMapper;
import sentimentanalysis.mapper.SentimentAnalysisMapperModule;
import sentimentanalysis.reducer.SentimentAnalysisReducer;

import java.io.IOException;

/**
 * This class handles:
 *  - Running the hadoop tasks
 */
public class SentimentAnalysis {

    public static void main(String[] args) throws
            IOException,
            InterruptedException,
            ClassNotFoundException {

        Configuration configuration = new Configuration();

        // Set up our dependency injection modules
        Guice.createInjector(new SentimentAnalysisMapperModule());

        // Parse the file containing the aspect words into a List
        // that we can consumer further in the process
        String aspectWordFilePath = "src/main/resources/aspectwords/aspectwords-test.json";
        AspectWordsParser aspectWordsParser = new AspectWordsParser();
        AspectWords aspectWords = aspectWordsParser.parse(aspectWordFilePath);

        Job job = Job.getInstance(configuration, "sentimentanalysis");
        job.setJarByClass(SentimentAnalysis.class);
        job.setMapperClass(SentimentAnalysisMapper.class);
        job.setReducerClass(SentimentAnalysisReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 1 : 0);
    }
}
