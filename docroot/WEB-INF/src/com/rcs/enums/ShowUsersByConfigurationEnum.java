package com.rcs.enums;

public enum ShowUsersByConfigurationEnum {	
		
	ROLE("filterbyrole"),
	GROUP("filterbygroup"),
	JOB_TITLE("filterbyjobtitle"),
	LINK_TYPE("linktype"),
	CUSTOM_LINK("customlink"),
	DISPLAY_STYLE("displaystyle"),
	FILTER_TYPE("filtertype"),
	TAGS("filterbytags"),
	CATEGORY("filterbycategory"),
	SHOW_EMAIL("showemail"),
	SHOW_PHONE("showphone");
	
	private String key;

    private ShowUsersByConfigurationEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}