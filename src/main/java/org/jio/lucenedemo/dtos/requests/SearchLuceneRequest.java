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
public class SearchLuceneRequest {

    @NotNull(message = "Query not null")
    private String query;

    @NotNull(message = "Mentions not null")
    private List<DocumentDtoRequest> mentions;
}
