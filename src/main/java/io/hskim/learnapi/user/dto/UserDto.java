package io.hskim.learnapi.user.dto;

import javax.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserDto(
  int id,
  @Size(min = 2, message = "{user.name.min-size}") String name,
  String regTimestamp
) {}
