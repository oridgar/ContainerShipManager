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
import org.learnings.libs.ContainerStatus;
import org.learnings.system.domain.SystemLinux;
import org.learnings.system.repository.SystemRepository;
import org.learnings.system.service.SystemService;
import org.learnings.system.service.impl.SystemServiceAmqpImpl;
import org.learnings.users.service.UserService;
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
	private SystemService systemService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Override
	public ContainerImpl getContainer(int id) {
		ContainerImpl container = containerRepository.findOneById(id); 
		return container;
	}

	@Override
	public List<ContainerImpl> getContainerList() {
		return containerRepository.findAll();
	}

	@Override
	@Transactional
	public void createContainer(ContainerImpl details) {
		SystemLinux containerServer = systemService.getSystem(details.getServerId());
		
		details.setServer(containerServer);
		details.setUser(userService.getUser(details.getUserId()));;
		//details.getServer().addContainer(details);
		
		ContainerCommandDO sendCommand = new ContainerCommandDO();
		sendCommand.setCommandType(ContainerCommandDO.CREATE_COMMAND);
		sendCommand.setId(details.getId());
		sendCommand.setImageName(details.getImage());
		sendCommand.setName(details.getName());
		sendCommand.setIp(details.getIp());
		sendCommand.setNetmask(details.getNetmask());
		sendCommand.setGateway(details.getGateway());
				 
		sendMqCommand(sendCommand, Integer.toString(details.getServerId()));
		
		//TODO: Check if this works instead of working directly with RabbitMQ
		//systemService.sendCommand(sendCommand);
		
		containerRepository.save(details);
	}

	@Override
	@Transactional
	public void deleteContainer(int id) {
		ContainerImpl currCont = this.getContainer(id);
		
		ContainerCommandDO sendCommand = new ContainerCommandDO();
		sendCommand.setCommandType(ContainerCommandDO.REMOVE_COMMAND);
		sendCommand.setId(currCont.getId());
		sendCommand.setName(currCont.getName());
		
		sendMqCommand(sendCommand, Integer.toString(currCont.getServer().getId()));
		
		containerRepository.delete(id);
	}

	@Override
	public void startContainer(int id) {
		ContainerImpl currCont = this.getContainer(id);
		
		ContainerCommandDO sendCommand = new ContainerCommandDO();
		sendCommand.setCommandType(ContainerCommandDO.START_COMMAND);
		sendCommand.setId(currCont.getId());
		sendCommand.setName(currCont.getName());
		sendCommand.setIp(currCont.getIp());
		sendCommand.setNetmask(currCont.getNetmask());
		sendCommand.setGateway(currCont.getGateway());
		
		sendMqCommand(sendCommand, Integer.toString(currCont.getServer().getId()));
	}

	@Override
	public void stopContainer(int id) {
		ContainerImpl currCont = this.getContainer(id);
		
		ContainerCommandDO sendCommand = new ContainerCommandDO();
		sendCommand.setCommandType(ContainerCommandDO.STOP_COMMAND);
		sendCommand.setId(currCont.getId());
		sendCommand.setName(currCont.getName());
		
		sendMqCommand(sendCommand, Integer.toString(currCont.getServer().getId()));
	}

	@Override
	public void restartContainer(int id) {
		ContainerImpl currCont = this.getContainer(id);
		
		this.stopContainer(id);
		this.startContainer(id);
	}

	
	private ContainerStatus sendMqCommand(Object message, String queueName) {
		ContainerStatus status = null;
		
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
				//ret = new String(retMessage.getBody(), StandardCharsets.UTF_8);
				ObjectMapper objectMapper = new ObjectMapper();
				status = objectMapper.readValue(retMessage.getBody(), ContainerStatus.class);
			}
		} catch (AmqpException | IOException e) {
			e.printStackTrace();
		}
		return status;
	}

	private String sendMqCommandAsync(Object message, String queueName) {
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
			
			//Sending the message
			rabbitTemplate.send("", queueName, mqMessage);
		} catch (AmqpException | IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	@Override	
	public ContainerStatus getContainerStatus(int id) {
	 	ContainerImpl container = this.getContainer(id);

		ContainerCommandDO sendCommand = new ContainerCommandDO();
		sendCommand.setCommandType(ContainerCommandDO.GET_STATUS_COMMAND);
		sendCommand.setId(container.getId());
		sendCommand.setName(container.getName());
		
		ContainerStatus status = sendMqCommand(sendCommand, Integer.toString(container.getServer().getId()));
		//ContainerStatus result 
		
	 	return status;
	}
}