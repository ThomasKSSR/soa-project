package com.example.notificationserver.listener;


import com.example.notificationserver.config.RabbitConfig;
import com.example.notificationserver.event.UserRegisteredEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserRegisteredListener {

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void handle(UserRegisteredEvent event) {
        System.out.println(
                "📨 Notification-service received user registration: "
                        + event.username()
        );
    }
}
