package com.javacircle.soa.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.javacircle.soa.model.Message;
import com.javacircle.soa.model.MessageType;
import com.javacircle.soa.service.MessageService;
import com.javacircle.soa.util.MessageUtil;

/**
 * 
 * This class provides the implementation for Message processing. It performs
 * various operations like Messages retrieval / storing the user messages in
 * memory / process or delete the message based on its type / priority then by
 * order it entered the queue.
 * 
 * @author N.Bala
 *
 */

@Component
public class MessageServiceImpl implements MessageService {

	private static ConcurrentHashMap<String, List<Message>> messageMap = new ConcurrentHashMap<String, List<Message>>();

	/**
	 * Util class auto injected
	 */
	@Autowired
	private MessageUtil messageUtil;

	/**
	 * Logger object to log the application related messages
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<Message> getMessages() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method stores the list of messages in the memory. It appends the
	 * messsages to the existing list to the corresponding message type.
	 * 
	 * This method uses the java8 'groupingby' to group based on MessageType.
	 * 
	 */
	public void saveMessages(List<Message> messageList) {
		// Initializing the list to store the old and newer messages
		List<Message> initialMessageList = new ArrayList<Message>();
		try {
			// Iterating the Map to get the existing list
			for (String key : MessageServiceImpl.messageMap.keySet()) {
				if (MessageServiceImpl.messageMap.get(key) != null
						&& MessageServiceImpl.messageMap.get(key).size() > 0) {
					// Adding all the existing list based on key
					initialMessageList.addAll(MessageServiceImpl.messageMap.get(key));
				}
			}

			// Checking the list size
			if (messageList != null && messageList.size() > 0) {
				// Adding the new list
				initialMessageList.addAll(messageList);
			}

			// Grouping the list based on Message Type using stream -
			// 'groupingBy'
			Map<String, List<Message>> msgTypeMap = initialMessageList.stream()
					.collect(Collectors.groupingBy(Message::getMessageType));
			MessageServiceImpl.messageMap.putAll(msgTypeMap);
		} catch (Exception e) {
			logger.debug("Exception In" + this.getClass().getName() + "Method:saveMessages" + e.getMessage());
		}
	}

	/**
	 * This method retrieves the message based on the given message type then
	 * based on priority and order (oldest)
	 */
	@Override
	public Message processMessageByType(String messageType) {
		Message messageObj = null;
		List<Message> messageList = null;
		try {
			if (!StringUtils.isEmpty(messageType)) {
				logger.debug("Message Type In" + this.getClass() + "processMessageByType():->" + messageType);
				if (MessageServiceImpl.messageMap != null && !MessageServiceImpl.messageMap.isEmpty()) {
					messageList = messageMap.get(messageType);
					if (messageList != null && messageList.size() > 0) {
						int pointer = messageUtil.getCandidateMessage(messageList);
						messageObj = messageList.get(pointer);
						messageList.remove(pointer);
						MessageServiceImpl.messageMap.put(messageType, messageList);
						logger.debug("Removed Message from the Queue" + messageObj);
					}
				}
			}
		} catch (Exception e) {
			logger.debug("Exception In" + this.getClass().getName() + "Method:processMessageByType" + e.getMessage());
		}
		return messageObj;
	}

	/**
	 * This method returns the list of messages based on given message type
	 */
	@Override
	public List<Message> getMessagesByType(String messageType) {
		List<Message> messageList = null;

		try {
			if (!StringUtils.isEmpty(messageType)) {
				if (MessageServiceImpl.messageMap != null && !MessageServiceImpl.messageMap.isEmpty()) {
					messageList = messageMap.get(messageType);
				} else {
					logger.debug("Message List is empty for the type:->" + messageType);
				}

			}
		} catch (Exception e) {
			logger.debug("Exception In" + this.getClass().getName() + "Method:getMessagesByType" + e.getMessage());
		}

		// TODO Auto-generated method stub
		return messageList;
	}

}
