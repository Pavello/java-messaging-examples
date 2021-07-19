package io.github.pavello.messaging.jms;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;

import org.springframework.jms.support.JmsUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JmsDurableTopicSubscriber {

	public static void main(String[] args) {
		new JmsDurableTopicSubscriber().run();
	}

	public void run() {
		MessageConsumer messageConsumer = null;
		JmsConfiguration jmsConfiguration = new JmsConfiguration();

		try {
			String clientId = "myClientId " + UUID.randomUUID().toString();
			jmsConfiguration.init(clientId);

			Session session = jmsConfiguration.getSession();
			Topic destination = session.createTopic("orders");

			messageConsumer = session.createDurableSubscriber(destination, "durable-subscription");
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
