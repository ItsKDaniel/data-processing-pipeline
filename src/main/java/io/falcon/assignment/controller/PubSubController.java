package io.falcon.assignment.controller;

import io.falcon.assignment.model.Payload;
import io.falcon.assignment.service.EventsService;
import io.falcon.assignment.service.RepositoryService;
import io.falcon.assignment.service.impl.PubSubServiceImpl;
import io.falcon.assignment.service.impl.RepositoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class PubSubController {

    private final EventsService<Payload> publisher;
    private final RepositoryService<UUID, Payload> repo;

    @Autowired
    public PubSubController(PubSubServiceImpl publisher, RepositoryServiceImpl repo) {
        this.publisher = publisher;
        this.repo = repo;
    }

    @PostMapping("/publish")
    public ResponseEntity<?> publish(@RequestBody Payload payload) {
        publisher.handle(payload);
        return ResponseEntity.ok("");
    }

    @GetMapping("/")
    public List<Payload> retrieve() {
        return Collections.emptyList();
    }
}
