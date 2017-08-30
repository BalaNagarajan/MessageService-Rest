package com.javacircle.soa.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.javacircle.soa.model.ErrorMessage;
import com.javacircle.soa.model.Message;

@JsonInclude(Include.NON_NULL)
public class MessageResponse extends ServiceResponse {

	@JsonProperty("messageList")
	private List<Message> messageList;
	@JsonProperty("message")
	private Message message;
	@JsonProperty("error")
	private ErrorMessage error;

	public ErrorMessage getError() {
		return error;
	}

	public void setError(ErrorMessage error) {
		this.error = error;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public List<Message> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<Message> messageList) {
		this.messageList = messageList;
	}

}
