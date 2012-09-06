package com.rcs.dto;

import java.io.Serializable;
import java.util.Date;

public class BlogEntryDTO  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String title;
	private Date postedDate;
	private String url;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
