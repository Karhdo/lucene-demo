package org.jio.lucenedemo.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SearchLuceneObjectResquest {
    @NotNull(message = "text not nulll")
    private String text;

    @NotNull(message = "searchPhrases not nulll")
    private List<SearchPhraseRequest> searchPhrases;
}
