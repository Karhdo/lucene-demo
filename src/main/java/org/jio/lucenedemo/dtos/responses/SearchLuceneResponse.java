package org.jio.lucenedemo.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SearchLuceneResponse {

    private boolean check;
}
