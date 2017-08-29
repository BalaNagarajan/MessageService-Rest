package com.javacircle.soa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.javacircle.soa.model.Message;

/**
 * Interface for the Service implementation
 * 
 * @author N.Bala
 *
 */
@Service
public interface MessageService {

	/**
	 * Override this method to provide implementation to return the list of
	 * messages from the object store
	 * 
	 * @return
	 */
	public List<Message> getMessages();

	/**
	 * Override this method to provide implementation to process the message
	 * based on type / priority and order
	 * 
	 * @param messageType
	 * @return
	 */
	public Message processMessageByType(String messageType);

	/**
	 * Override this method to provide implementation to get the list of
	 * messages based on its type
	 * 
	 * @param messageType
	 * @return
	 */
	public List<Message> getMessagesByType(String messageType);

	public void saveMessages(List<Message> messageList);

}
