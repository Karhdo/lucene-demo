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
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.jio.lucenedemo.configs.CustomAnalyzer;
import org.jio.lucenedemo.dtos.requests.*;
import org.jio.lucenedemo.dtos.responses.SearchLuceneResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SearchService implements ISearchService {

    @Override
    public SearchLuceneResponse search(SearchLuceneResquest request) throws IOException, ParseException {
        //var
        List<DocumentDtoRequest> matchingDocuments = new ArrayList<>();
        Map<String, DocumentDtoRequest> searchMap = new HashMap<>();

        RAMDirectory index = new RAMDirectory();
        CustomAnalyzer analyzer = new CustomAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = null;
        DirectoryReader reader = null;

        try {
            writer = new IndexWriter(index, config);

            // Thêm các tài liệu vào index
            for (DocumentDtoRequest dto : request.getSearchPhrases()) {
                searchMap.put(dto.getId(), dto);
                writer.addDocument(dto.toDocument());
            }
            writer.close();  // Đóng IndexWriter sau khi hoàn thành ghi

            // Tìm kiếm
            String[] fields = {"search_text", "mention_type"};
            MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, analyzer);
            Query query = parser.parse(request.getQuery());
            reader = DirectoryReader.open(index);  // Mở DirectoryReader cho việc tìm kiếm
            IndexSearcher searcher = new IndexSearcher(reader);

            TopDocs results = searcher.search(query, 10);
            ScoreDoc[] hits = results.scoreDocs;

            for (ScoreDoc hit : hits) {
                Document doc = searcher.doc(hit.doc);
                String foundId = doc.get("id");

                if (searchMap.containsKey(foundId)) {
                    matchingDocuments.add(searchMap.get(foundId));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng các tài nguyên để giải phóng bộ nhớ
            if (writer != null && writer.isOpen()) {
                writer.close(); // Đóng IndexWriter
            }
            if (reader != null) {
                reader.close(); // Đóng DirectoryReader
            }
            //index.close(); // Đóng RAMDirectory để giải phóng bộ nhớ
        }

        return SearchLuceneResponse.builder()
                .matchingDocuments(matchingDocuments)
                .build();
    }

}
