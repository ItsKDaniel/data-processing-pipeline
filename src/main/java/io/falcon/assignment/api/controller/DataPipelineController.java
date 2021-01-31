package io.falcon.assignment.api.controller;

import io.falcon.assignment.common.model.Payload;
import io.falcon.assignment.common.utils.ApiResponseCodes;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

import static io.falcon.assignment.common.utils.ApiResponseCodes.PAYLOAD_PUBLISHED;
import static io.falcon.assignment.common.utils.ApiResponseCodes.SUCCESS;

@RestController
public class DataPipelineController {

    @PostMapping(path = "/api/publish", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payload published successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = io.falcon.assignment.common.model.ApiResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request payload",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = io.falcon.assignment.common.model.ApiResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected errors",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = io.falcon.assignment.common.model.ApiResponse.class)))
    })
    public ResponseEntity<Object> publishPayload(@Valid @RequestBody Payload payload) {
        return response(PAYLOAD_PUBLISHED);
    }

    @GetMapping(path = "/api/payload/all")
    public ResponseEntity<Object> getAllPayload() {
        return response(SUCCESS, Collections.emptyList());
    }

    private ResponseEntity<Object> response(ApiResponseCodes response) {
        return ResponseEntity.status(response.getHttpCode())
                .body(response.asResponse());
    }

    private ResponseEntity<Object> response(ApiResponseCodes response, Object content) {
        return ResponseEntity.status(response.getHttpCode())
                .body(response.asResponse(content));
    }
}
