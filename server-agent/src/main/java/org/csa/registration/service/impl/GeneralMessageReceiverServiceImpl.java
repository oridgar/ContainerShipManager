package org.csa.registration.service.impl;

import org.csa.registration.service.GeneralMessageReceiverService;
import org.learnings.libs.Command;
import org.springframework.stereotype.Service;

@Service
public class GeneralMessageReceiverServiceImpl implements
		GeneralMessageReceiverService {

	@Override
	public String receiveMessage(Command message) {
		String res = "Image - " + message.getImageName() + " , Container - " + message.getContainerName();
		return res;
	}

}
