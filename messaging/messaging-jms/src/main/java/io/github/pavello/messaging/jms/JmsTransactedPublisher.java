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
public class JmsTransactedPublisher {

	public static void main(String[] args) {
		new JmsTransactedPublisher().run();
	}

	public void run() {
		MessageProducer messageProducer = null;
		JmsConfiguration jmsConfiguration = new JmsConfiguration();
		jmsConfiguration.setSessionTransacted(true);

		try {
			String clientId = "myClientId " + UUID.randomUUID().toString();
			jmsConfiguration.init(clientId);

			Session session = jmsConfiguration.getSession();
			Destination destination = session.createQueue("orders");

			messageProducer = session.createProducer(destination);

			Message message = session.createTextMessage("My message 1");
			Message message1 = session.createTextMessage("My message 2");
			Message message2 = session.createTextMessage("My message 3");
			Message message3 = session.createTextMessage("My message 4");

			messageProducer.send(message);
			messageProducer.send(message1);
			messageProducer.send(message2);
			messageProducer.send(message3);

			session.commit();
			log.info("Messages sent in one transaction!");
		} catch (JMSException e) {
			log.info("Error: " + e.getMessage());
		} finally {
			JmsUtils.closeMessageProducer(messageProducer);
			jmsConfiguration.close();
		}
	}

}
