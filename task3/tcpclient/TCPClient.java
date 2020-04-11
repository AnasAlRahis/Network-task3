package tcpclient;
import java.net.*;


import java.io.*;

public class TCPClient {
	
	
	public static String askServer(String hostname, int port) throws  IOException {
		byte[] server_input_byte = new byte[1024];                                // THE MOST WE CAN READ FROM A SERVER
		char[] server_input_char = new char[1024];                                //data read from server as characters
		String server_input = " ";
		int max = 0;

		
		Socket clientToServer = new Socket(hostname, port);
		clientToServer.setSoTimeout(20000);                                            //setting timeout for 500ms
		try { max = clientToServer.getInputStream().read(server_input_byte);                //try reading bytes from the server and storing them into a byte array
				
		} catch(SocketTimeoutException et) {                                        //catch timeout
			clientToServer.close();
			return server_input;
		}catch(IOException ex) {                                                     //catch IO exceptions
			clientToServer.close();
			System.err.print(ex);
			
			
		}
		
		for(int i = 0; i < max; i ++)                          //converting the bytes read into characters
			server_input_char[i] = (char) server_input_byte[i];
		
		StringBuilder fromServer = new StringBuilder();                             // building a string from the characters.
		fromServer.append(server_input_char,0 , max);
		
		server_input = fromServer.toString();
		clientToServer.close();                                                       
		return server_input;
		
    }
	
	public static String askServer(String hostname, int port, String toServer) throws  IOException {
		if(toServer == null)                                           //call the first method if no string was passed to the argument (-) 
			return askServer(hostname,port);
		
		char[] user_input_char = toServer.toCharArray();                         //converting the input string into character array
		byte[] user_input_byte = new byte[user_input_char.length];
		byte[] server_input_byte = new byte[1024];                               // THE MOST WE CAN READ FROM A SERVER
		char[] server_input_char = new char[1024];                              
		String server_input = " ";
		int max = 0;
		
		for(int i = 0; i < user_input_char.length; i++)                          //converting the input characters into bytes and storing them in a byte array
			user_input_byte[i] = (byte) user_input_char[i];
		
		Socket clientToServer = new Socket(hostname, port);
		clientToServer.setSoTimeout(20000);
		
		try{ clientToServer.getOutputStream().write(user_input_byte);            //writing the bytes to the server
		clientToServer.getOutputStream().write('\n'); 
		} catch(IOException ex) {                                           		//catch IO exceptions
			clientToServer.close();
			System.err.println(ex);
			 
			}
		
		try { max = clientToServer.getInputStream().read(server_input_byte);             //reading bytes from the server and storing them in a byte array
		} catch(SocketTimeoutException ex){                                      //catching timeouts
			clientToServer.close();
			return server_input;
		} catch(IOException ex) {
			clientToServer.close();
			System.err.print(ex); 
			}
		 
		for(int i = 0; i < max; i++)                       //converting bytes read into characters and storing them in a character array
			server_input_char[i] = (char) server_input_byte[i];
		StringBuilder output = new StringBuilder();                               //building a string from the characters
		output.append(server_input_char, 0 ,max); 
		
		server_input = output.toString();
		 
		clientToServer.close();
		return server_input;
	}
	
	
 
}



