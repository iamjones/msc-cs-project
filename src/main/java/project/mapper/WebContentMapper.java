package project.mapper;

import com.google.inject.Inject;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.util.logging.Logger;

/**
 * Mapper to extract text and map url to sentiment score.
 */
public class WebContentMapper extends Mapper<LongWritable, Text, Text, Text> {

    private Logger logger = Logger.getLogger("WebContentMapper"); // @TODO use class not string

    @Inject
    public WebContentMapper() {

    }

    protected void map(LongWritable key, Text value, Context context) {


    }
}
