package org.distshell.shared;


import java.util.Collection;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ServiceAsync {

	void getUpdates(AsyncCallback<Map<Command, Map<Machine, Collection<CommandReply>>>> callback);

	void sendCommand(String command, AsyncCallback<Command> callback);


}
