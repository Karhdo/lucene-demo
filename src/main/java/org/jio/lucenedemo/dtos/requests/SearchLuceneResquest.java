package org.jio.lucenedemo.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SearchLuceneResquest {

    @NotNull(message = "text not nulll")
    private String text;

    @NotNull(message = "searchPhrase not nulll")
    private String searchPhrase;
}
