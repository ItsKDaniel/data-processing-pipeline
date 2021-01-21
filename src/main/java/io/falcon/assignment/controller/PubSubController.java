package io.falcon.assignment.controller;

import io.falcon.assignment.model.Payload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class PubSubController {
    @PostMapping("/")
    public ResponseEntity<?> publish(@RequestBody Payload payload) {
        return ResponseEntity.ok("");
    }

    @GetMapping("/")
    public List<Payload> retrieve() {
        return Collections.emptyList();
    }
}
