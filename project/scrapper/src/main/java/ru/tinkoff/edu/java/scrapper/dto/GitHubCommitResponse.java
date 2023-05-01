package ru.tinkoff.edu.java.scrapper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubCommitResponse(@JsonProperty String sha, @JsonProperty CommitResponse commit) {
}
