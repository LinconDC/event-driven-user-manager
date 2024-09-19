package br.com.usermanager.service;

import br.com.usermanager.event.producer.Producer;
import br.com.usermanager.factory.UserFactory;
import br.com.usermanager.model.request.UserRequest;
import br.com.usermanager.model.response.UserResponse;
import br.com.usermanager.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import static br.com.usermanager.factory.UserFactory.*;

@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final Producer producer;
    private final UserRepository repository;
    private final String EVENT_TYPE_CREATE = "CREATE";
    private final String EVENT_TYPE_FIND = "FIND";

    public UserService(Producer producer, UserRepository repository) {
        this.producer = producer;
        this.repository = repository;
    }

    public Mono<UserResponse> create(UserRequest userRequest) {
        producer.send(userRequest, EVENT_TYPE_FIND);
        return isNewUser(userRequest)
                .flatMap(isNew -> {
                    if (!isNew) {
                        log.info("[NOT][SAVE] user already exists: {}", userRequest);
                        return Mono.empty();
                    }
                    producer.send(userRequest, EVENT_TYPE_CREATE);
                    return repository.save(toUser(userRequest))
                            .doOnNext(user -> log.info("[SAVE][USER] create: {}", userRequest))
                            .map(UserFactory::toResponse);
                });
    }

    private Mono<Boolean> isNewUser(UserRequest userRequest) {
        return repository.findByNameAndAndEmail(userRequest.userName(), userRequest.userEmail())
                .map(user -> false)
                .defaultIfEmpty(true);
    }
}
