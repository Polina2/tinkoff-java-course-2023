package ru.tinkoff.edu.java.scrapper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ListAnswersResponse(@JsonProperty List<StackOverflowResponse> items) {
}
