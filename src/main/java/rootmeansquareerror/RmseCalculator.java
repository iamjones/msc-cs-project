package rootmeansquareerror;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import domain.entity.rootmeansequareerror.Document;
import org.apache.htrace.fasterxml.jackson.databind.JsonNode;
import org.apache.htrace.fasterxml.jackson.databind.ObjectMapper;

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

    public static void main(String args[]) {

        String scrollId = null;

        try {

            String elasticSearchUrl = "http://localhost:9200/reviews/review/_search?scroll=10m";
            String httpMethod = "POST";
            Integer size = 5000;

            String httpBody = "{"
                + "\"size\": " + size.toString()
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

            JsonNode json = objectMapper.readTree(result);

            scrollId = objectMapper.treeToValue(json.get("_scroll_id"), String.class);

            Document[] results = objectMapper.treeToValue(json.get("hits").get("hits"), Document[].class);

            Double squaredDifferences = 0.0;
            Integer numOfRecords = 0;

            while (results.length != 0) {

                String scrollHttpBody = "{"
                    + "\"scroll\": \"10m\","
                    + "\"scroll_id\": \"" + scrollId + "\""
                    + "}";

                HttpURLConnection scrollHttpcon = (HttpURLConnection) new URL("http://localhost:9200/_search/scroll").openConnection();
                scrollHttpcon.setDoOutput(true);
                scrollHttpcon.setRequestProperty("Content-Type", "application/json");
                scrollHttpcon.setRequestProperty("Accept", "application/json");
                scrollHttpcon.setRequestMethod(httpMethod);
                scrollHttpcon.connect();

                byte[] scrollHttpBodyBytes = scrollHttpBody.getBytes("UTF-8");
                OutputStream scrollOs = scrollHttpcon.getOutputStream();
                scrollOs.write(scrollHttpBodyBytes);
                scrollOs.close();

                InputStream scrollResponse = scrollHttpcon.getInputStream();

                String scrollResult = CharStreams.toString(new InputStreamReader(
                    scrollResponse,
                    Charsets.UTF_8
                ));

                JsonNode scrollJson = objectMapper.readTree(scrollResult);

                results = objectMapper.treeToValue(scrollJson.get("hits").get("hits"), Document[].class);
                scrollId = objectMapper.treeToValue(scrollJson.get("_scroll_id"), String.class);

                for (Document d : results) {

                    squaredDifferences += Math.pow(d.get_source().getUserRating() - d.get_source().getPredictedRating(), 2);
                    numOfRecords++;
                }
            }

            Double rmse = Math.sqrt(squaredDifferences / numOfRecords);

            System.out.println("-----------");
            System.out.println("-----------");
            System.out.println("Total number of documents: ");
            System.out.println(numOfRecords.toString());
            System.out.println("-----------");
            System.out.println("-----------");
            System.out.println("Root-mean-square Error is: ");
            System.out.println(rmse.toString());
            System.out.println("-----------");
            System.out.println("-----------");

        } catch (Exception e) {
            System.out.println("An exception occurred:");
            System.out.println(e.getMessage());

            System.out.println("-----------");
            System.out.println("Scroll ID:" + scrollId);
            System.out.println("-----------");
        }
    }
}
