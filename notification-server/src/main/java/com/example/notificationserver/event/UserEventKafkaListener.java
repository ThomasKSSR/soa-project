package com.example.notificationserver.event;

import com.example.notificationserver.event.UserEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventKafkaListener {

    @KafkaListener(topics = "${app.kafka.topic}", groupId = "notification-service")
    public void listen(UserEvent event) {
        System.out.println("KAFKA received event: " + event.type() + " user=" + event.username());
    }
}
