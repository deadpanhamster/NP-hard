import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/***
 * Contains utility methods for the main server program. Currently just
 * a random number generator. This is probably where logging will live, also.
 * @author rmen8_000
 *
 */

public class ServerUtil {
	
	final File commLog = new File("commLog.txt");
	final File gameLog = new File("gameLog.txt");
	
	
	public ServerUtil() {
		
	}
	
	/***
	 * Returns a random number between 1 and 10. 
	 * @return
	 */
	
	public int generateRand() {
		System.out.println("Generating random number.");
		
		Random generator = new Random();
		
		/*Took formula for random number from 
		stackoverflow.com/questions/363681/
		generating-random-integers-in-a-range-with-java*/
		int result = generator.nextInt((10 - 1) + 1) + 1;
		System.out.println("Number generated: " + result);
		
		return result;
	}
	
	/***
	 * Handles logging. If game is true, logs to game log file, else to comms
	 * log. Concatenates date time format with message. Also prints message
	 * to console.
	 * @param game
	 * @param message
	 */
	
	public void Log(boolean game, String message) {
		
		Date date = new Date();
		SimpleDateFormat ft = 
				new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss: ");
		
		System.out.println(message);
		
		/* Grabbed file writing format from 
		 * stackoverflow.com/questions/1625234/
		 * how-to-append-text-to-an-existing-file-in-java
		 */
		if (game) {
			try (PrintWriter out = new PrintWriter(new BufferedWriter
					(new FileWriter(gameLog, true)))) {
				out.println(ft.format(date) + message);
			}
			catch (IOException e) {
				System.out.println("Logging failed.");
			}
		}
		else {
			try (PrintWriter out = new PrintWriter(new BufferedWriter
					(new FileWriter(commLog, true)))) {
				out.println(ft.format(date) + message);
			}
			catch (IOException e) {
				System.out.println("Logging failed.");
			}
		}
	}
}

	