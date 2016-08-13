package org.csa.registration.service.impl;

import org.csa.registration.domain.RegistrationDO;
import org.csa.registration.service.GeneralMessageReceiverService;
import org.csa.registration.service.RegistrationService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private GeneralMessageReceiverService generalMessageReceiverService;

	@Override
	public void register(RegistrationDO registrationDO) {

		JsonMessageConverter converter = new JsonMessageConverter();
		
		MessageListenerAdapter listener = new MessageListenerAdapter(generalMessageReceiverService);
		listener.setDefaultListenerMethod("receiveMessage");
		listener.setMessageConverter(converter);
		
		
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setMessageListener(listener);
		container.setConnectionFactory(rabbitTemplate.getConnectionFactory());
		container.setQueueNames(registrationDO.getQueueName());
		
		container.start();
	}

}
