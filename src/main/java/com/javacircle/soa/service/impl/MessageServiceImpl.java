package com.javacircle.soa.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.javacircle.soa.model.Message;
import com.javacircle.soa.model.MessageType;
import com.javacircle.soa.service.MessageService;
import com.javacircle.soa.util.MessageUtil;

@Component
public class MessageServiceImpl implements MessageService {

	private static ConcurrentHashMap<String, List<Message>> messageMap = new ConcurrentHashMap<String, List<Message>>();

	@Autowired
	private MessageUtil messageUtil;

	@Override
	public List<Message> getMessages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeMessages(List<Message> messageList) {
		System.out.println("-----Inside Service Class---Store Messages---");
		List<Message> redMessages = null;
		List<Message> yellowMessages = null;
		List<Message> blueMessages = null;
		List<Message> updatedMessages = null;

		try {
			redMessages = messageUtil.constructListByMessageType(MessageType.RED.toString(), messageList);
			yellowMessages = messageUtil.constructListByMessageType(MessageType.YELLOW.toString(), messageList);
			blueMessages = messageUtil.constructListByMessageType(MessageType.BLUE.toString(), messageList);

			if (redMessages != null && redMessages.size() > 0) {
				updatedMessages = messageUtil.updateExistingMessages(messageMap, MessageType.RED.toString(),
						redMessages);
				if (updatedMessages != null && updatedMessages.size() > 0) {
					messageMap.put(MessageType.RED.toString(), updatedMessages);
					System.out.println("-----RED Message Size----" + messageMap.get(MessageType.RED.toString()).size());
				}
			}

			if (yellowMessages != null && yellowMessages.size() > 0) {
				updatedMessages = messageUtil.updateExistingMessages(messageMap, MessageType.YELLOW.toString(),
						yellowMessages);
				if (updatedMessages != null && updatedMessages.size() > 0) {
					messageMap.put(MessageType.YELLOW.toString(), updatedMessages);
					System.out.println(
							"-----YELLOW Message Size----" + messageMap.get(MessageType.YELLOW.toString()).size());
				}
			}

			if (blueMessages != null && blueMessages.size() > 0) {
				updatedMessages = messageUtil.updateExistingMessages(messageMap, MessageType.BLUE.toString(),
						blueMessages);
				if (updatedMessages != null && updatedMessages.size() > 0) {
					messageMap.put(MessageType.BLUE.toString(), updatedMessages);
					System.out
							.println("----BLUE Message Size----" + messageMap.get(MessageType.BLUE.toString()).size());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Message processMessageByType(String messageType) {
		Message messageObj = null;
		List<Message> messageList = null;
		if (!StringUtils.isEmpty(messageType)) {
			if (MessageServiceImpl.messageMap != null && !MessageServiceImpl.messageMap.isEmpty()) {
				messageList = messageMap.get(messageType);
				if (messageList != null && messageList.size() > 0) {
					int pointer = messageUtil.getCandidateMessage(messageList);
					messageObj = messageList.get(pointer);
				}
			}
		}
		return messageObj;
	}

	@Override
	public List<Message> getMessagesByType(String messageType) {
		List<Message> messageList = null;

		if (!StringUtils.isEmpty(messageType)) {
			if (MessageServiceImpl.messageMap != null && !MessageServiceImpl.messageMap.isEmpty()) {
				messageList = messageMap.get(messageType);
			} else {
				System.out.println("-----MAP IS EMPTY----");
			}

		}

		// TODO Auto-generated method stub
		return messageList;
	}

}
