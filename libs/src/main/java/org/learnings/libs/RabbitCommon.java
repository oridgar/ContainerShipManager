package org.learnings.libs;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

public class RabbitCommon {
	//Managing connections for specific rabbitMQ message broker
	private static ConnectionFactory cf;
	private static String brokerHostName;
	
	public static void setBrokerHostName(String brokerHostName) {
		RabbitCommon.brokerHostName = brokerHostName;
	}
	
	public static void createEntities(String queueName, String exchangeName, String routingKey) {
		if (cf == null) {
			 cf = new CachingConnectionFactory(brokerHostName);
		}
		
		// set up the queue, exchange, binding on the broker
		// Object for managing rabbitMQ entities (exchange, queue, etc.)
		RabbitAdmin admin = new RabbitAdmin(cf);
		Queue queue = new Queue(queueName);
		admin.declareQueue(queue);
		TopicExchange exchange = new TopicExchange(exchangeName);
		admin.declareExchange(exchange);
		admin.declareBinding(
			BindingBuilder.bind(queue).to(exchange).with(queueName));
	}
	
	public static void DeleteEntities(String queueName, String exchangeName, String routingKey) {
		if (cf == null) {
			 cf = new CachingConnectionFactory(brokerHostName);
		}
		
		// set up the queue, exchange, binding on the broker
		// Object for managing rabbitMQ entities (exchange, queue, etc.)
		RabbitAdmin admin = new RabbitAdmin(cf);
		admin.deleteQueue(queueName);		
	}
}
