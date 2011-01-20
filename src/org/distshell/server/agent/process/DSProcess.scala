package org.distshell.server.agent.process

import java.io._
import org.distshell.shared.CommandReply.{Output, Error, Finished}
import org.distshell.shared.Machine
import akka.actor.ActorRef

class DSProcess(command: String, directory: String, machine: Machine, listener: ActorRef) {
	
	val streamPollInterval = 50;

	var finished = false;
	var process: Process = null
	
	val pb = new ProcessBuilder("/bin/sh", "-c", command)
	
	
	pb.directory(new File(directory))
	try {
		process = pb.start()
		new Thread() {
			override def run() {
				process.waitFor()
				finished = true
			}
		}.start()
	
		val outputReaderThread = new Thread() {
			override def run() {
				var isFinished = false
				while(!isFinished) {
					isFinished = finished
					val bytesInOutput= process.getInputStream.available() 
					if (bytesInOutput > 0) { 
						val bytes = new Array[Byte](bytesInOutput)
						process.getInputStream.read(bytes, 0, bytesInOutput)
						listener ! new Output(machine, new String(bytes)) //TODO encoding
					}
					val bytesInErr= process.getErrorStream().available()
					if (bytesInErr > 0) { 
						val bytes = new Array[Byte](bytesInErr)
						process.getErrorStream().read(bytes, 0, bytesInErr)
						listener ! new Error(machine, new String(bytes)) //TODO encoding
					}
					if (!isFinished) Thread.sleep(streamPollInterval)
				}  
				listener ! new Finished(machine)
			}
		}
		outputReaderThread.start
	} catch {
		case ex: IOException => {
			listener ! new Error(machine, "Error starting process: " + ex.getMessage)
			listener ! new Finished(machine)
		}
	}
		
		
	def input(inputStr: String) {
		process.getOutputStream.write(inputStr.getBytes())  //TODO so far we use default encoding
	}
	
	def abort() {
		process.destroy()
		finished = true
	}
	
}