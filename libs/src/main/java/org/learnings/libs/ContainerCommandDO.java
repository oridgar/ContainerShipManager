package org.learnings.libs;

public class ContainerCommandDO implements ICommand{
	
	private String commandType;
	private String name;
	private int id;
	private String imageName;
	private String ip;
	private String netmask;
	private String gateway;
	
	public static final String CREATE_COMMAND = "CREATE_COMMAND";
	public static final String START_COMMAND = "START_COMMAND";
	public static final String STOP_COMMAND = "STOP_COMMAND";
	public static final String REMOVE_COMMAND = "REMOVE_COMMAND";
	public static final String CONTAINER_NW_SETUP_COMMAND = "CONTAINER_NW_SETUP_COMMAND";
	public static final String GET_STATUS_COMMAND = "GET_STATUS_COMMAND";
		
	
	public ContainerCommandDO() {
		super();
	}
	
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public String getNetmask() {
		return netmask;
	}
	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	
	
}
