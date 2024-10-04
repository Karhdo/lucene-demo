package org.jio.lucenedemo.services;

import lombok.RequiredArgsConstructor;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.RAMDirectory;
import org.jio.lucenedemo.configs.CustomAnalyzer;
import org.jio.lucenedemo.dtos.requests.*;
import org.jio.lucenedemo.dtos.responses.SearchLuceneResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SearchService implements ISearchService{

    @Override
    public SearchLuceneResponse search(SearchLuceneRequest request) throws IOException, ParseException {
        //var
        List<DocumentDtoRequest> matchingDocuments = new ArrayList<>();
        Map<String, DocumentDtoRequest> searchMap = new HashMap<>();

        RAMDirectory index = new RAMDirectory();
        CustomAnalyzer analyzer = new CustomAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(index, config);

        for (DocumentDtoRequest dto : request.getMentions()) {
            searchMap.put(dto.getId(), dto);
            writer.addDocument(dto.toDocument());
        }
        writer.close();


        String[] fields = {"search_text", "mention_type"};
        MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, analyzer);
        Query query = parser.parse(request.getQuery());
        IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(index));

        TopDocs results = searcher.search(query, 10);
        ScoreDoc[] hits = results.scoreDocs;

        for (ScoreDoc hit : hits) {
            Document doc = searcher.doc(hit.doc);
            String foundId = doc.get("id");

            if (searchMap.containsKey(foundId)) {
                matchingDocuments.add(searchMap.get(foundId));
            }
        }

        index.close();

        return SearchLuceneResponse.builder()
                .matchingDocuments(matchingDocuments)
                .build();
    }
}
