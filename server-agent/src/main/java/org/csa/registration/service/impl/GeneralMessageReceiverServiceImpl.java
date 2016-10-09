package org.csa.registration.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.csa.registration.service.GeneralMessageReceiverService;
import org.learnings.libs.ContainerCommandDO;
import org.learnings.libs.ContainerStatus;
import org.learnings.libs.ICommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("unchecked")
public class GeneralMessageReceiverServiceImpl implements
		GeneralMessageReceiverService {  
	
	
	public static final String CREATE_TEMPLATE = "docker create --name %s --net='none' -it %s";
	public static final String START_TEMPLATE = "docker start %s";
	public static final String STOP_TEMPLATE = "/usr/local/bin/stop-container %s";
	public static final String REMOVE_TEMPLATE = "docker rm --force %s";
	public static final String GET_STATUS_TEMPLATE = "docker inspect %s";
	public static final String CONTAINER_NW_SETUP_TEMPLATE = "/usr/local/bin/pipework %s %s %s/%s@%s";
	
	
	private Map<String, ContainerCommandDO> containerDetailsMap = new HashMap<String, ContainerCommandDO>();
	
	private final Logger logger = LoggerFactory.getLogger(org.csa.registration.service.impl.GeneralMessageReceiverServiceImpl.class);
	
		
	public Object receiveMessage(ICommand message) {
		ContainerStatus containerStatus = new ContainerStatus();
		
		try{
			ContainerCommandDO containerCommandDO = (ContainerCommandDO)message;
			
			if(!containerCommandDO.getCommandType().equals(ContainerCommandDO.GET_STATUS_COMMAND)){
				String command = getCommand(containerCommandDO);
				String res = executeCommand(command);
				
				if(containerCommandDO.getCommandType().equals(ContainerCommandDO.START_COMMAND)){
					ContainerCommandDO nwSetupContainerCommandDO = containerDetailsMap.get(containerCommandDO.getName());
					nwSetupContainerCommandDO.setCommandType(ContainerCommandDO.CONTAINER_NW_SETUP_COMMAND);
					String nwSetupCommand = getCommand(nwSetupContainerCommandDO);
					res = executeCommand(nwSetupCommand);
				}
			}
			containerStatus = getContainerStatusAfterCommand(containerCommandDO);
				
		}
		catch (Throwable e){
			logger.error(e.getMessage(), e);
		}
		
		return containerStatus;
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
			if(!StringUtils.isEmpty(errStr)){
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
		else if(containerCommandDO.getCommandType().equals(ContainerCommandDO.GET_STATUS_COMMAND)){
			command = String.format(GET_STATUS_TEMPLATE, containerCommandDO.getName());
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
	
	
	private ContainerStatus getContainerStatusAfterCommand(ContainerCommandDO containerCommandDO){
		ContainerStatus containerStatus = new ContainerStatus();
		if(containerCommandDO.getCommandType().equals(ContainerCommandDO.REMOVE_COMMAND)){
			containerStatus.setState(ContainerStatus.REMOVED);
		}
		else{
			ContainerCommandDO containerStatusCommandDO = new ContainerCommandDO();
			containerStatusCommandDO.setName(containerCommandDO.getName());
			containerStatusCommandDO.setCommandType(ContainerCommandDO.GET_STATUS_COMMAND);
			String command = getCommand(containerStatusCommandDO);
			String res = executeCommand(command);
			containerStatus = getStatus(res);
		}
		return containerStatus;
	}
	
	
	private ContainerStatus getStatus(String data){
		ContainerStatus containerStatus = new ContainerStatus();
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			ArrayList<Object> objectsList = mapper.readValue(data, ArrayList.class);	
			
			//upper element
			Map<String, Object> singleObject = (Map<String, Object>)objectsList.get(0);
			containerStatus.setId(singleObject.get("Id").toString());
			containerStatus.setCreatedDate(singleObject.get("Created").toString());
			
			//state element
			Map<String, Object> stateObject = (Map<String, Object>)singleObject.get("State");
			if(Boolean.parseBoolean(stateObject.get("Running").toString())){
				containerStatus.setState(ContainerStatus.RUNNING);
			}
			else if(Boolean.parseBoolean(stateObject.get("Paused").toString())){
				containerStatus.setState(ContainerStatus.PAUSED);
			}
			else {
				containerStatus.setState(ContainerStatus.STOPPED);
			}
			containerStatus.setStartedDate(stateObject.get("StartedAt").toString());
			containerStatus.setFinishedDate(stateObject.get("FinishedAt").toString());
			containerStatus.setExitCode(((Integer)stateObject.get("ExitCode")).toString());
						
			//config element
			Map<String, Object> configObject = (Map<String, Object>)singleObject.get("Config");
			containerStatus.setImageName(configObject.get("Image").toString());
			
			Map<String, Object> exposedPorts = (Map<String, Object>)configObject.get("ExposedPorts");
			if (exposedPorts != null) { 
				String [] ports = new String [exposedPorts.keySet().size()];
				int counter = 0;
				for(String port : exposedPorts.keySet()){
					ports[counter] = port.split("/")[0];
					counter++;
				}
				containerStatus.setUsedPorts(ports);
			}
		} 
		catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return containerStatus;
	}
	
	
	

}
