package org.distshell.server

import akka.actor._
import org.distshell.shared.{Machine, Command, CommandReply}
import org.distshell.shared.CommandReply.Finished

class CommandCoordinator(command: Command, machines: ActorRef *) extends Actor{

	//TODO setup so that events are recieved if machines do not respond
	
	var map = Map[Machine, List[CommandReply]]()
	var commandTerminated = Set[Machine]() 
	
	def execute: Receive = {
		case Execute(command, directory) => {
			for(machine <- machines) machine ! ExecuteProcess(command, directory, self)
			become(started)
		}
	}
	
	def started: Receive = {
		case commandReply: CommandReply => {
			val list = map.get(commandReply.getMachine) match {
					case Some(list) => list
					case None => List()
				}
			map = map + (commandReply.getMachine -> (commandReply :: list))
			commandReply match {
				case finished : Finished => commandTerminated = commandTerminated + finished.getMachine
				case _ =>
			}
		}
		case GetUpdates => {
			self.reply(Updates(command, map))
			map = Map()
		}
	}
	
	def receive = execute
	
}