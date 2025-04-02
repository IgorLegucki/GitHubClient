package com.githubclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Owner(@JsonProperty("login") String login) {}

