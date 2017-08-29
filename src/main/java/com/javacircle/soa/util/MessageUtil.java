package com.javacircle.soa.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.javacircle.soa.model.Message;
import com.javacircle.soa.model.MessageType;

/**
 * 
 * This class acts as a Utility class for Message Processing module. Its
 * contains the utility methods to be reused across the module.
 * 
 * @author N.Bala
 *
 */

@Component
public class MessageUtil {

	/**
	 * This message constructs the corresponding message list from the
	 * super/parent list based on the message type
	 * 
	 * @param messageType
	 * @param parentList
	 * @return
	 */
	public List<Message> constructListByMessageType(String messageType, List<Message> parentList) {

		List<Message> subMessageList = new ArrayList<Message>();

		if (parentList != null && parentList.size() > 0) {
			if (!StringUtils.isEmpty(messageType)) {
				parentList.forEach((messageObj) -> {
					if (!StringUtils.isEmpty(messageObj.getMessageType())) {
						if (messageObj.getMessageType().equalsIgnoreCase(messageType)) {
							subMessageList.add(messageObj);
						}
					}
				});
			}
		}

		return subMessageList;
	}

	/**
	 * The method checks the existing list based on the message type from the
	 * map , if the list is present then it appends the new items in the end
	 * otherwise add the new list and returns to the caller.
	 * 
	 * @param messageMap
	 * @param messageType
	 * @param newMessages
	 * @return
	 */
	public List<Message> updateExistingMessages(ConcurrentHashMap<String, List<Message>> messageMap, String messageType,
			List<Message> newMessages) {
		List<Message> tempMessages = null;
		if (messageMap != null) {
			if (messageMap.containsKey(messageType)) {
				tempMessages = messageMap.get(messageType);
				if (tempMessages != null && tempMessages.size() > 0) {
					tempMessages.addAll(newMessages);
				}
			} else {
				tempMessages = newMessages;
			}
		}

		return tempMessages;

	}

	/**
	 * This method performs the core logic of identifying the h
	 * 
	 * @param messageList
	 * @return
	 */
	public int getCandidateMessage(List<Message> messageList) {
		Message messageObj = null;
		int index = 0;
		int priorityTemp = 0;
		int priorityValue = 1;
		int pointer = 0;

		if (messageList != null && messageList.size() > 0) {
			for (Message message : messageList) {
				priorityValue = Integer.parseInt(message.getMessagePriority());

				if (index == 0) {
					priorityTemp = priorityValue;
				}
				System.out.println("----Priority Temp----" + priorityTemp);
				System.out.println("----Priority Value----" + priorityValue);

				if (priorityTemp > priorityValue) {
					pointer = index;
					int temp = priorityValue;
					priorityTemp = temp;
					System.out.println("----Inner Priority Temp----" + priorityTemp);
				}

				index++;

			}
		}

		System.out.println("-----Pointer Value-----" + pointer);
		return pointer;

	}

}
