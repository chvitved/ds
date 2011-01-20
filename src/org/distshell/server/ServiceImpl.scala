package org.distshell.server
import com.google.gwt.user.server.rpc.RemoteServiceServlet

import org.distshell.shared.Service
import akka.actor.{Actor, ActorRef}
import akka.actor.Actor._
import org.distshell.shared.{Machine, Command, CommandReply}

class ServiceImpl extends RemoteServiceServlet with Service {
	
	val serviceActor = actorOf[ServiceActor].start
	
	override def sendCommand(command: String) = {
		(serviceActor !! SendCommand(command, "")).get.asInstanceOf[Command]
	}
	
	override def getUpdates() = {
		val replies = (serviceActor !! GetUpdates).get.asInstanceOf[Map[Command, Map[Machine, List[CommandReply]]]]

		val result = new java.util.HashMap[Command, java.util.Map[Machine, java.util.Collection[CommandReply]]]() 
		for((command, machinesMap) <- replies) {
			var commandMap = new java.util.HashMap[Machine, java.util.Collection[CommandReply]]()
			for((machine, updates) <- machinesMap) {
				commandMap.put(machine, new java.util.ArrayList[CommandReply](scala.collection.JavaConversions.asCollection(updates)))
			}
			result.put(command, commandMap)
		}
		result
	}
}