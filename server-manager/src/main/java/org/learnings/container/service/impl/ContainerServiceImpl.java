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
import org.learnings.libs.ContainerCommandDO;
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
		ContainerCommandDO sendCommand = new ContainerCommandDO();
		sendCommand.setCommandType(ContainerCommandDO.CREATE_COMMAND);
		sendCommand.setId(details.getId());
		sendCommand.setImageName(details.getImage());
		sendCommand.setName(details.getName());
		
		sendMqCommand(sendCommand, details.getServerId());
		
		containerRepository.save(details);
	}

	@Override
	@Transactional
	public void deleteContainer(String id) {
		Container currCont = this.getContainer(id);
		
		ContainerCommandDO sendCommand = new ContainerCommandDO();
		sendCommand.setCommandType(ContainerCommandDO.REMOVE_COMMAND);
		sendCommand.setId(currCont.getId());
		sendCommand.setName(currCont.getName());
		
		sendMqCommand(sendCommand, currCont.getServerId());
		
		containerRepository.delete(id);
	}

	@Override
	public void startContainer(String id) {
		Container currCont = this.getContainer(id);
		
		ContainerCommandDO sendCommand = new ContainerCommandDO();
		sendCommand.setCommandType(ContainerCommandDO.START_COMMAND);
		sendCommand.setId(currCont.getId());
		sendCommand.setName(currCont.getName());
		
		sendMqCommand(sendCommand, currCont.getServerId());
	}

	@Override
	public void stopContainer(String id) {
		Container currCont = this.getContainer(id);
		
		ContainerCommandDO sendCommand = new ContainerCommandDO();
		sendCommand.setCommandType(ContainerCommandDO.STOP_COMMAND);
		sendCommand.setId(currCont.getId());
		sendCommand.setName(currCont.getName());
		
		sendMqCommand(sendCommand, currCont.getServerId());
	}

	@Override
	public void restartContainer(String id) {
		this.stopContainer(id);
		this.startContainer(id);
	}

	
	private String sendMqCommand(Object message, String queueName) {
		String ret = null;
		
		ObjectMapper mapper = new ObjectMapper();
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
			Message retMessage = rabbitTemplate.sendAndReceive("", queueName, mqMessage);
			if (retMessage != null)  {
				ret = new String(retMessage.getBody(), StandardCharsets.UTF_8);
			}
		} catch (AmqpException | IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
}