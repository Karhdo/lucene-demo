package org.jio.lucenedemo.configs;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

public class CustomAnalyzer extends Analyzer {
    @Override
    protected TokenStreamComponents createComponents(String s) {
        StandardTokenizer tokenizer = new StandardTokenizer();
        TokenStream filter = new LowerCaseFilter(tokenizer);
        filter = new ASCIIFoldingFilter(filter);

        return new TokenStreamComponents(tokenizer, filter);
    }
}
