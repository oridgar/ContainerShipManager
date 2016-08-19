package org.csa.registration.service;

import org.learnings.libs.RegisterCsaDO;

public interface RegistrationService {
	public void register(RegisterCsaDO registrationDO);
	public void unregister();
}
