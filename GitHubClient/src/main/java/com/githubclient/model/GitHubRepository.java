package com.githubclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubRepository(
        @JsonProperty("name") String name,
        @JsonProperty("owner") Owner owner,
        @JsonProperty("fork") boolean fork
) {}