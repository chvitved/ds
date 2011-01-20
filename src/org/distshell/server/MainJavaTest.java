package org.distshell.server;

import org.mortbay.jetty.servlet.Context;

import com.sun.grizzly.http.embed.GrizzlyWebServer;

import akka.servlet.Initializer;

public class MainJavaTest {
	
	public static void main(String[] args) {
		new GrizzlyWebServer();
		
	}

}
