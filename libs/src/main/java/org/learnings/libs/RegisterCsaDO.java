package org.learnings.libs;

public class RegisterCsaDO {
	
	private String csmHostName;
	private String queueName;
	private int csaId;
	private String csaName;
	private String mqHost;
	private Integer mqPort;
	private String mqUser;
	private String mqPassword;
	
	public String getCsmHostName() {
		return csmHostName;
	}
	public void setCsmHostName(String csmHostName) {
		this.csmHostName = csmHostName;
	}
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	public int getCsaId() {
		return csaId;
	}
	public void setCsaId(int csaId) {
		this.csaId = csaId;
	}
	public String getCsaName() {
		return csaName;
	}
	public void setCsaName(String csaName) {
		this.csaName = csaName;
	}
	public String getMqHost() {
		return mqHost;
	}
	public void setMqHost(String mqHost) {
		this.mqHost = mqHost;
	}
		
	public Integer getMqPort() {
		return mqPort;
	}
	public void setMqPort(Integer mqPort) {
		this.mqPort = mqPort;
	}
	public String getMqUser() {
		return mqUser;
	}
	public void setMqUser(String mqUser) {
		this.mqUser = mqUser;
	}
	public String getMqPassword() {
		return mqPassword;
	}
	public void setMqPassword(String mqPassword) {
		this.mqPassword = mqPassword;
	}
	
	
}
