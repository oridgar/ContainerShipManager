package org.csa.registration.service.impl;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.csa.registration.domain.RegistrationDO;
import org.csa.registration.service.GeneralMessageReceiverService;
import org.csa.registration.service.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private GeneralMessageReceiverService generalMessageReceiverService;
	
	@Value("${csa.ext-config-location}")
    private String externalConfigLocation;
	
	private SimpleMessageListenerContainer container;

	private RegistrationDO registrationDO;	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@PostConstruct
	public void init(){
		registrationDO = getConfig();
		if(registrationDO != null){
			register(registrationDO, false);
		}
	}

	@Override
	public void register(RegistrationDO registrationDO) {		
		register(registrationDO, true);
	}
	
	
	private void register(RegistrationDO registrationDO, boolean saveConfig) {		

		JsonMessageConverter converter = new JsonMessageConverter();
		
		MessageListenerAdapter listener = new MessageListenerAdapter(generalMessageReceiverService);
		listener.setDefaultListenerMethod("receiveMessage");
		listener.setMessageConverter(converter);
		
		
		container = new SimpleMessageListenerContainer();
		container.setMessageListener(listener);
		container.setConnectionFactory(rabbitTemplate.getConnectionFactory());
		container.setQueueNames(registrationDO.getQueueName());
		
		container.start();
		if(saveConfig){
			saveConfig(registrationDO);
		}
	}
	
	
	private void saveConfig(RegistrationDO registrationDO){
		
		Properties props = new Properties();
		props.setProperty("queueName", registrationDO.getQueueName());
		props.setProperty("csmHostName", registrationDO.getCsmHostName());
		props.setProperty("csaId", registrationDO.getCsaId());
		props.setProperty("csaName", registrationDO.getCsaName());
		
		OutputStream os = null;
		try {
			os = FileUtils.openOutputStream
			  (new File(externalConfigLocation));
			props.store(os, null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
            IOUtils.closeQuietly(os);
        }

	}
	
	
	private RegistrationDO getConfig(){
		RegistrationDO registrationDO = null;
		InputStream is = null;
		try {
			File f = new File(externalConfigLocation);
			FileUtils.touch(f);
			is = FileUtils.openInputStream(f);
			Properties props = new Properties();
			props.load(is);
			if(StringUtils.isNotEmpty(props.getProperty("queueName"))){
				registrationDO = new RegistrationDO();
				registrationDO.setQueueName(props.getProperty("queueName"));
				registrationDO.setCsmHostName(props.getProperty("csmHostName"));
				registrationDO.setCsaId(props.getProperty("csaId"));
				registrationDO.setCsaName(props.getProperty("csaName"));
				register(registrationDO);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
            IOUtils.closeQuietly(is);
        }
		return registrationDO;
	}
	

	public RegistrationDO getRegistrationDO() {
		return registrationDO;
	}

	public void setRegistrationDO(RegistrationDO registrationDO) {
		this.registrationDO = registrationDO;
	}
	
	

}
