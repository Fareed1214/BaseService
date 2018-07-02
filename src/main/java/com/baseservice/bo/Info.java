package com.baseservice.bo;

import com.baseservice.constants.ResponseStatus;

public class Info
{

	private String status = ResponseStatus.BOOK_OPERATION_FAIL;

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

}
