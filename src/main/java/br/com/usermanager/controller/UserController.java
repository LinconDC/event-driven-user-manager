package br.com.usermanager.controller;

import br.com.usermanager.model.request.UserRequest;
import br.com.usermanager.model.response.UserResponse;
import br.com.usermanager.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @RequestMapping("/create")
    public Mono<ResponseEntity<UserResponse>> createUser(@RequestBody UserRequest userRequest) {
        log.info("[REQUEST][POST] createUser: {}", userRequest);
        return service.create(userRequest)
                .map(userResponse -> ResponseEntity.status(HttpStatus.CREATED).body(userResponse))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }
}
