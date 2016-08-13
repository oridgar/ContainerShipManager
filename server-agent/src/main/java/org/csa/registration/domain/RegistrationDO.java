package org.csa.registration.domain;

public class RegistrationDO {
	
	private String csmHostName;
	private String queueName;
	private String csaId;
	private String csaName;
	
	public RegistrationDO() {
		super();
	}

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

	public String getCsaId() {
		return csaId;
	}

	public void setCsaId(String csaId) {
		this.csaId = csaId;
	}

	public String getCsaName() {
		return csaName;
	}

	public void setCsaName(String csaName) {
		this.csaName = csaName;
	}
	
	
}
