package org.learnings.libs;

public class ImageStatus {
	
	private String id;
	private String name;
	private String createdDate;
	private String state;
	
	public static final String CREATED = "CREATED";
	public static final String REMOVED = "REMOVED";
	
	public ImageStatus() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "ImageStatus [id=" + id + ", name=" + name + ", createdDate="
				+ createdDate + ", state=" + state + "]";
	}
	
	
	
	

}
