package io.github.pavello.messaging.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.support.JmsUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JmsConfiguration {

	@Setter
	boolean sessionTransacted = false;
	@Setter
	int sessionAckMode = Session.AUTO_ACKNOWLEDGE;

	private ConnectionFactory connectionFactory;
	private Connection connection;
	@Setter
	@Getter
	private Session session;

	public void init(String clientId) throws JMSException {
		connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		connection = connectionFactory.createConnection();
		connection.setClientID(clientId);
		connection.start();

		session = connection.createSession(sessionTransacted, sessionAckMode);
	}

	public void close() {
		JmsUtils.closeSession(session);
		JmsUtils.closeConnection(connection);
	}
}
