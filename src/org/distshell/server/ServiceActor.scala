package org.distshell.server

import akka.actor.Actor._
import akka.actor.{Actor, ActorRef, ScalaActorRef}
import org.distshell.shared.{Machine, Command, CommandReply}
import java.util.UUID

class ServiceActor extends Actor{

	var activeCommandCoordinators = Vector[ActorRef]()

	val machine1 = actorOf(new agent.Machine("machine 1")).start
	val machine2 = actorOf(new agent.Machine("machine 2")).start

	def receive = {
		case GetUpdates => {
			val collector = actorOf(new UpdateCollector(activeCommandCoordinators)).start 
			collector.forward(GetUpdates)
		}
		case SendCommand(commandString, dir) => {
			val command = new Command(UUID.randomUUID().toString())
			self.reply(command)
			val coordinator = actorOf(new CommandCoordinator(command, machine1, machine2)).start
			activeCommandCoordinators = coordinator +: activeCommandCoordinators
			coordinator ! Execute(commandString, "/Users/chr/")
	
		}
	}

	class UpdateCollector(activeCommandCoordinators: Vector[ActorRef]) extends Actor {

		var replies = Map[Command, Map[Machine, List[CommandReply]]]() 
		var replyRef : Option[ReplyRef] = None 

		def go : Receive = {
			case GetUpdates => {
				if (!activeCommandCoordinators.isEmpty) {
					activeCommandCoordinators.foreach(_ ! GetUpdates)
					replyRef = Some(new ReplyRef(self))
					become(getResults)
				} else {
					self.reply(replies)
				}
	
			}
		}

		def getResults: Receive = {
			case Updates(process, repliesFromCoordinator) => {
				replies = replies + (process -> repliesFromCoordinator)
				if (activeCommandCoordinators.length == replies.size) {
					replyRef.get.reply(replies)
				}
			}
		}

		def receive = go

		class ReplyRef(actor: ScalaActorRef) {						
			val reply: Either[ActorRef, akka.dispatch.CompletableFuture[Any]] = 
				if (actor.senderFuture.isDefined) {
					Right(actor.senderFuture.get)
				} else if (actor.sender.isDefined){
					Left(actor.sender.get)
				} else {
					throw new Exception("no sender")
				}

			def reply(msg: Any) {
				reply match {
				case Left(actorRef) => actorRef ! msg
				case Right(completeableFuture) => completeableFuture completeWithResult msg
				}
			}
		}
	}
}