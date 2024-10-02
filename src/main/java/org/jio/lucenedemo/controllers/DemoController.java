package org.jio.lucenedemo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.lucene.queryparser.classic.ParseException;
import org.jio.lucenedemo.dtos.commons.ApiResponse;
import org.jio.lucenedemo.dtos.requests.SearchLuceneResquest;
import org.jio.lucenedemo.dtos.responses.SearchLuceneResponse;
import org.jio.lucenedemo.services.ISearchService;
import org.jio.lucenedemo.utils.ValidatorUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("${api.prefix}/lucenes")
@RequiredArgsConstructor
public class DemoController {

    private final ValidatorUtil validatorUtil;
    private final ISearchService searchService;


    @Operation(
            summary = "FEATURE: Search Lucene API",
            description = "Search documents using Lucene based on search phrases.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Sample request body",
                    content = @Content(schema = @Schema(
                            example = "{\n" +
                                    "  \"query\": \"#milobotphienbangioihan OR ((milo Mllo ((\\\"mi lo\\\" \\\"mylo\\\") AND (\\\"dynamind\\\" \\\"SEA Games\\\" \\\"SEAGames\\\" \\\"am nong\\\" \\\"sua\\\" \\\"balo\\\" \\\"ballo\\\" \\\"binh nuoc\\\" \\\"the thao\\\" \\\"nang luong\\\" \\\"nap van\\\" \\\"nha vo dich\\\" \\\"ben bi\\\" \\\"#DànhChoGiớiTrẻ\\\" \\\"#ProteinCanxi\\\" \\\"#HànhTrìnhChinhPhục\\\" \\\"Nang luong va Y Chi de thanh cong\\\" \\\"ý chí\\\" \\\"#TapTrungToiNoc\\\" \\\"học viện thể thao activ\\\" \\\"#hocvienthethao\\\" \\\"#VuTruNangDong\\\" \\\"#TUYENTHUVUTRUNANGDONG\\\" \\\"Năng lượng tự nhiên để cao lớn vươn xa\\\" \\\"#nangluong tunhien\\\" \\\"#caolonvuonxa\\\" \\\"#Nangluongchogioitre\\\" \\\"Năng Lượng cho giới trẻ\\\" \\\"cao lãnh\\\" \\\"đồng tháp\\\" \\\"gáo gồng\\\" \\\"gáo giồng\\\"))) AND -(\\\"milo yiannopoulos\\\" \\\"wrote\\\" \\\"nitro\\\" \\\"CS Go\\\" \\\"uống pepsi\\\" \\\"cube\\\" \\\"baby milo\\\" \\\"petshop\\\" \\\"sdt\\\" \\\"hotline\\\" \\\"hot line\\\" \\\"shop\\\" \\\"ship\\\"))\",\n" +
                                    "  \"searchPhrases\": [\n" +
                                    "    {\n" +
                                    "      \"id\": \"0a4a4d4b-4087-5c2c-990a-5176ad67dbe9\",\n" +
                                    "      \"link\": \"instagram.com/p/C_2KstLIfrr#18070142818580059\",\n" +
                                    "      \"id_source\": \"ig_3137124503\",\n" +
                                    "      \"id_reference\": \"2926683d-c837-5463-9a5b-90f32ea108fe\",\n" +
                                    "      \"id_parent_comment\": \"747153ff-a637-5272-80d3-184b4fb7d400\",\n" +
                                    "      \"views\": 0,\n" +
                                    "      \"likes\": 0,\n" +
                                    "      \"comments\": 0,\n" +
                                    "      \"shares\": 0,\n" +
                                    "      \"rating_score\": 0,\n" +
                                    "      \"engagement_total\": 0,\n" +
                                    "      \"engagement_s_c\": 0,\n" +
                                    "      \"identity\": \"ig_3137124503\",\n" +
                                    "      \"identity_name\": \"Karolina Dula\",\n" +
                                    "      \"mention_type\": 2,\n" +
                                    "      \"search_text\": [\n" +
                                    "        \"\",\n" +
                                    "        \"@ola.ruu miło mi 😍\"\n" +
                                    "      ],\n" +
                                    "      \"attachment\": \"{\\\"parent_info\\\":{\\\"link\\\":\\\"instagram.com/p/C_2KstLIfrr/\\\",\\\"title\\\":\\\"Loro Piana silk &\\\"}}\",\n" +
                                    "      \"is_to_topic\": false\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"id\": \"98208d98-a1d3-5209-8fee-2ee838a26a97\",\n" +
                                    "      \"link\": \"instagram.com/p/C_9-nDnIW3A#18010650494367976\",\n" +
                                    "      \"id_source\": \"ig_3137124503\",\n" +
                                    "      \"id_reference\": \"c0959e97-5b21-53ea-b49e-08c5b5800011\",\n" +
                                    "      \"id_parent_comment\": \"eb131a8b-076b-5c63-bfaf-a8677be56972\",\n" +
                                    "      \"views\": 0,\n" +
                                    "      \"likes\": 0,\n" +
                                    "      \"comments\": 0,\n" +
                                    "      \"shares\": 0,\n" +
                                    "      \"rating_score\": 0,\n" +
                                    "      \"engagement_total\": 0,\n" +
                                    "      \"identity\": \"ig_3137124503\",\n" +
                                    "      \"mention_type\": 2,\n" +
                                    "      \"search_text\": [\n" +
                                    "        \"\",\n" +
                                    "        \"@__camilla_aa miło mi 😍\"\n" +
                                    "      ],\n" +
                                    "      \"attachment\": \"{\\\"parent_info\\\":{\\\"link\\\":\\\"instagram.com/p/C_9-nDnIW3A/\\\",\\\"title\\\":\\\"Najpiękniejsza\\\"}}\",\n" +
                                    "      \"is_to_topic\": false\n" +
                                    "    }\n" +
                                    "  ]\n" +
                                    "}"
                    ))
            )
    )
    @PostMapping("/luceneSearch")
    public ResponseEntity<?> luceneSearch(
            @Valid @RequestBody SearchLuceneResquest resquest,
            BindingResult result
    ) throws IOException, ParseException {
        ApiResponse apiResponse = new ApiResponse();

        if (result.hasErrors()) {
            apiResponse.error(validatorUtil.handleValidationErrors(result.getFieldErrors()));
            return ResponseEntity.badRequest().body(apiResponse);
        }

        SearchLuceneResponse response = searchService.search(resquest);

        apiResponse.ok(response.getMatchingDocuments());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
