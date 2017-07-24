package domain.postags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to contain all the POS Tagger classification types and methods to
 * help decide what type words are.
 *
 * These word types are taken from Penn and are used in the Stanford NLP POS Tagger.
 */
public class PosTags {

    private Map<String, String> tags = new HashMap<>();

    public PosTags() {
        this.tags.put("CC", "Coordinating conjunction");
        this.tags.put("CD", "Cardinal number");
        this.tags.put("DT", "Determiner");
        this.tags.put("EX", "Existential there");
        this.tags.put("FW", "Foreign word");
        this.tags.put("IN", "Preposition or subordinating conjunction");
        this.tags.put("JJ", "Adjective");
        this.tags.put("JJR", "Adjective, comparative");
        this.tags.put("JJS", "Adjective, superlative");
        this.tags.put("LS", "List item marker");
        this.tags.put("MD", "Modal");
        this.tags.put("NN", "Noun, singular or mass");
        this.tags.put("NNS", "Noun, plural");
        this.tags.put("NNP", "Proper noun, singular");
        this.tags.put("NNPS", "Proper noun, plural");
        this.tags.put("PDT", "Predeterminer");
        this.tags.put("POS", "Possessive ending");
        this.tags.put("PRP", "Personal pronoun");
        this.tags.put("PRP$", "Possessive pronoun");
        this.tags.put("RB", "Adverb");
        this.tags.put("RBR", "Adverb, comparative");
        this.tags.put("RBS", "Adverb, superlative");
        this.tags.put("RP", "Particle");
        this.tags.put("SYM", "Symbol");
        this.tags.put("TO", "to");
        this.tags.put("UH", "Interjection");
        this.tags.put("VB", "Verb, base form");
        this.tags.put("VBD", "Verb, past tense");
        this.tags.put("VBG", "Verb, gerund or present participle");
        this.tags.put("VBN", "Verb, past participle");
        this.tags.put("VBP", "Verb, non-3rd person singular present");
        this.tags.put("VBZ", "Verb, 3rd person singular present");
        this.tags.put("WDT", "Wh-determiner");
        this.tags.put("WP", "Wh-pronoun");
        this.tags.put("WP$", "Possessive wh-pronoun");
        this.tags.put("WRB", "Wh-adverb");
    }

    /**
     * Checks if a word has been classified as a Noun.
     * @param word String
     * @return boolean
     */
    public boolean isNoun(String word) {

        List<String> adverbs = new ArrayList<>();
        adverbs.add("NN");
        adverbs.add("NNS");
        adverbs.add("NNP");
        adverbs.add("NNPS");

        String[] parts = word.split("_");

        return parts.length >= 2 && adverbs.contains(parts[1]);
    }

    /**
     * Checks if a word has been classified as an Adverb.
     * @param word String
     * @return boolean
     */
    public boolean isAdverb(String word) {

        List<String> adverbs = new ArrayList<>();
        adverbs.add("RB");
        adverbs.add("RBR");
        adverbs.add("RBS");
        adverbs.add("WRB");

        String[] parts = word.split("_");

        return parts.length >= 2 && adverbs.contains(parts[1]);
    }

    /**
     * Checks if the word has been classified as a Verb.
     * @param word String
     * @return boolean
     */
    public boolean isVerb(String word) {

        List<String> verbs = new ArrayList<>();
        verbs.add("VB");
        verbs.add("VBD");
        verbs.add("VBG");
        verbs.add("VBN");
        verbs.add("VBP");
        verbs.add("VBZ");

        String[] parts = word.split("_");

        return parts.length >= 2 && verbs.contains(parts[1]);
    }

    public boolean isAdjective(String word) {

        List<String> adjectives = new ArrayList<>();
        adjectives.add("JJ");
        adjectives.add("JJR");
        adjectives.add("JJS");

        String[] parts = word.split("_");

        return parts.length >= 2 && adjectives.contains(parts[1]);
    }

    public boolean isAdverbInRangeOfAspectWord(String document, String aspectWord, int nGram) {

        String[] d = document.split(aspectWord);

        for (String section : d) {
            String[] words       = section.split(" ");

            if (words.length > nGram) {
                // Cap the string to five words
                words = Arrays.copyOfRange(words, 0, nGram - 1);
            }

            // Check the five (or less) words for an adverb
            for (String w : words) {
                if (this.isAdverb(w)) {
                    return true;
                }
            }
        }

        return false;
    }
}
