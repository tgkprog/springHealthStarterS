package com.sel2in.smsWebSend.model;

import java.io.Serializable;

public enum Gender implements Serializable {
	MALE, FEMALE;

	public static Gender valueOf(Integer ordinal) {
		if (ordinal != null) {
			for (Gender value : values()) {
				if (value.ordinal() == ordinal) {
					return value;
				}
			}
		}
		return null;
	}

}
