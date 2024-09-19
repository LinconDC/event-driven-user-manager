package br.com.usermanager.event.producer;

import br.com.usermanager.model.request.UserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class Producer {

    private final StreamBridge streamBridge;
    private final Logger log = LoggerFactory.getLogger(Producer.class);

    private String HEADER_EVENT_TYPE = "eventType";
    private String HEADER_SOURCE = "source";
    private String SOURCE_TYPE = "user-manager";
    private String OUTPUT = "events-v1";

    public Producer(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public Boolean send(UserRequest user, String eventType) {
        log.info("Sending event to topic events-v1, event: user");
        var message = MessageBuilder.withPayload(user)
                .setHeader(HEADER_EVENT_TYPE, eventType)
                .setHeader(HEADER_SOURCE, SOURCE_TYPE)
                .setHeader(KafkaHeaders.KEY, user.userName().getBytes(StandardCharsets.UTF_8))
                .build();

        return streamBridge.send(OUTPUT, message);
    }
}
