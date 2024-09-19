package org.jio.lucenedemo.utils;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.ByteBuffersDirectory;

import java.io.IOException;

public class LuceneUtil {

    public static String convertToAsciiUsingLucene(String text) throws IOException {
        // Use custom analyzer to remove diacritics and convert to ASCII
        try (Analyzer analyzer = new ASCIIFoldingAnalyzer()) {
            return analyzeText(analyzer, text);
        }
    }

    private static String analyzeText(Analyzer analyzer, String text) throws IOException {
        TokenStream tokenStream = analyzer.tokenStream(null, text);
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);

        StringBuilder result = new StringBuilder();
        tokenStream.reset();

        while (tokenStream.incrementToken()) {
            result.append(charTermAttribute.toString()).append(" ");
        }

        tokenStream.end();
        tokenStream.close();

        return result.toString().trim();
    }

    // Custom Analyzer that applies ASCII folding to remove diacritics
    private static class ASCIIFoldingAnalyzer extends Analyzer {
        @Override
        protected TokenStreamComponents createComponents(String fieldName) {
            Tokenizer tokenizer = new StandardTokenizer();
            TokenStream filter = new ASCIIFoldingFilter(tokenizer);
            return new TokenStreamComponents(tokenizer, filter);
        }
    }

    public static boolean doesTextSatisfySearch(String text, String searchPhrase) throws IOException, ParseException {
        Analyzer analyzer = new StandardAnalyzer();
        ByteBuffersDirectory directory = new ByteBuffersDirectory();

        // Index the text
        indexText(directory, analyzer, text);

        // Search the index
        return searchIndex(directory, analyzer, searchPhrase);
    }

    private static void indexText(ByteBuffersDirectory directory, Analyzer analyzer, String text) throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter writer = new IndexWriter(directory, config);

        Document doc = new Document();
        doc.add(new TextField("content", text, TextField.Store.YES));
        writer.addDocument(doc);
        writer.close();
    }

    private static boolean searchIndex(ByteBuffersDirectory directory, Analyzer analyzer, String searchPhrase) throws IOException, ParseException {
        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        QueryParser parser = new QueryParser("content", analyzer);
        Query query = parser.parse(searchPhrase);

        TopDocs results = searcher.search(query, 10);
        ScoreDoc[] hits = results.scoreDocs;

        reader.close();

        return hits.length > 0;
    }
}
