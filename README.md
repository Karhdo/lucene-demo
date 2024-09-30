# User Guide

## Overview of Lucene

Apache Lucene is a high-performance, full-featured text search engine library written in Java. It is a technology suitable for nearly any application that requires full-text search, especially cross-platform. Lucene provides powerful features through a simple API:

- **Indexing and Searching**: Lucene allows you to index documents and search them efficiently.
- **Text Analysis**: It includes analyzers to process text, such as tokenizing, filtering, and stemming.
- **Query Parsing**: Lucene supports complex query parsing, enabling sophisticated search capabilities.
- **Scalability**: It is designed to handle large volumes of data and can be scaled horizontally.

## How the Lucene Search API Works

The Lucene search API works through a series of steps to index and search text data:

1. **Text Analysis**: The text is first analyzed using an `Analyzer`, such as the `StandardAnalyzer`. This process involves tokenizing the text into terms and applying filters to normalize the text.

    ```java
    Analyzer analyzer = new StandardAnalyzer();
    ```

2. **Indexing**: The analyzed text is then indexed using an `IndexWriter`. Each document is added to the index, where it is stored in a way that makes it efficient to search.

    ```java
    IndexWriterConfig config = new IndexWriterConfig(analyzer);
    IndexWriter writer = new IndexWriter(directory, config);
    Document doc = new Document();
    doc.add(new TextField("content", text, Field.Store.YES));
    writer.addDocument(doc);
    writer.close();
    ```

3. **Query Parsing**: When a search query is received, it is parsed into a `Query` object using a `QueryParser`.

    ```java
    QueryParser parser = new QueryParser("content", analyzer);
    Query query = parser.parse(searchPhrase);
    ```

4. **Searching**: The parsed query is then executed against the index using an `IndexSearcher`. The search results are retrieved as `TopDocs`, which contain the matching documents.

    ```java
    IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(directory));
    TopDocs results = searcher.search(query, 10);
    ```

5. **Result Processing**: The search results are processed and returned to the user. Each result can be accessed to retrieve the stored fields.

    ```java
    for (ScoreDoc scoreDoc : results.scoreDocs) {
        Document doc = searcher.doc(scoreDoc.doc);
        String content = doc.get("content");
        // Process the content
    }
    ```

In your project, the `SearchService` class implements the search functionality using Lucene. The `search` method in `SearchService` converts the input text to ASCII, performs the search, and returns the result.

```java
@Service
@RequiredArgsConstructor
public class SearchService implements ISearchService {
    @Override
    public SearchLuceneResponse search(SearchLuceneResquest resquest) throws IOException, ParseException {
        String asciiText = LuceneUtil.convertToAsciiUsingLucene(resquest.getText());
        boolean result = LuceneUtil.doesTextSatisfySearch(asciiText, resquest.getSearchPhrase());
        return SearchLuceneResponse.builder().check(result).build();
    }
}