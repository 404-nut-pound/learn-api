package io.hskim.learnapi.user.service;

import io.hskim.learnapi.user.dto.UserDto;
import io.hskim.learnapi.user.exception.UserNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private static List<UserDto> userList = new ArrayList<>();

  static {
    DateTimeFormatter dtFormatter = DateTimeFormatter.ofLocalizedDateTime(
      FormatStyle.FULL,
      FormatStyle.MEDIUM
    );

    userList.add(
      UserDto
        .builder()
        .id(1)
        .name("A")
        .regTimestamp(dtFormatter.format(LocalDateTime.now().minusDays(2)))
        .build()
    );
    userList.add(
      UserDto
        .builder()
        .id(2)
        .name("B")
        .regTimestamp(dtFormatter.format(LocalDateTime.now().minusDays(1)))
        .build()
    );
    userList.add(
      UserDto
        .builder()
        .id(3)
        .name("C")
        .regTimestamp(dtFormatter.format(LocalDateTime.now()))
        .build()
    );
  }

  public List<UserDto> getUserList() {
    return userList;
  }

  public UserDto getUser(int id) {
    return userList
      .stream()
      .filter(userDto -> userDto.id() == id)
      .findFirst()
      .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다.")
      );
  }
}
