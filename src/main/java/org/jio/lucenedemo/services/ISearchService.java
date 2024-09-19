package org.jio.lucenedemo.services;

import jakarta.validation.Valid;
import org.apache.lucene.queryparser.classic.ParseException;
import org.jio.lucenedemo.dtos.requests.SearchLuceneResquest;
import org.jio.lucenedemo.dtos.responses.SearchLuceneResponse;

import java.io.IOException;

public interface ISearchService {
    SearchLuceneResponse search(@Valid SearchLuceneResquest resquest) throws IOException, ParseException;
}
