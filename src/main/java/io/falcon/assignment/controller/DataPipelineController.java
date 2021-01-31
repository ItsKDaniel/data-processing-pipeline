package io.falcon.assignment.controller;

import io.falcon.assignment.model.Payload;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
public class DataPipelineController {

    @PostMapping(path = "/api/publish", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void publishPayload(@Valid @RequestBody Payload payload) {

    }
}
