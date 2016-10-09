package org.learnings.libs;

import java.util.Arrays;

public class ContainerStatus {
	private String state;
	private String id;
	private String imageName;
	private String createdDate;
	private String startedDate;
	private String finishedDate;
	private String exitCode;
	private String ip;
	private Integer numCpu;
	private String memorySize;
	private String[] usedPorts;
	private String   envVars;
	private String   cmd;
		
	public static final String RUNNING = "RUNNING";
	public static final String STOPPED = "STOPPED";
	public static final String PAUSED = "PAUSED";
	public static final String REMOVED = "REMOVED";
	
	public ContainerStatus() {
		super();
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getStartedDate() {
		return startedDate;
	}

	public void setStartedDate(String startedDate) {
		this.startedDate = startedDate;
	}

	public String getFinishedDate() {
		return finishedDate;
	}

	public void setFinishedDate(String finishedDate) {
		this.finishedDate = finishedDate;
	}

	public String getExitCode() {
		return exitCode;
	}

	public void setExitCode(String exitCode) {
		this.exitCode = exitCode;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getNumCpu() {
		return numCpu;
	}

	public void setNumCpu(Integer numCpu) {
		this.numCpu = numCpu;
	}

	public String getMemorySize() {
		return memorySize;
	}

	public void setMemorySize(String memorySize) {
		this.memorySize = memorySize;
	}

	public String[] getUsedPorts() {
		return usedPorts;
	}

	public void setUsedPorts(String[] usedPorts) {
		this.usedPorts = usedPorts;
	}

	public String getEnvVars() {
		return envVars;
	}

	public void setEnvVars(String envVars) {
		this.envVars = envVars;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	@Override
	public String toString() {
		return "ContainerStatus [state=" + state + ", id=" + id
				+ ", imageName=" + imageName + ", createdDate=" + createdDate
				+ ", startedDate=" + startedDate + ", finishedDate="
				+ finishedDate + ", usedPorts=" + Arrays.toString(usedPorts)
				+ "]";
	}
	
	
	
}