package com.rcs.enums;

public enum ConfigurationEnum {
	
	CONFIG1("config1"),
	CONFIG2("config2");
	
	private String key;

    private ConfigurationEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
	
}
