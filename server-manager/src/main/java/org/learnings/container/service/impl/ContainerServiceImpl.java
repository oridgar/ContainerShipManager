package org.learnings.container.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.learnings.container.domain.Container;
import org.learnings.container.domain.ContainerImpl;
import org.learnings.container.repository.ContainerRepository;
import org.learnings.container.service.ContainerService;
import org.learnings.libs.Command;
import org.learnings.system.repository.SystemRepository;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContainerServiceImpl implements ContainerService {

	@Autowired
	private ContainerRepository containerRepository;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Override
	public Container getContainer(String id) {
		return containerRepository.findOneById(id);
	}

	@Override
	public List<ContainerImpl> getContainerList() {
		return containerRepository.findAll();
	}

	@Override
	@Transactional
	public void createContainer(ContainerImpl details) {
		String ret = null;
		
		Command sendCommand = new Command();
		sendCommand.setCommandName("");
		sendCommand.setContainerName(details.getName());
		sendCommand.setImageName(details.getImage());
		
		
		ObjectMapper mapper = new ObjectMapper();
		//byte[] a;
		try {
			//Creating properties for the message
			MessageProperties props = new MessageProperties();
			props.setContentType(MessageProperties.CONTENT_TYPE_JSON);
			props.setHeader("__TypeId__", sendCommand.getClass().getName());
			props.setContentEncoding("UTF-8");
			rabbitTemplate.setReplyTimeout(20000);
			
			//Creating message with the properties
			Message mqMessage = new Message(mapper.writeValueAsBytes(sendCommand), props);
			
			//Sending the message and wait for reply
			Message retMessage = rabbitTemplate.sendAndReceive("", details.getServerId(), mqMessage);
			if (retMessage != null)  {
				ret = new String(retMessage.getBody(), StandardCharsets.UTF_8);
			}
		} catch (AmqpException | IOException e) {
			e.printStackTrace();
		}
		containerRepository.save(details);
	}

	@Override
	@Transactional
	public void deleteContainer(String id) {
		Container currCont = this.getContainer(id);
		
		String ret = null;
		
		Command sendCommand = new Command();
		sendCommand.setCommandName("");
		sendCommand.setContainerName(currCont.getName());
		sendCommand.setImageName(currCont.getImage());
		
		
		ObjectMapper mapper = new ObjectMapper();
		//byte[] a;
		try {
			//Creating properties for the message
			MessageProperties props = new MessageProperties();
			props.setContentType(MessageProperties.CONTENT_TYPE_JSON);
			props.setHeader("__TypeId__", sendCommand.getClass().getName());
			props.setContentEncoding("UTF-8");
			rabbitTemplate.setReplyTimeout(20000);
			
			//Creating message with the properties
			Message mqMessage = new Message(mapper.writeValueAsBytes(sendCommand), props);
			
			//Sending the message and wait for reply
			Message retMessage = rabbitTemplate.sendAndReceive("", currCont.getServerId(), mqMessage);
			if (retMessage != null)  {
				ret = new String(retMessage.getBody(), StandardCharsets.UTF_8);
			}
		} catch (AmqpException | IOException e) {
			e.printStackTrace();
		}
		
		containerRepository.delete(id);
	}

	@Override
	public void startContainer(String id) {
		//TODO: Send request from MQ to start container
	}

	@Override
	public void stopContainer(String id) {
		//TODO: Send request from MQ to stop container
	}

	@Override
	public void restartContainer(String id) {
		//TODO: Send request from MQ to restart container		
	}

}