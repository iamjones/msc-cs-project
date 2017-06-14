package dictionarybuilder.mapper;

import com.google.inject.AbstractModule;
import utils.Punctuation;
import utils.StopWords;

/**
 * Sets up dependency injection for the DictionaryMapper class.
 */
public class DictionaryMapperModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(StopWords.class).to(StopWords.class);
        bind(Punctuation.class).to(Punctuation.class);
    }
}
