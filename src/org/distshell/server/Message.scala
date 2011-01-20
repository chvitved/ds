package org.distshell.server

import akka.actor.ActorRef
import org.distshell.shared.{Machine, Command, CommandReply}

case class GetUpdates
case class Updates(command: Command, replies: Map[Machine, List[CommandReply]])
case class SendCommand(command: String, dir: String)

case class ExecuteProcess(command: String, directory: String, listener: ActorRef)
case class Execute(command: String, directory: String)
case class Input(input: String)
case class Abort(id: String) 

