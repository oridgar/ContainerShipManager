package org.csa.registration.service.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.csa.registration.service.GeneralMessageReceiverService;
import org.learnings.libs.ContainerCommandDO;
import org.learnings.libs.ICommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GeneralMessageReceiverServiceImpl implements
		GeneralMessageReceiverService {  
	
	
	public static final String CREATE_TEMPLATE = "docker create --name %s --net='none' -it %s";
	public static final String START_TEMPLATE = "docker start %s";
	public static final String STOP_TEMPLATE = "/usr/local/bin/stop-container %s";
	public static final String REMOVE_TEMPLATE = "docker rm --force %s";
	public static final String CONTAINER_NW_SETUP_TEMPLATE = "/usr/local/bin/pipework %s %s %s/%s@%s";
	
	
	private Map<String, ContainerCommandDO> containerDetailsMap = new HashMap<String, ContainerCommandDO>();
	
	private final Logger logger = LoggerFactory.getLogger(org.csa.registration.service.impl.GeneralMessageReceiverServiceImpl.class);
	
		
	public String receiveMessage(ICommand message) {
		
		ContainerCommandDO containerCommandDO = (ContainerCommandDO)message;
		
		String command = getCommand(containerCommandDO);
		String res = executeCommand(command);
		
		if(containerCommandDO.getCommandType().equals(ContainerCommandDO.START_COMMAND)){
			ContainerCommandDO nwSetupContainerCommandDO = containerDetailsMap.get(containerCommandDO.getName());
			nwSetupContainerCommandDO.setCommandType(ContainerCommandDO.CONTAINER_NW_SETUP_COMMAND);
			String nwSetupCommand = getCommand(nwSetupContainerCommandDO);
			res = executeCommand(nwSetupCommand);
		}		
		
		return res;
	}
	
	
	private String executeCommand(String command){
		OutputStream outputStream = null;
		InputStream inputStream = null;
		InputStream errorStream = null;
		String res = null;
		try {
			
			Process proc = Runtime.getRuntime().exec("/bin/bash");
			outputStream = proc.getOutputStream();
			IOUtils.write(command.getBytes(), outputStream);
			IOUtils.closeQuietly(outputStream);
			
			inputStream = proc.getInputStream();
			String outStr = IOUtils.toString(inputStream);
			logger.info("Command output is - " + outStr);
			
			errorStream = proc.getErrorStream();
			String errStr = IOUtils.toString(errorStream);
			logger.info("Command error is - " + errStr);
			
			res = outStr;
			if(StringUtils.isEmpty(res)){
				res = errStr;
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally{			
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(errorStream);
		}
		return res;
	}
	
	
	private String getCommand(ContainerCommandDO containerCommandDO){
		String command = null;
		
		if(containerCommandDO.getCommandType().equals(ContainerCommandDO.CREATE_COMMAND)){
			containerDetailsMap.put(containerCommandDO.getName(), containerCommandDO);
			command = String.format(CREATE_TEMPLATE, containerCommandDO.getName(), containerCommandDO.getImageName());
		}
		else if(containerCommandDO.getCommandType().equals(ContainerCommandDO.START_COMMAND)){
			command = String.format(START_TEMPLATE, containerCommandDO.getName());
		}
		else if(containerCommandDO.getCommandType().equals(ContainerCommandDO.STOP_COMMAND)){
			command = String.format(STOP_TEMPLATE, containerCommandDO.getName());
		}
		else if(containerCommandDO.getCommandType().equals(ContainerCommandDO.REMOVE_COMMAND)){
			command = String.format(REMOVE_TEMPLATE, containerCommandDO.getName());
		}
		else if(containerCommandDO.getCommandType().equals(ContainerCommandDO.CONTAINER_NW_SETUP_COMMAND)){
			command = String.format(CONTAINER_NW_SETUP_TEMPLATE, "vSwitch0", 
														containerCommandDO.getName(), 
															containerCommandDO.getIp(), 
																containerCommandDO.getNetmask(), 
																	containerCommandDO.getGateway());
		}
		
		return command;
	}
	
	
	

}
