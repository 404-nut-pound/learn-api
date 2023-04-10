package io.hskim.learnapi.user.controller;

import io.hskim.learnapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  ResponseEntity<?> getUserList() {
    return ResponseEntity.ok().body(userService.getUserList());
  }

  @GetMapping(path = "/{id}")
  ResponseEntity<?> getUser(@PathVariable int id) {
    return ResponseEntity.ok().body(userService.getUser(id));
  }
}
