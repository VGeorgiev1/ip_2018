package org.elsys.netprog.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Chat {
	
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		ArrayList<Connection> sockets = new ArrayList<Connection>();
		try {
			serverSocket = new ServerSocket(10001);
			
			while(true) {
				Connection c = new Connection(serverSocket.accept(), sockets);
				c.start();
				sockets.add(c);
			}
 		    
		} catch (Throwable t) {
			System.out.println(t.getMessage() + "in server");
		} finally {
			if (serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
			//System.out.println("Server closed");
		}
	}
	
}
