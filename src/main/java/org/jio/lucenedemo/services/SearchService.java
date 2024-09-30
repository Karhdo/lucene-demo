package org.jio.lucenedemo.services;

import lombok.RequiredArgsConstructor;
import org.apache.lucene.queryparser.classic.ParseException;
import org.jio.lucenedemo.dtos.requests.SearchLuceneObjectResquest;
import org.jio.lucenedemo.dtos.requests.SearchLuceneResquest;
import org.jio.lucenedemo.dtos.requests.SearchPhraseRequest;
import org.jio.lucenedemo.dtos.responses.SearchLuceneResponse;
import org.jio.lucenedemo.utils.LuceneUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService implements ISearchService{

    @Override
    public SearchLuceneResponse search(SearchLuceneResquest resquest) throws IOException, ParseException {
        String asciiText = LuceneUtil.convertToAsciiUsingLucene(resquest.getText());

        //boolean result = LuceneUtil.doesTextSatisfySearch(asciiText, resquest.getSearchPhrase());

        HashMap<String, Boolean> mapCheck = new HashMap<>();

        for (String search : resquest.getSearchPhrases()) {
            boolean result = LuceneUtil.doesTextSatisfySearch(asciiText,search);
            mapCheck.put(search, result);

        }

        return SearchLuceneResponse.builder()
                .mapCheck(mapCheck)
                .build();
    }

    @Override
    public SearchLuceneResponse searchV2(SearchLuceneObjectResquest resquest) throws IOException, ParseException {
        String asciiText = LuceneUtil.convertToAsciiUsingLucene(resquest.getText());

        //boolean result = LuceneUtil.doesTextSatisfySearch(asciiText, resquest.getSearchPhrase());

        HashMap<Integer, Boolean> mapCheck = new HashMap<>();

        for (SearchPhraseRequest search : resquest.getSearchPhrases()) {
            boolean result = LuceneUtil.doesTextSatisfySearch(asciiText,search.getPhrase());
            mapCheck.put(search.getId(), result);

        }

        return SearchLuceneResponse.builder()
                .mapCheckV2(mapCheck)
                .build();
    }
}
