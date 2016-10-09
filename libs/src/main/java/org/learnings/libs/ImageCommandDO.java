package org.learnings.libs;

public class ImageCommandDO implements ICommand{
	
	private String commandType;
	private String name;
	private int id;
	
	public static final String CREATE_COMMAND = "CREATE_COMMAND";
	public static final String REMOVE_COMMAND = "REMOVE_COMMAND";
		
	
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
