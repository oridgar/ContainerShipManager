package org.learnings.system.service.impl;

import org.learnings.system.domain.SystemLinux;
import org.learnings.system.repository.SystemRepository;
import org.learnings.system.restclient.CsaConnector;
import org.learnings.system.restclient.CsaConnectorFactory;
import org.learnings.system.service.SystemService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import retrofit.client.Response;

import org.springframework.amqp.core.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.annotation.PostConstruct;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.learnings.libs.Command;
import org.learnings.libs.RabbitCommon;
import org.learnings.libs.RegisterCsaDO;
import org.learnings.system.domain.System;

@Service
public class SystemServiceImpl implements SystemService {

	@Autowired
	private SystemRepository systemRepository;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	//@Value("${queue.name}")
	//private String queueName;
	
	@Value("${queue.exchange}")
	private String exchange;
	
	@Value("${spring.rabbitmq.host}")
	private String brokerHostName;
	
	@Override
	public System getSystem(String id) {
		return systemRepository.findOneById(id);
	}

	@Override
	public void createSystem(SystemLinux details) {
		String queueName = details.getId();
		
		//Creating the queue
		RabbitCommon.createEntities(queueName, exchange, queueName);
		
		RegisterCsaDO newCsa = new RegisterCsaDO();
		newCsa.setCsaId(details.getId());
		newCsa.setCsaName(details.getHostname());
		newCsa.setCsmHostName(brokerHostName);
		newCsa.setQueueName(queueName);
		newCsa.setMqHost(brokerHostName);
		newCsa.setMqPort(5672);
		newCsa.setMqUser("guest");
		newCsa.setMqPassword("guest");
		CsaConnector csa = CsaConnectorFactory.getConnector(details.getIp(), "8100");
		Response a = csa.register(newCsa);
		systemRepository.save(details);
	}
	
	@Override
	public void deleteSystem(String id) {
		System details = this.getSystem(id);

	
		CsaConnector csa = CsaConnectorFactory.getConnector(details.getIp(), "8100");
		Response a = csa.unregister();
		
		RabbitCommon.DeleteEntities(id, exchange, id);
		
		systemRepository.delete(id);
	}

	@Override
	public List<SystemLinux> getSystemList() {
		return systemRepository.findAll();
	}

	@Override
	public String getHostname() {
		rabbitTemplate.convertAndSend("","serverManager", "hello!");
		return null;
	}
	
	public String command(System system, Command message) {
		String ret = null;
		ObjectMapper mapper = new ObjectMapper();
		//byte[] a;
		try {
			//Creating properties for the message
			MessageProperties props = new MessageProperties();
			props.setContentType(MessageProperties.CONTENT_TYPE_JSON);
			props.setHeader("__TypeId__", message.getClass().getName());
			props.setContentEncoding("UTF-8");
			rabbitTemplate.setReplyTimeout(20000);
			
			//Creating message with the properties
			Message mqMessage = new Message(mapper.writeValueAsBytes(message), props);
			
			//Sending the message and wait for reply
			Message retMessage = rabbitTemplate.sendAndReceive("", system.getId(), mqMessage);
			if (retMessage != null)  {
				ret = new String(retMessage.getBody(), StandardCharsets.UTF_8);
				//java.lang.System.out.println(ret);
			}
			
			//rabbitTemplate.send("", "agent", mqMessage);
			//rabbitTemplate.convertAndSend("","serverManager", message);
		} catch (AmqpException | IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	@PostConstruct
	private void init() {
		//rabbitTemplate.setExchange(exchange);
		//rabbitTemplate.setQueue(queueName);
		RabbitCommon.setBrokerHostName(brokerHostName);
	}
}
