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
public class SearchLuceneResquest {

    @NotNull(message = "text not nulll")
    private String query;

    @NotNull(message = "mentions not nulll")
    private List<DocumentDtoRequest> mentions;
}
