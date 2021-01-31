package io.falcon.assignment.controller;

import io.falcon.assignment.model.Payload;
import io.falcon.assignment.model.PayloadRequest;
import io.falcon.assignment.service.pubsub.RedisPubSubService;
import io.falcon.assignment.utils.ApiResponseCodes;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

import static io.falcon.assignment.utils.ApiResponseCodes.PAYLOAD_PUBLISHED;
import static io.falcon.assignment.utils.ApiResponseCodes.SUCCESS;

@RestController
public class DataPipelineController extends BaseController {

    private final RedisPubSubService publisher;

    @Autowired
    public DataPipelineController(RedisPubSubService publisher) {
        this.publisher = publisher;
    }

    @PostMapping(path = "/api/publish", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payload published successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = io.falcon.assignment.model.ApiResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request payload",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = io.falcon.assignment.model.ApiResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected errors",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = io.falcon.assignment.model.ApiResponse.class)))
    })
    public ResponseEntity<Object> publishPayload(@Valid @RequestBody PayloadRequest request) {
        publisher.publish(request);
        return response(PAYLOAD_PUBLISHED);
    }

    @GetMapping(path = "/api/payload/all")
    public ResponseEntity<Object> getAllPayload() {
        List<Payload> payloads = publisher.getAllDataFromRepo();
        return response(SUCCESS, payloads);
    }
}
