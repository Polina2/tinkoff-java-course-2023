package ru.tinkoff.edu.java.scrapper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record StackOverflowResponse(@JsonProperty Integer answer_id, @JsonProperty OffsetDateTime creation_date, @JsonProperty OffsetDateTime last_edit_date) {
}
