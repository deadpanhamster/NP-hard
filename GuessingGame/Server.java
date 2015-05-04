import java.io.*;
import java.net.*;

/***
 * Server main method for assignment one. At the moment this is simple.
 * It opens a server socket and starts a new thread whenever a client connects.
 * Future improvements: 
 * Logging with the serverutil class
 * Tracking connections and implementing a queue
 * Possibly running the primary components of the game and sending info to threads.
 *  
 * @author rmen8_000
 *
 */

public class Server {
	
	public static void main(String[] args) {
		
		final int Port = 25000;
		final ServerUtil util = new ServerUtil();
		
		util.Log(true, "Server started.");
		
		/*Threading adapted fromdocs.oracle.com/javase/tutorial/
		 * displayCode.html?code=https://docs.oracle.com/javase/
		 * tutorial/networking/sockets/examples/KKMultiServer.java
		 */
		
		try (ServerSocket socket = new ServerSocket(Port)){
						
			while (true) {
				new ServerThread(socket.accept()).start();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
