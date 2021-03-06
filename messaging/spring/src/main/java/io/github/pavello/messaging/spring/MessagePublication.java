package io.github.pavello.messaging.spring;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.pavello.messaging.spring.dto.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.Collections.singletonMap;

@RestController
@RequestMapping("/orders")
@Slf4j
@RequiredArgsConstructor
public class MessagePublication {

	private final JmsTemplate jmsTemplate;

	@PostMapping
	public ResponseEntity create() {
		var orderId = UUID.randomUUID();
//		var message = "Message: " + orderId;
		var message = new Order();
		message.setId(orderId.toString());
		message.setDescription("Description");

		log.info("Sending message  with orderId {}", orderId);
		jmsTemplate.convertAndSend("orders", message);

		return createResponse(orderId);
	}

	private ResponseEntity createResponse(UUID orderId) {
		return ResponseEntity.accepted().body(singletonMap("orderId", orderId.toString()));
	}
}
