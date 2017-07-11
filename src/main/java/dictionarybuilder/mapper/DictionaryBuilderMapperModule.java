package dictionarybuilder.mapper;

import com.google.inject.AbstractModule;
import domain.punctuation.Punctuation;
import domain.stopwords.StopWords;

/**
 * Sets up dependency injection for the DictionaryBuilderMapper class.
 */
public class DictionaryBuilderMapperModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(StopWords.class).to(StopWords.class);
        bind(Punctuation.class).to(Punctuation.class);
    }
}
