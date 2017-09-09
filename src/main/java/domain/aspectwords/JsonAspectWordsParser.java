package domain.aspectwords;

import domain.entity.AspectWords;
import org.apache.htrace.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Class responsible for reading a list of aspects words from a JSON file and
 * parsing them into a List which will be stored in a singleton class
 * that can be injected and consumed by other classes.
 */
public class JsonAspectWordsParser implements AspectWordsParser {

    /**
     * {@inheritDoc}
     */
    @Override
    public AspectWords parse(String aspectWordsFilePath) {

        try {
            File aspectWordsFile = new File(aspectWordsFilePath);

            if (!aspectWordsFile.exists()) {
                throw new IllegalArgumentException("File '" + aspectWordsFilePath + "' does not exist.");
            }

            ObjectMapper mapper = new ObjectMapper();

            List<String> aspectWords = mapper.readValue(aspectWordsFile, List.class);

            if (aspectWords.size() == 0) {
                throw new IllegalArgumentException("File does not contain any aspect words.");
            }

            return new AspectWords(aspectWords);

        } catch(IOException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }
}
