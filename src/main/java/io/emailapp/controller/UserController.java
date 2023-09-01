package io.emailapp.controller;

import io.emailapp.dao.entity.UserEntity;
import io.emailapp.model.dto.HttpResponse;
import io.emailapp.service.concrete.UserServiceHandler;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static io.emailapp.util.HttpResponseUtil.HTTP_RESPONSE_UTIL;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserController {

    UserServiceHandler userServiceHandler;

    @PostMapping
    public ResponseEntity<HttpResponse> createUser(@RequestBody UserEntity user) {
        var newUser = userServiceHandler.saveUser(user);

        return ResponseEntity.created(URI.create("")).body(
                HTTP_RESPONSE_UTIL.buildHttpResponse(
                        newUser,
                        "User created",
                        "user", CREATED)
        );
    }

    @GetMapping
    public ResponseEntity<HttpResponse> confirmUserAccount(@RequestParam("token") String token) {
        var isSuccess = userServiceHandler.verifyToken(token);

        return ResponseEntity.created(URI.create("")).body(
                HTTP_RESPONSE_UTIL.buildHttpResponse(
                        isSuccess,
                        "Account verified",
                        "success",
                        OK)
        );
    }
}
