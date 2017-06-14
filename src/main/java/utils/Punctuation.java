package utils;

/**
 * This class holds all types of punctuation we want to use and some methods to
 * do some processing with the stored list of punctuations.
 */
public class Punctuation {

    private String[] punctuation = {
        "!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",",
        "-", ".", "/", ":", ";", "<", "=", ">", "?", "@", "[", "]", "^",
        "_", "`", "{", "|", "}","~","\\"
    };

    /**
     * Takes a string and if it has any punctuation from the punctuation array it
     * is removed from the string and the string is returned.
     *
     * @param document String
     * @return String
     */
    public String removeAllPunctuation(String document) {

        String punctuationRemoved = document;

        for (String i : this.punctuation) {
            punctuationRemoved = punctuationRemoved.replace(i, "");
        }

        return punctuationRemoved;
    }
}
