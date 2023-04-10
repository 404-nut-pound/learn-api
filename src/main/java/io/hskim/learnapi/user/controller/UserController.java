package io.hskim.learnapi.user.controller;

import io.hskim.learnapi.user.dto.UserDto;
import io.hskim.learnapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<?> postUser(
    @Validated(
      value = { UserDto.ValidGroups.PostGroup.class }
    ) @RequestBody UserDto userDto
  ) {
    return ResponseEntity.ok().body(userService.postUser(userDto));
  }

  @GetMapping
  public ResponseEntity<?> getUserList() {
    return ResponseEntity.ok().body(userService.getUserList());
  }

  @GetMapping(path = "/{id}")
  // @GetMapping(path = "/{id}", params = { "version=1" })
  // @GetMapping(path = "/{id}", headers = { "X-API-VERSION=1" })
  // @GetMapping(path = "/{id}", produces = { "application/vender.appv1+json" })
  public ResponseEntity<?> getUser(@PathVariable int id) {
    return ResponseEntity.ok().body(userService.getUser(id));
  }
}
