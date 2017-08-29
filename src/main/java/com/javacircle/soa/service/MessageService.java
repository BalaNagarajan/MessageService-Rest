package com.javacircle.soa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.javacircle.soa.model.Message;

@Service
public interface MessageService {

	public List<Message> getMessages();

	public void storeMessages(List<Message> messageList);

	public Message processMessageByType(String messageType);

	public List<Message> getMessagesByType(String messageType);

}
