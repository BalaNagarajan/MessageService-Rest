package com.javacircle.soa.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.javacircle.soa.model.Message;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "messageList" })
public class MessageRequest {

	@JsonProperty("messageList")
	private List<Message> messageList;

	@JsonProperty("messageList")
	public List<Message> getMessageList() {
		return messageList;
	}

	@JsonProperty("messageList")
	public void setMessageList(List<Message> messageList) {
		this.messageList = messageList;
	}

}
