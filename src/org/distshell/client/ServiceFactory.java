package org.distshell.client;

import org.distshell.shared.Service;
import org.distshell.shared.ServiceAsync;

import com.google.gwt.core.client.GWT;


public class ServiceFactory {

	private static final ServiceAsync service = GWT.create(Service.class);
	
	public static ServiceAsync getService() {
		return service;
	}
	
	
}
