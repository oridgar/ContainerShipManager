package org.csa.registration.useful;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.learnings.libs.Command;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

//test commit #1
public class Receiver {

	//private CountDownLatch latch = new CountDownLatch(1);

	private RabbitTemplate rabbitTemplate;
	
	public RabbitTemplate getRabbitTemplate() {
		return rabbitTemplate;
	}



	public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}



	public String receiveMessage(Command message) {
		StringBuilder ret = new StringBuilder();
		//ObjectMapper mapper = new ObjectMapper();
		try {
			Command receivedCommand = message;
			//Command receivedCommand = mapper.readValue((String)message, Command.class);
			System.out.println("Received <" + receivedCommand.getCommandName() + ">");
			Process proc = Runtime.getRuntime().exec(receivedCommand.getCommandName());
//			try {
//				proc.wait(10000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			BufferedReader stdInput = new BufferedReader(new 
				     InputStreamReader(proc.getInputStream()));
			
//			MessageProperties props = new MessageProperties();
//			//props.setContentType("application/json");
//			props.setHeader("__TypeId__", message.getClass().getName());

			String s = null;
			while ((s = stdInput.readLine()) != null) {
//				Message mqMessage = new Message(s.getBytes(), props);
//				rabbitTemplate.send("", "serverManager", mqMessage);
				ret.append (s);
				ret.append(System.lineSeparator());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//latch.countDown();
		
		System.out.println(ret.toString());
		
		byte[] retbytes = null;
		//Command retCommand  = new Command();
		//retCommand.setCommandName(ret.toString());
		//retbytes = ret.toString().getBytes(StandardCharsets.UTF_8);

		//return retbytes;
		return ret.toString();
	}


}
