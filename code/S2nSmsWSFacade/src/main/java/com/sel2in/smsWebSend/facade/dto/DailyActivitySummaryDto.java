
package com.sel2in.smsWebSend.facade.dto;

import java.util.Date;

public class DailyActivitySummaryDto {

	/** 
	 * see xls
	 * */
	private long id;
	private String authorId;
	private Date date;
	private int count;
	
	
	public DailyActivitySummaryDto() {
	}


	public DailyActivitySummaryDto(long id, String authorId, Date date, int count) {
		super();
		this.id = id;
		this.authorId = authorId;
		this.date = date;
		this.count = count;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getAuthorId() {
		return authorId;
	}


	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}




	

}