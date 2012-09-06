package com.rcs.enums;

public enum BasePortletSectionsEnum {
	
	ADMIN_SECTION_CONFIGURATION("configuration"),
	ADMIN_SECTION_VIEW_REPORTS("view_configuration");
	
	private String key;

    private BasePortletSectionsEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
	
}
