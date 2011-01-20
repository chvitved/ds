package org.distshell.server.agent
import org.distshell.server.ExecuteProcess
import org.distshell.server.agent.process.DSProcess

import akka.actor.Actor

class Machine(name: String) extends Actor{
	
	val machine = new org.distshell.shared.Machine(name)

	def receive: Receive = {
		case ExecuteProcess(command, dir, listener) => {
			val process = new DSProcess(command, dir, machine, listener)
		}
	}
}