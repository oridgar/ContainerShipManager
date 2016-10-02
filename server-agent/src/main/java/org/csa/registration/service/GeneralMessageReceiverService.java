package org.csa.registration.service;

import org.learnings.libs.ICommand;

public interface GeneralMessageReceiverService {
	public Object receiveMessage(ICommand message);
}
