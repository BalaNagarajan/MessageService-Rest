package com.javacircle.soa.response;

import org.springframework.http.HttpStatus;

public class ServiceResponse {

	private HttpStatus httpStatus;
	private boolean isValidRequest;
	private int statusCode;

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public boolean isValidRequest() {
		return isValidRequest;
	}

	public void setValidRequest(boolean isValidRequest) {
		this.isValidRequest = isValidRequest;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
