package org.jio.lucenedemo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.lucene.queryparser.classic.ParseException;
import org.jio.lucenedemo.dtos.requests.SearchLuceneObjectResquest;
import org.jio.lucenedemo.dtos.requests.SearchLuceneResquest;
import org.jio.lucenedemo.dtos.commons.ApiResponse;
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
            summary = "Search Lucene API",
            description = "Search documents using Lucene based on search phrases.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Sample request body",
                    content = @Content(schema = @Schema(
                            example = "{\n" +
                                    "  \"text\": \"@stylebyelka_wiolka bardzo mi miło i deceniam 🤍\",\n" +
                                    "  \"searchPhrases\": [\n" +
                                    "    \"#milobotphienbangioihan OR ((milo Mllo ((\\\"mi lo\\\" \\\"mylo\\\") AND (\\\"dynamind\\\" \\\"SEA Games\\\" \\\"SEAGames\\\" \\\"am nong\\\" \\\"sua\\\" \\\"balo\\\" \\\"ballo\\\" \\\"binh nuoc\\\" \\\"the thao\\\" \\\"nang luong\\\" \\\"nap van\\\" \\\"nha vo dich\\\" \\\"ben bi\\\" \\\"#DànhChoGiớiTrẻ\\\" \\\"#ProteinCanxi\\\" \\\"#HànhTrìnhChinhPhục\\\" \\\"Nang luong va Y Chi de thanh cong\\\" \\\"ý chí\\\" \\\"#TapTrungToiNoc\\\" \\\"học viện thể thao activ\\\" \\\"#hocvienthethao\\\" \\\"#VuTruNangDong\\\" \\\"#TUYENTHUVUTRUNANGDONG\\\" \\\"Năng lượng tự nhiên để cao lớn vươn xa\\\" \\\"#nangluong tunhien\\\" \\\"#caolonvuonxa\\\" \\\"#Nangluongchogioitre\\\" \\\"Năng Lượng cho giới trẻ\\\" \\\"cao lãnh\\\" \\\"đồng tháp\\\" \\\"gáo gồng\\\" \\\"gáo giồng\\\"))) AND -(\\\"milo yiannopoulos\\\" \\\"wrote\\\" \\\"nitro\\\" \\\"CS Go\\\" \\\"uống pepsi\\\" \\\"cube\\\" \\\"baby milo\\\" \\\"petshop\\\" \\\"sdt\\\" \\\"hotline\\\" \\\"hot line\\\" \\\"shop\\\" \\\"ship\\\"))\",\n" +
                                    "    \"bibi\"\n" +
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

        apiResponse.ok(response.getMapCheck());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @Operation(
            summary = "Search Lucene API V2",
            description = "Search documents using Lucene based on search phrases.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Sample request body",
                    content = @Content(schema = @Schema(
                            example = "{\n" +
                                    "  \"text\": \"@stylebyelka_wiolka bardzo mi miło i deceniam 🤍\",\n" +
                                    "  \"searchPhrases\": [\n" +
                                    "    {\n" +
                                    "      \"id\": 1,\n" +
                                    "      \"phrase\": \"#milobotphienbangioihan OR ((milo Mllo ((\\\"mi lo\\\" \\\"mylo\\\") AND (\\\"dynamind\\\" \\\"SEA Games\\\" \\\"SEAGames\\\" \\\"am nong\\\" \\\"sua\\\" \\\"balo\\\" \\\"ballo\\\" \\\"binh nuoc\\\" \\\"the thao\\\" \\\"nang luong\\\" \\\"nap van\\\" \\\"nha vo dich\\\" \\\"ben bi\\\" \\\"#DànhChoGiớiTrẻ\\\" \\\"#ProteinCanxi\\\" \\\"#HànhTrìnhChinhPhục\\\" \\\"Nang luong va Y Chi de thanh cong\\\" \\\"ý chí\\\" \\\"#TapTrungToiNoc\\\" \\\"học viện thể thao activ\\\" \\\"#hocvienthethao\\\" \\\"#VuTruNangDong\\\" \\\"#TUYENTHUVUTRUNANGDONG\\\" \\\"Năng lượng tự nhiên để cao lớn vươn xa\\\" \\\"#nangluong tunhien\\\" \\\"#caolonvuonxa\\\" \\\"#Nangluongchogioitre\\\" \\\"Năng Lượng cho giới trẻ\\\" \\\"cao lãnh\\\" \\\"đồng tháp\\\" \\\"gáo gồng\\\" \\\"gáo giồng\\\"))) AND -(\\\"milo yiannopoulos\\\" \\\"wrote\\\" \\\"nitro\\\" \\\"CS Go\\\" \\\"uống pepsi\\\" \\\"cube\\\" \\\"baby milo\\\" \\\"petshop\\\" \\\"sdt\\\" \\\"hotline\\\" \\\"hot line\\\" \\\"shop\\\" \\\"ship\\\"))\"\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"id\": 2,\n" +
                                    "      \"phrase\": \"bibi\"\n" +
                                    "    }\n" +
                                    "  ]\n" +
                                    "}"
                    ))
            )
    )

    @PostMapping("/luceneSearchV2")
    public ResponseEntity<?> luceneSearchV2(
            @Valid @RequestBody SearchLuceneObjectResquest resquest,
            BindingResult result
    ) throws IOException, ParseException {
        ApiResponse apiResponse = new ApiResponse();

        if (result.hasErrors()) {
            apiResponse.error(validatorUtil.handleValidationErrors(result.getFieldErrors()));
            return ResponseEntity.badRequest().body(apiResponse);
        }

        SearchLuceneResponse response = searchService.searchV2(resquest);

        apiResponse.ok(response.getMapCheckV2());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
