import java.io.*;
import java.net.*;

/***
 * A server thread is created whenever a client connects to the server. Each
 * thread runs a copy of the game. Eventually they will all play the same game.
 * @author rmen8_000
 *
 */
public class ServerThread extends Thread {
	
	private Socket client = null;
	PrintWriter out = null;
	BufferedReader in = null;
	BufferedReader console = null;

	String input;
	String cInput;
	
	ServerUtil util = new ServerUtil();
	
	int guessMe;
	int guessCount = 10;
	int clientGuess;
	int score = 0;
	
	public ServerThread(Socket socket) {
		this.client = socket;
	}
	
	public void run() {
			util.Log(true, "Client connected. IP: " + 
				client.getInetAddress() + " Port: " + client.getPort());
		
		try {
			
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(
					new InputStreamReader(client.getInputStream()));
			console = new BufferedReader(new InputStreamReader(System.in));
			
			//First generation of random num.
			guessMe = util.generateRand();
			
			util.Log(false, "Game begin message sent.");
			out.println("Let the game begin! Guess the random number to "
					+ "score a point. You have 10 guesses to score as many "
					+ "points as you can! Good luck");
			
			//Game loop;
			while (guessCount > 0) {
				
				util.Log(false, "Guess prompt sent.");
				out.println("You have " + guessCount + " guesses "
						+ "remaining. You score is: " + score + ". What is "
						+ "your next guess?");
				
				//Get client input. Input validation on client side.
				clientGuess = Integer.parseInt(in.readLine());
				
				if (clientGuess == guessMe) {
					util.Log(true, "client " + client.getInetAddress() + ":" +
							client.getPort() + " guessed correctly.");
					util.Log(false, "Success message sent.");
					out.println("You guessed correctly. 1 point to you.");
					score++;
					guessMe = util.generateRand();
				}
				else {
					util.Log(true, "client " + client.getInetAddress() + ":" +
							client.getPort() + " guessed incorrectly.");
					util.Log(false, "Failure message sent.");
					out.println("You guess incorrectly. Try again.");
				}
				
				guessCount--;
			}	
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (NumberFormatException e) {
			util.Log(true, "client " + client.getInetAddress() + ":" +
							client.getPort() + " forced exit.");
		}
		finally {
			try {
				out.close();
				in.close();
				util.Log(true, "Thread end.");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}