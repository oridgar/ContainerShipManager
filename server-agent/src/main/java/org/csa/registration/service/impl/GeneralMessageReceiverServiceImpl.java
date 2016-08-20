package org.csa.registration.service.impl;

import java.io.InputStream;
import java.io.OutputStream;

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
	
	
	public static final String CREATE_TEMPLATE = "docker create --name %s -it %s";
	public static final String START_TEMPLATE = "docker start %s";
	public static final String STOP_TEMPLATE = "docker stop %s";
	public static final String REMOVE_TEMPLATE = "docker rm --force %s";
	
	private final Logger logger = LoggerFactory.getLogger(org.csa.registration.service.impl.GeneralMessageReceiverServiceImpl.class);
	
		
	public String receiveMessage(ICommand message) {
		
		ContainerCommandDO containerCommandDO = (ContainerCommandDO)message;
		
		String command = getCommand(containerCommandDO);
		
		logger.info("Command is - " + command);
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
		
		return command;
	}
	
	
	/*public String receiveMessage(Command message) {
		String command = String.format(COMMAND_TEMPLATE, message.getContainerName(), message.getImageName());
		logger.info("Command is - " + command);
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
	}*/
	

	/*@Override
	public String receiveMessage(Command message) {
		String command = String.format("docker run --%s -d %s", message.getContainerName(), message.getImageName());
		StringBuilder ret = new StringBuilder();
		try {
			System.out.println("Command is -- " + command);
			Process proc = Runtime.getRuntime().exec(command);			
			BufferedReader stdInput = new BufferedReader(new 
				     InputStreamReader(proc.getInputStream()));			
			String s = null;
			while ((s = stdInput.readLine()) != null) {
				ret.append (s);
				ret.append(System.lineSeparator());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Command output is --");
		System.out.println(ret.toString());
		return ret.toString();
	}*/
	

//works !	
/*	@Override
	public String receiveMessage(Command message) {
		String command = String.format("docker run --name %s -itd %s", message.getContainerName(), message.getImageName());
		StringBuilder ret = new StringBuilder();
		try {
			System.out.println("Command is -- " + command);
			Process proc = Runtime.getRuntime().exec("/bin/sh");
			OutputStream out = proc.getOutputStream();
		    out.write(command.getBytes());
		    out.close();
			BufferedReader stdInput = new BufferedReader(new 
				     InputStreamReader(proc.getInputStream()));			
			String s = null;
			while ((s = stdInput.readLine()) != null) {
				ret.append (s);
				ret.append(System.lineSeparator());
			}
			
			StringBuilder ret1  = new StringBuilder();
			BufferedReader stdErr = new BufferedReader(new 
				     InputStreamReader(proc.getErrorStream()));			
			String s1 = null;
			while ((s1 = stdErr.readLine()) != null) {
				ret1.append (s1);
				ret1.append(System.lineSeparator());
			}
			System.out.println("Command error is --");
			System.out.println(ret1.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Command output is --");
		System.out.println(ret.toString());
		return ret.toString();
	}*/
	
	

}
