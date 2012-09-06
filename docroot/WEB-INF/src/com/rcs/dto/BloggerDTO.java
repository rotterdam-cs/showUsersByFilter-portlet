package com.rcs.dto;

import java.io.Serializable;

public class BloggerDTO  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long userId;
	private String url;
	private String comment;
	private String authorcategory;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getAuthorcategory() {
		return authorcategory;
	}
	public void setAuthorcategory(String authorcategory) {
		this.authorcategory = authorcategory;
	}	

}
