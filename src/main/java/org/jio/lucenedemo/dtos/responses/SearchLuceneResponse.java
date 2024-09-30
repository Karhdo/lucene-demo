package org.jio.lucenedemo.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SearchLuceneResponse {

    private HashMap<String, Boolean> mapCheck;

    private HashMap<Integer, Boolean> mapCheckV2;

    //private boolean check;
}
