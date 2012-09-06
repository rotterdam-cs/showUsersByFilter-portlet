package com.rcs.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BloggersDTO  implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<BloggerDTO> blogger = new ArrayList<BloggerDTO>();	
	
	public List<BloggerDTO> getBlogger() {
		return blogger;
	}
	
	public void setBlogger(List<BloggerDTO> blogger) {
		this.blogger = blogger;
	}	

	public void addBlogger(BloggerDTO bloggerDTO) {
		blogger.add(bloggerDTO);		
	}
	
}