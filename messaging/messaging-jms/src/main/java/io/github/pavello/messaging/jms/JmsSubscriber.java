package io.github.pavello.messaging.jms;

import java.util.UUID;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.springframework.jms.support.JmsUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JmsSubscriber {

	public static void main(String[] args) {
		new JmsSubscriber().run();
	}

	public void run() {
		MessageConsumer messageConsumer = null;
		JmsConfiguration jmsConfiguration = new JmsConfiguration();

		try {
			String clientId = "myClientId " + UUID.randomUUID().toString();
			jmsConfiguration.init(clientId);

			Session session = jmsConfiguration.getSession();
			Destination destination = session.createQueue("orders");

			messageConsumer = session.createConsumer(destination);
			messageConsumer.setMessageListener(new Listener());

			log.info("Keep listening ...");
			TimeUtils.doInfiniteLoop();
		} catch (JMSException e) {
			log.info("Error: " + e.getMessage());
		} finally {
			JmsUtils.closeMessageConsumer(messageConsumer);
			jmsConfiguration.close();
		}
	}

	@Slf4j
	@RequiredArgsConstructor
	static class Listener implements MessageListener {
		private String clientId;

		@Override
		public void onMessage(final Message message) {
			log.info("Client with id " + clientId + " received message: " + message);
		}
	}
}
