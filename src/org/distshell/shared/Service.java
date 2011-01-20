package org.distshell.shared;

import java.util.Collection;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("services")
public interface Service extends RemoteService {
	Command sendCommand(String command);
	Map<Command, Map<Machine,Collection<CommandReply>>> getUpdates();
}
