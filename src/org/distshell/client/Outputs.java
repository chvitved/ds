package org.distshell.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.distshell.client.components.UnorderedListWidget;
import org.distshell.client.process.ProcessOutputWidget;
import org.distshell.shared.Machine;
import org.distshell.shared.Command;
import org.distshell.shared.CommandReply;

import com.google.gwt.user.client.ui.Composite;

public class Outputs extends Composite{
	
	private UnorderedListWidget list;
	private Map<Machine, ProcessOutputWidget> map;
	
	public Outputs() {
		list = new UnorderedListWidget();
		map = new HashMap<Machine, ProcessOutputWidget>();
		initWidget(list);
		
	}
	
	public void showOutput(Command process, Machine machine, Collection<CommandReply> processReplies) {
		if (!map.containsKey(machine)) {
			ProcessOutputWidget w = new ProcessOutputWidget();
			list.add(w);
			map.put(machine, w);
		}
		ProcessOutputWidget w = map.get(machine);
		for (CommandReply processReply : processReplies) {
			w.add(processReply);
		}
	}

}
