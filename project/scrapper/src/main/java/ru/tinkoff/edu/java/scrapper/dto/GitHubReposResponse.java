package ru.tinkoff.edu.java.scrapper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record GitHubReposResponse(@JsonProperty String name, @JsonProperty("pushed_at") OffsetDateTime updatedAt) {
}
