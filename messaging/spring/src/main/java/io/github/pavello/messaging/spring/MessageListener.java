package io.github.pavello.messaging.spring;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MessageListener {

	@JmsListener(destination = "orders")
	public void onMessage(Message<Object> message) {
		log.info("I got a message from queue orders: {}", message.getPayload());
	}
}
