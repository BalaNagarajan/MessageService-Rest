package com.javacircle.soa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {

	public Message() {

	}

	@JsonProperty("message")
	private String message;
	@JsonProperty("messageType")
	private String messageType;
	@JsonProperty("messagePriority")
	private String messagePriority;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((messagePriority == null) ? 0 : messagePriority.hashCode());
		result = prime * result + ((messageType == null) ? 0 : messageType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (messagePriority == null) {
			if (other.messagePriority != null)
				return false;
		} else if (!messagePriority.equals(other.messagePriority))
			return false;
		if (messageType == null) {
			if (other.messageType != null)
				return false;
		} else if (!messageType.equals(other.messageType))
			return false;
		return true;
	}

	@JsonProperty("messageType")
	public String getMessageType() {
		return messageType;
	}

	@JsonProperty("messageType")
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	@JsonProperty("messagePriority")
	public String getMessagePriority() {
		return messagePriority;
	}

	@JsonProperty("messagePriority")
	public void setMessagePriority(String messagePriority) {
		this.messagePriority = messagePriority;
	}

	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	@JsonProperty("message")
	public void setMessage(String message) {
		this.message = message;
	}

}
