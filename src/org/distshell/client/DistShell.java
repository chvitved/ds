package org.distshell.client;

import org.distshell.client.input.CommandLine;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DistShell implements EntryPoint {

	public void onModuleLoad() {
		RootPanel root = RootPanel.get("body");
		CommandLine commandLine = new CommandLine();
		root.add(commandLine);
		
		Outputs outputs = new Outputs();
		root.add(outputs);
		
		DataCollector dc = new DataCollector(outputs);
	}
}
