package org.elsys.netprog.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Connection implements Runnable {
	private Thread t;
	private String address;
	private int port;
	private Socket socket;
	private ArrayList<Connection> others;
	private String name;
	private PrintWriter out;
	private BufferedReader in;
	Connection(Socket socket, ArrayList<Connection> others) {
		this.socket = socket;
		this.others = others;
		name = "client";
		try {
			this.out = new PrintWriter(this.socket.getOutputStream(), true);
			this.in = new BufferedReader(
					new InputStreamReader(this.socket.getInputStream()));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public Socket getSocket() {
		return this.socket;
	}  
	public void run() {
		try {
			String inputLine;
 		    while ((inputLine = this.in.readLine()) != null) {
		        if (inputLine.equals("exit"))
		            break;
		        for(int i=0;i < others.size();i++) {
		        	if(others.get(i).getSocket() != this.socket) {
		        		others.get(i).getWriter().println(inputLine);
		        	}
		        }
		        //out.println(inputLine);
		    }
		} catch (Throwable t) {
			System.out.println(t.getMessage()+"in clientj");
		}
		
	}
	public PrintWriter getWriter() {
		if(out == null) {
			System.out.println("out wtf");
		};
		return this.out;
	}
	public BufferedReader getReader() {
		if(in == null) {
			System.out.println("in wtf");
		};
		return this.in;
	}
	public void start () {
		  
		     t = new Thread (this, name);
		     t.start();
		  
	}
}

