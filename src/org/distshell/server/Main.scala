package org.distshell.server

import com.sun.grizzly.http.servlet.ServletAdapter
import com.sun.grizzly.http.embed.GrizzlyWebServer
import org.mortbay.jetty.webapp.WebAppContext
import org.mortbay.jetty.servlet.{ServletHolder, Context, DefaultServlet}
import org.mortbay.jetty.Server
import akka.servlet.Initializer

object Main extends Application{

	//val server = new Server(8888);
	//	val root = new Context(server,"/",Context.SESSIONS)
	//	root.setResourceBase("war")
	//	root.addServlet(new ServletHolder(new ServiceImpl()), "/distshell/services")
	//	root.addServlet(new ServletHolder(new DefaultServlet()), "/")
	//	root.setWelcomeFiles(Array("distshell.html"))
	//	root.addEventListener(new Initializer())
	//  server.start()
	//  server.join()
	
	val grizzly = new GrizzlyWebServer()
	
	val staticContent = new ServletAdapter("war")
	staticContent.setContextPath("/")
	staticContent.setHandleStaticResources(true)
	grizzly.addGrizzlyAdapter(staticContent, Array("/"))
	
	val sa = new ServletAdapter(new ServiceImpl())
	sa.setContextPath("/distshell/services")
	grizzly.addGrizzlyAdapter(sa, Array("/distshell/services"))
	
	grizzly.start()
	println("started grizzly")

	
}