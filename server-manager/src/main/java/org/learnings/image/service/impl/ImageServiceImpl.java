package org.learnings.image.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.learnings.container.domain.Container;
import org.learnings.container.domain.ContainerImpl;
import org.learnings.container.repository.ContainerRepository;
import org.learnings.container.service.ContainerService;
import org.learnings.image.domain.ImageImpl;
import org.learnings.image.repository.ImageRepository;
import org.learnings.image.service.ImageService;
import org.learnings.libs.Command;
import org.learnings.libs.ContainerCommandDO;
import org.learnings.libs.ContainerStatus;
import org.learnings.libs.ImageCommandDO;
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
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
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
	public ImageImpl getImage(int id) {
		ImageImpl image = imageRepository.findOneById(id); 
		return image;
	}

	@Override
	public List<ImageImpl> getImageList() {
		return imageRepository.findAll();
	}

	@Override
	@Transactional
	public void createImage(ImageImpl details) {
		List<SystemLinux> systemList = systemService.getSystemList();
		
		ImageCommandDO sendCommand = new ImageCommandDO();
		sendCommand.setCommandType(ImageCommandDO.PULL_IMAGE_COMMAND);
		sendCommand.setId(details.getId());
		sendCommand.setName(details.getName());
				 
		for (SystemLinux currSystem : systemList) {
			//sendMqCommand(sendCommand, Integer.toString(currSystem.getId()));
		}
		
		//TODO: Check if this works instead of working directly with RabbitMQ
		//systemService.sendCommand(sendCommand);
		
		imageRepository.save(details);
	}

	@Override
	@Transactional
	public void deleteImage(int id) {
		List<SystemLinux> systemList = systemService.getSystemList();
		
		ImageImpl currImage = this.getImage(id);
		
		ImageCommandDO sendCommand = new ImageCommandDO();
		sendCommand.setCommandType(ImageCommandDO.REMOVE_IMAGE_COMMAND);
		sendCommand.setId(currImage.getId());
		sendCommand.setName(currImage.getName());
		
		for (SystemLinux currSystem : systemList) {
			//sendMqCommand(sendCommand, Integer.toString(currSystem.getId()));
		}
		
		imageRepository.delete(id);
	}
}