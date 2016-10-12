package org.learnings.libs;

public class ImageCommandDO implements ICommand{
	
	private String commandType;
	private String name;
	private int id;
	
	public static final String PULL_IMAGE_COMMAND = "PULL_IMAGE_COMMAND";
	public static final String REMOVE_IMAGE_COMMAND = "REMOVE_IMAGE_COMMAND";
	public static final String GET_IMAGE_STATUS_COMMAND = "GET_IMAGE_STATUS_COMMAND";
		
	
	public ImageCommandDO() {
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
}
