package org.learnings;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.amqp.support.converter.JsonMessageConverter;

import org.springframework.amqp.core.BindingBuilder;

@SpringBootApplication
public class ServerAgentApplication implements CommandLineRunner {

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Value("${queue.name}")
	String queueName;

	public static void main(String[] args) {
		SpringApplication.run(ServerAgentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String brokerHostName = "localhost";
		String exchangeName = "";
		String serverQueuename = "serverManager";
		String queueName = "agent";
		
		// Managing connections for specific rabbitMQ message broker
		ConnectionFactory cf = new CachingConnectionFactory(brokerHostName);
		
		createEntities(cf, queueName, exchangeName, queueName);
		
		runMessageListener(queueName, exchangeName, brokerHostName);
		//runCustomListener(queuename);
		
	}

	public void createEntities(ConnectionFactory cf,String queueName, String exchangeName, String routingKey) {
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
	
	public void runMessageListener(String queueName, String exchangeName, String brokerHostName) {
		// Managing connections for specific rabbitMQ message broker
		ConnectionFactory cf = new CachingConnectionFactory(brokerHostName);

		JsonMessageConverter converter = new JsonMessageConverter();
				
		// This is a custom class for handling messages when received. you can
		// implement any method name but must set it into the listener
		// which will call this class.
		Receiver receiver = new Receiver();
		receiver.setRabbitTemplate(rabbitTemplate);
		// Tells which method to call when receiving a message.
		
		MessageListenerAdapter listener = new MessageListenerAdapter(receiver);
		listener.setDefaultListenerMethod("receiveMessage");
		listener.setMessageConverter(converter);
		
		
		// This object will listen to specific queue, connect with our
		// connection factory
		// and call our listener adapter when message is received.

		
		//converter.setClassMapper(classMapper);
		
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setMessageListener(listener);
		container.setConnectionFactory(cf);
		container.setQueueNames(queueName);
		
		
		
		// Start listening to the queue
		container.start();
		System.out.println("waiting for messages...");
	}

	public void runCustomListener(String queueName) {
		while (true) {
			Object message;
			while ((message = rabbitTemplate.receiveAndConvert(queueName)) == null) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println(message);
		}
	}
}
