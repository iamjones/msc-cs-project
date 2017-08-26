package RootMeanSquareError;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import domain.entity.RootMeanSequareError.Document;
import org.apache.htrace.fasterxml.jackson.databind.JsonNode;
import org.apache.htrace.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Script to calculate the root mean square error of all the data
 * that we written to Elasticsearch from the sentiment analysis hadoop task.
 */
public class RmseCalculator {

    public static void main(String args[]) throws
        IOException
    {

        String elasticSearchUrl = "http://localhost:9200/reviews/_search";
        String httpMethod       = "POST";
        String httpBody         = "{"
        + "    \"query\": {}"
        + "}";



        HttpURLConnection httpcon = (HttpURLConnection) new URL(elasticSearchUrl).openConnection();
        httpcon.setDoOutput(true);
        httpcon.setRequestProperty("Content-Type", "application/json");
        httpcon.setRequestProperty("Accept", "application/json");
        httpcon.setRequestMethod(httpMethod);
        httpcon.connect();

        byte[] httpBodyBytes = httpBody.getBytes("UTF-8");
        OutputStream os = httpcon.getOutputStream();
        os.write(httpBodyBytes);
        os.close();

        InputStream response = httpcon.getInputStream();

        String result = CharStreams.toString(new InputStreamReader(
            response,
            Charsets.UTF_8
        ));

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json             = objectMapper.readTree(result);

        Document[] results = objectMapper.treeToValue(json.get("hits").get("hits"), Document[].class);

        Double squaredDifferences = 0.0;
        Integer numOfRecords      = 0;

        for (Document d : results) {

            squaredDifferences += Math.pow(d.get_source().getUserRating() - d.get_source().getPredictedRating(), 2);

            numOfRecords++;
        }

        Double rmse = Math.sqrt(squaredDifferences / numOfRecords);

        System.out.println("-----------");
        System.out.println("-----------");
        System.out.println("Root-mean-square Error is:");
        System.out.println(rmse.toString());
        System.out.println("-----------");
        System.out.println("-----------");
    }
}
