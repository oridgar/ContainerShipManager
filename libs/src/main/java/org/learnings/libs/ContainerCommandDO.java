package org.learnings.libs;

public class ContainerCommandDO implements ICommand{
	private String commandType;
	private String name;
	private String id;
	private String imageName;
	private String ip;
	
	public static final String CREATE_COMMAND = "CREATE_COMMAND";
	public static final String START_COMMAND = "START_COMMAND";
	public static final String STOP_COMMAND = "STOP_COMMAND";
	public static final String REMOVE_COMMAND = "REMOVE_COMMAND";
	
	public String getCommandType() {
		return commandType;
	}
	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
