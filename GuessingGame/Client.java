import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/***
 * Client program for assignment. 
 * 
 * Need to implement:
 * Check to see if there is a way to get a message from the server without
 * the constant calls to println.
 * @author rmen8_000
 *
 */

public class Client {

	public static void main(String[] args) {
		
		final int Port = 25000;
		
		//For use at home (No easy access to server).
		final InetAddress server = InetAddress.getLoopbackAddress();
		//final String server = "c34n1.csit.rmit.edu.au";
		final String exit = "X";
		
		PrintWriter out = null;
		BufferedReader in = null;
		BufferedReader console = null;
		Socket connect = null;
		
		String userInput = null;
				
		try {
			System.out.println("Program start.");
			
			//Connect to server program
			connect = new Socket(server, Port);
			out = new PrintWriter(connect.getOutputStream(), true);
			in = new BufferedReader(
					new InputStreamReader(connect.getInputStream()));
			console = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("Connected. Server IP: " + 
					connect.getInetAddress());
			
			System.out.println(in.readLine());
			
			//Game loop.
			while (true) {
				System.out.println(in.readLine());
				
				//Input loop with validation.
				do {
					System.out.println("Enter a number between 1 and 10.");
					userInput = console.readLine();
					
					if (userInput.equals(exit))
						break;
					
					} while (!isInt(userInput));
				
				//Double break if user enters X. Not sure how to fix this.
				if (userInput.equals(exit))
					break;
				
				out.println(userInput);
				System.out.println(in.readLine());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		finally {
			try {
			out.close();
			in.close();
			console.close();
			connect.close();
			System.out.println("Exiting.");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/***
	 * Input validation method.Checks a string to see if it is an integer 
	 * between 1 and 10. If it is, returns true. Otherwise, returns false.
	 * @param input
	 * @return
	 */
	
	//Code adapted from www.cse.yorku.ca/~mack/1011/InputValidation.PDF
	public static boolean isInt(String input) {
		
		//If string is empty, return false.
		if (input.length() == 0)
			return false;
		
		int i = 0;
		
		//Check each character in the string to see if it is a digit.
		while (i<input.length()) {
			if (!Character.isDigit(input.charAt(i)))
				//If not, break.
				break;
			i++;
		}
		
		//If i is the same as the length of the input, then the input is an int.
		if (i == input.length()) {
			//Convert input to int and check if it is between 1 and 10.
			int number = Integer.parseInt(input);
			//If yes, return true.
			if (number > 0 && number < 11)
				return true;
			//Else, return false.
			else
				return false;
		}
			
		//Return false if i is not the same as input length. 
		else
			return false;

	}
}
