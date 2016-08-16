package org.csa.registration.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.csa.registration.service.GeneralMessageReceiverService;
import org.learnings.libs.Command;
import org.springframework.stereotype.Service;

@Service
public class GeneralMessageReceiverServiceImpl implements
		GeneralMessageReceiverService {  
	
	
	public static final String COMMAND_TEMPLATE = "docker run --name %s -itd %s";
	
		
	public String receiveMessage(Command message) {
		String command = String.format(COMMAND_TEMPLATE, message.getContainerName(), message.getImageName());
		System.out.println("Command is - " + command);
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
			System.out.println("Command output is -- " + outStr);
			
			errorStream = proc.getErrorStream();
			String errStr = IOUtils.toString(errorStream);
			System.out.println("Command error is -- " + errStr);
			
			res = outStr;
			if(StringUtils.isEmpty(res)){
				res = errStr;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{			
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(errorStream);
		}
		return res;
	}
	

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
