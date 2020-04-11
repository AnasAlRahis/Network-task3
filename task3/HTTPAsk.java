import java.net.*;
import java.io.*;
import tcpclient.TCPClient;
public class HTTPAsk {
    public static void main( String[] args) throws IOException {
        // Your code here
		
		ServerSocket testsocket = new ServerSocket(Integer.parseInt(args[0]));
		
		
		
		String badrequest = "HTTP/1.1 400 BAD REQUEST\r\n\r\n";
		String unknownserver = "HTTP/1.1 404 NOT FOUND\r\n\r\n";
		
		while(true) { 
			StringBuilder input_string = new StringBuilder();
			Socket testconnection = testsocket.accept();
			
			BufferedReader bufferinput = new BufferedReader(new InputStreamReader(testconnection.getInputStream()));
			String teststring = bufferinput.readLine();
			
			
			if(teststring.contains("GET") && teststring.contains("ask?") && teststring.contains("HTTP/1.1")) {
				String[] split_string = splitting(teststring);
				if(split_string[0].equals("") || split_string[1].equals("")) {
					testconnection.getOutputStream().write(badrequest.getBytes());
					bufferinput.close();
					testconnection.close();
				}
				else {
					try {
						String result = TCPClient.askServer(split_string[0],Integer.parseInt(split_string[1]),split_string[2]);
					
						input_string.append("HTTP/1.1 200 OK\r\n");
						input_string.append("\r\n");
						input_string.append(result);
					
						testconnection.getOutputStream().write(input_string.toString().getBytes());
						bufferinput.close();
						testconnection.close();
					} catch (IOException x){
						testconnection.getOutputStream().write(unknownserver.getBytes());
						bufferinput.close();
						testconnection.close();
					}
				}
			}
			else {
				testconnection.getOutputStream().write(badrequest.getBytes());
				bufferinput.close();
				testconnection.close();
			}
			
			
		}	
			
  }
	
	
	
	public static String[] splitting(String input) { 
		String[] hostportparam = new String[3];
		for(int x = 0; x < hostportparam.length; x++)
				hostportparam[x] = "";
		String[] spltd_string = input.split("[&=? ]");
		for(int i = 0; i<spltd_string.length;i++) {
			if(spltd_string[i].equals("hostname"))
				hostportparam[0] = spltd_string[i+1];
			if(spltd_string[i].equals("port"))
				hostportparam[1] = spltd_string[i+1];
			if(spltd_string[i].equals("string"))
				hostportparam[2] = spltd_string[i+1];
		}
		return hostportparam;
		
	}
	
	
	
}

