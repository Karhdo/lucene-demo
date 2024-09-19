package org.jio.lucenedemo.services;

import lombok.RequiredArgsConstructor;
import org.apache.lucene.queryparser.classic.ParseException;
import org.jio.lucenedemo.dtos.requests.SearchLuceneResquest;
import org.jio.lucenedemo.dtos.responses.SearchLuceneResponse;
import org.jio.lucenedemo.utils.LuceneUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SearchService implements ISearchService{

    @Override
    public SearchLuceneResponse search(SearchLuceneResquest resquest) throws IOException, ParseException {
        String asciiText = LuceneUtil.convertToAsciiUsingLucene(resquest.getText());
        boolean result = LuceneUtil.doesTextSatisfySearch(asciiText, resquest.getSearchPhrase());

        return SearchLuceneResponse.builder()
                .check(result)
                .build();
    }
}
