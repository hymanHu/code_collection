package com.thornBird.personalArchives.modules.test.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.thornBird.personalArchives.modules.test.service.SendMessage;

@Service
@Profile("dev")
public class SendMessageForDevImpl implements SendMessage {

	@Override
	public String send() {
		return "Send message for dev.";
	}

}
