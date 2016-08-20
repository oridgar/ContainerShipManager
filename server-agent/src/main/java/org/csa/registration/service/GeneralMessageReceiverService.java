package org.csa.registration.service;

import org.learnings.libs.ICommand;

public interface GeneralMessageReceiverService {
	public String receiveMessage(ICommand message);
}
