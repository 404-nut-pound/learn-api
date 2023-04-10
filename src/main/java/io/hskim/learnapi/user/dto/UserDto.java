package io.hskim.learnapi.user.dto;

import lombok.Builder;

@Builder
public record UserDto(int id, String name, String regTimestamp) {}
