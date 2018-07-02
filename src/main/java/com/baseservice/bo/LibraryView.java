package com.baseservice.bo;

import java.util.List;

public class LibraryView
{
	private List<BookInfo> library;
	private String result;

	public List<BookInfo> getLibrary()
	{
		return library;
	}

	public void setLibrary(List<BookInfo> library)
	{
		this.library = library;
	}

	public String getResult()
	{
		return result;
	}

	public void setResult(String result)
	{
		this.result = result;
	}

}
