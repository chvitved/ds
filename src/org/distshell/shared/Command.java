package org.distshell.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Command implements Serializable{
	
	private String id;

	//gwt
	public Command(){}
	
	public Command(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
