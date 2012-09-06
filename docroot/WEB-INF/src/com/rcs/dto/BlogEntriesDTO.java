package com.rcs.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BlogEntriesDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private boolean hasEntries = false;
	
	public boolean isHasEntries() {
		return hasEntries;
	}

	public void setHasEntries(boolean hasEntries) {
		this.hasEntries = hasEntries;
	}	
	
	List<BlogEntryDTO> blogEntriesDTO = new ArrayList<BlogEntryDTO>();

	public List<BlogEntryDTO> getBlogEntriesDTO() {
		return blogEntriesDTO;
	}

	public void setBlogEntriesDTO(List<BlogEntryDTO> blogEntriesDTO) {
		this.blogEntriesDTO = blogEntriesDTO;
	}
	
	public void addBlogEntry(BlogEntryDTO blogEntryDTO) {
		blogEntriesDTO.add(blogEntryDTO);		
	}

}
