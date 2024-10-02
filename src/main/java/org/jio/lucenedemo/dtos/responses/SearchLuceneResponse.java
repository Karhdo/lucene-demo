package org.jio.lucenedemo.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jio.lucenedemo.dtos.requests.DocumentDtoRequest;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SearchLuceneResponse {

    private List<DocumentDtoRequest> matchingDocuments;

    private boolean check;

}
