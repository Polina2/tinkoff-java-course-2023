package ru.tinkoff.edu.java.scrapper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record StackOverflowResponse(
        @JsonProperty Integer answerId, @JsonProperty OffsetDateTime creationDate,
        @JsonProperty("last_activity_date") OffsetDateTime lastActivityDate
) {
}
