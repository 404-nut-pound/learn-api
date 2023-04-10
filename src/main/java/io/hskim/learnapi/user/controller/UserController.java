package io.hskim.learnapi.user.controller;

import io.hskim.learnapi.user.dto.UserDto;
import io.hskim.learnapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
  // @GetMapping(path = "/{id}", params = { "version=1" }) //query string에 추가
  // @GetMapping(path = "/{id}", headers = { "X-API-VERSION=1" }) //header에 추가
  // @GetMapping(path = "/{id}", produces = { "application/vnd.company.appv1+json" }) //header accept에 추가
  public EntityModel<?> getUser(@PathVariable int id) {
    UserDto user = userService.getUser(id);

    return EntityModel.of(
      user,
      WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUser(id))
        .withSelfRel(),
      WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserList())
        .withRel("user-list")
    );
  }
}
