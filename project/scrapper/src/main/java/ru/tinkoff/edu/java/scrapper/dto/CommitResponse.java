package ru.tinkoff.edu.java.scrapper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CommitResponse(@JsonProperty String message) {
}
