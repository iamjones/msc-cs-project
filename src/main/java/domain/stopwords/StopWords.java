package domain.stopwords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class holds all the stop words a provided methods to
 * do some processing with the stored list of stop words.
 */
public class StopWords {

    public String[] stopwords = {
        "a","about","above","after","again","against","all","am","an","and","any","are","arent","as","at","be",
        "because","been","before","being","below","between","both","but","by","cant","cannot","could","couldnt",
        "did","didnt","do","does","doesnt","doing","dont","down","during","each","few","for","from","further",
        "had","hadnt","has","hasnt","have","havent","having","he","hed","hell","hes","her","here","heres",
        "hers","herself","him","himself","his","how","hows","i","id","ill","im","ive","if","in","into","is",
        "isnt","it","its","its","itself","lets","me","more","most","mustnt","my","myself","no","nor",
        "of","off","on","once","only","or","other","ought","our","ours","ourselves","out","over","own","same",
        "shant","she","shed","shell","shes","should","shouldnt","so","some","such","than","that","thats","the",
        "their","theirs","them","themselves","then","there","theres","these","they","theyd","theyll","theyre",
        "theyve","this","those","through","to","too","under","until","up","very","was","wasnt","we","wed","well",
        "were","weve","were","werent","what","whats","when","whens","where","wheres","which","while","who",
        "whos","whom","why","whys","with","wont","would","wouldnt","you","youd","youll","youre","youve","your",
        "yours","yourself","yourselves"
    };

    private List<String> stopwordsList;

    public StopWords() {
        this.stopwordsList = Arrays.asList(this.stopwords);
    }

    /**
     * Takes a document and removes all the stop words that it contains.
     * Returns a list of words that are not found in our stop word list.
     *
     * @param document String
     * @return List<String>
     */
    public List<String> removeStopWords(List<String> document) {

        List<String> nonStopWords = new ArrayList<>();

        for (String word : document) {
            if (!this.stopwordsList.contains(word.toLowerCase())) {
                nonStopWords.add(word);
            }
        }

        return nonStopWords;
    }
}
