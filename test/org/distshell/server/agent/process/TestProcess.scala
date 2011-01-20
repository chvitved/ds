package org.distshell.server.agent.process
import org.distshell.shared.CommandReply.{Output, Error, Finished}

import org.junit.Test
import org.junit.Assert._
import akka.actor.{Actor, ActorRef}
import akka.actor.Actor._

class TestProcess {
	
	case class Start(command: String, directory: String)
	
	class ProcessTestActor(val asertFunc : (String, String) => Unit) extends Actor {
		val outBuilder = new StringBuilder()
		val errorBuilder = new StringBuilder()
		
		var waiting: Option[akka.dispatch.CompletableFuture[Any]] = None;
		
		def receive: Receive = {
			  case Start(command, directory) => {
			 	  waiting = self.senderFuture
			 	  new DSProcess(command, directory, null, self)
			  }
			  case output: Output => outBuilder.append(output.getValue())
			  case error: Error => errorBuilder.append(error.getValue())
			  case finished: Finished => {
			 	asertFunc(outBuilder.toString, errorBuilder.toString)
			 	waiting match {
			 		case Some(future) => future completeWithResult "done" 
			 		case None => 
			 	}
			  }
		}
	}
		
	def testCommand(command: String, asertFunc : (String, String) => Unit) {
		val testActor = actorOf(new ProcessTestActor(asertFunc)).start
		testActor !! (Start(command, "test/testfiles"), 5000) match {
			case Some(done) => //fine
			case None => fail("asertFunc not called. Is an actor waiting for a message or blocking for reply that does not happen. Do the process finish?")
		}
	}
	
	@Test
	def testLs() {
		val command = "ls"
		def assert(out: String, err: String) {
			assertEquals("dir1\nfile1.txt\nfile2.txt\n", out)
			assertEquals("", err)
		}
		testCommand(command, assert)
	}
	
	@Test
	def testLsWithParams() {
		val command = "ls -alh"
		def assert(out: String, err: String) {
			assertTrue(out.contains("file1.txt\n"))
			assertTrue(out.contains("file2.txt\n"))
			assertTrue(out.contains("dir1\n"))
			assertEquals("", err)
		}
		testCommand(command, assert)
	}
	
	@Test
	def testUnknownCommand() {
		val command = "unknown file1.txt"
		def assert(out: String, err: String) {
			assertEquals("", out)
			assertEquals("/bin/sh: unknown: command not found\n", err) 
		}
		testCommand(command, assert)
	}

}