package org.csa.registration.service;

import org.learnings.libs.Command;

public interface GeneralMessageReceiverService {
	public String receiveMessage(Command message);
}
