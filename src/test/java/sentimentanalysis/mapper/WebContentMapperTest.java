package project.mapper;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Before;

public class WebContentMapperTest {

    private MapDriver<LongWritable, Text, Text, Text> webContentMapper;

    @Before
    public void setUp() {
        WebContentMapper mapper = new WebContentMapper();

        webContentMapper = MapDriver.newMapDriver(mapper);
    }
}