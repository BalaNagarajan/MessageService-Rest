package com.javacircle.soa.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.javacircle.soa.model.ErrorMessage;
import com.javacircle.soa.model.Message;
import com.javacircle.soa.request.MessageRequest;
import com.javacircle.soa.response.MessageResponse;
import com.javacircle.soa.service.MessageService;

/**
 * This class acts as a Controller for the Message Processing Service.It accepts
 * the 'n' list of messages and stores it in the memory. Also removes the
 * message from the queue based on message type,message priority and order.
 * 
 * Message Priority : 1 2 3 4 5 6 (Highest --> Lowest)
 * 
 * @author N.Bala
 *
 */
@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class MessageController {

	/**
	 * Service injection
	 */
	@Autowired
	private MessageService messageService;

	/**
	 * Logger object to log the application related messages
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * This api call returns the list of messages based on given message type.
	 * Possible Message type values { RED / BLUE / RED }
	 * 
	 * @param messageType
	 * @return
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/v1/messages/{messageType}")
	public MessageResponse getMessage(@PathVariable(name = "messageType") String messageType) {
		MessageResponse messageResponse = new MessageResponse();
		ErrorMessage errorMessage = null;
		try {
			if (!StringUtils.isEmpty(messageType)) {
				logger.debug("Message Type In getMessage()---:" + messageType);
				List<Message> messageList = messageService.getMessagesByType(messageType);
				if (messageList != null && messageList.size() > 0) {
					messageResponse.setMessageList(messageList);
				} else {
					System.out.println("---In Else Error Part---");
					errorMessage = new ErrorMessage();
					errorMessage.setInternalMessage("Message list is empty");
					errorMessage.setDescription("No Messages");
					messageResponse.setError(errorMessage);
					messageResponse.setStatusCode(HttpStatus.OK.value());

				}

			}
		} catch (Exception e) {
			logger.debug("Exception In" + this.getClass().getName() + "Method:getMessage" + e.getMessage());
		}

		return messageResponse;

	}

	/**
	 * This api call stores the list of user messages in memory based on the
	 * order it received / type
	 * 
	 * @param messageRequest
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/v1/messages")
	public void storeMessages(@RequestBody MessageRequest messageRequest) {
		System.out.println("-----Inside Store Messages------");
		try {
			if (messageRequest != null && messageRequest.getMessageList() != null) {
				if (messageRequest.getMessageList().size() > 0) {
					messageService.storeMessages(messageRequest.getMessageList());
				}

			}
		} catch (Exception e) {
			logger.debug("Exception In" + this.getClass().getName() + "Method:storeMessages" + e.getMessage());
		}

	}

	/**
	 * This method process or deletes the message from the queue based on the
	 * message type. First it checks for the highest priority message based on
	 * message type , if more than one entries are present with the same
	 * priority then based on the order it received (older) one gets removed
	 * from the queue and published to the end user to indicate the deletion
	 * 
	 * @param messageType
	 * @return
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.DELETE, value = "/v1/messages/{messageType}")
	public MessageResponse processMessage(@PathVariable(name = "messageType") String messageType) {
		MessageResponse messageResponse = new MessageResponse();
		try {
			if (!StringUtils.isEmpty(messageType)) {
				System.out.println("The Message Type Is----" + messageType);
				Message message = messageService.processMessageByType(messageType);
				messageResponse.setMessage(message);
			}
		} catch (Exception e) {
			logger.debug("Exception In" + this.getClass().getName() + "Method:processMessage" + e.getMessage());
		}

		return messageResponse;

	}

}
