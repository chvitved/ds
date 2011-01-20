package org.distshell.client;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.distshell.shared.Machine;
import org.distshell.shared.Command;
import org.distshell.shared.CommandReply;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DataCollector {
	
	public DataCollector(final Outputs outputs) {
		ServiceFactory.getService();
		Timer timer = new Timer() {
			@Override
			public void run() {
				ServiceFactory.getService().getUpdates(new AsyncCallback<Map<Command, Map<Machine, Collection<CommandReply>>>>() {
					@Override
					public void onFailure(Throwable caught) {
						throw new RuntimeException(caught);
					}

					@Override
					public void onSuccess(final Map<Command, Map<Machine, Collection<CommandReply>>> updates) {
						if (!updates.isEmpty()) {
							for(Entry<Command, Map<Machine, Collection<CommandReply>>> processEntries: updates.entrySet()) {
								Command process = processEntries.getKey();
								for(Entry<Machine, Collection<CommandReply>> e: processEntries.getValue().entrySet()) {
									outputs.showOutput(process, e.getKey(), e.getValue());
								}
							}
						}
					}
				});
				
			}
		};
		timer.scheduleRepeating(1000);
	}

}
