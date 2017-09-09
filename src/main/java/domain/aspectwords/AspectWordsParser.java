package domain.aspectwords;

import domain.entity.AspectWords;

public interface AspectWordsParser {

    /**
     * Gets a file and parses the contents into a List.
     *
     * @param aspectWordsFilePath String
     * @return AspectWords
     * @throws IllegalArgumentException if file is invalid
     */
     AspectWords parse(String aspectWordsFilePath);
}
