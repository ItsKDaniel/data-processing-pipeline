package io.falcon.assignment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.falcon.assignment.model.Payload;
import io.falcon.assignment.service.pubsub.RedisPubSubService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class DataPipelineControllerTest {
    private static ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RedisPubSubService mockPublisher;

    @BeforeAll
    static void init() {
        mapper = new ObjectMapper();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(mockPublisher);
    }

    @Test
    public void testPublishSuccess() throws Exception {

        mockMvc.perform(
                post("/api/publish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildPayloadRequest("abrakadabra", "2018-10-09 00:12:12+0100"))
                        .with(request -> request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("status", Matchers.is("201.10")));

        verify(mockPublisher, times(1)).publish(any());

    }

    @Test
    public void testPublishBadRequest_missingcontent() throws Exception {
        mockMvc.perform(
                post("/api/publish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildPayloadRequest(null, "2018-10-09 00:12:12+0100"))
                        .with(request -> request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status", Matchers.is("400.01")))
                .andExpect(jsonPath("content.message", Matchers.is("content must not be blank")));
    }

    @Test
    public void testPublishBadRequest_invalidDateFormat() throws Exception {
        mockMvc.perform(
                post("/api/publish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildPayloadRequest("abc", "2018-10-09 00:12:12"))
                        .with(request -> request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status", Matchers.is("400.05")))
                .andExpect(jsonPath("content.message", Matchers.is("invalid date format")));
    }

    @Test
    public void testGetAllPayloadSuccess() throws Exception {

        when(mockPublisher.getAllDataFromRepo())
                .thenReturn(Collections.singletonList(
                        Payload.builder()
                                .content("abrakadabra")
                                .timestamp("2018-10-08 23:12:12+0000")
                                .longest_palindrome_size(3)
                                .build()
                ));

        mockMvc.perform(
                get("/api/payload/all")
                        .with(request -> request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].content", Matchers.is("abrakadabra")));

        verify(mockPublisher, times(1)).getAllDataFromRepo();
    }

    private String buildPayloadRequest(String content, String timestamp) throws JsonProcessingException {
        return mapper.writeValueAsString(Payload.builder().content(content).timestamp(timestamp).build());
    }
}
