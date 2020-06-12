package com.thornBird.personalArchives.modules.test.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.thornBird.personalArchives.modules.test.service.SendMessage;

@Service
@Profile("live")
public class SendMessageForLiveImpl implements SendMessage {

	@Override
	public String send() {
		return "Send message for live.";
	}

}
