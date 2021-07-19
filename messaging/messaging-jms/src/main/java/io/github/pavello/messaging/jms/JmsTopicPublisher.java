package io.github.pavello.messaging.jms;

import java.util.UUID;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.springframework.jms.support.JmsUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JmsTopicPublisher {

	public static void main(String[] args) {
		new JmsTopicPublisher().run();
	}

	public void run() {
		MessageProducer messageProducer = null;
		JmsConfiguration jmsConfiguration = new JmsConfiguration();

		try {
			String clientId = "myClientId " + UUID.randomUUID().toString();
			jmsConfiguration.init(clientId);

			Session session = jmsConfiguration.getSession();
			Destination destination = session.createTopic("orders");

			messageProducer = session.createProducer(destination);

			Message message = session.createTextMessage("My message!");

			messageProducer.send(message);

			log.info("Message sent!");
		} catch (JMSException e) {
			log.info("Error: " + e.getMessage());
		} finally {
			JmsUtils.closeMessageProducer(messageProducer);
			jmsConfiguration.close();
		}
	}

}
