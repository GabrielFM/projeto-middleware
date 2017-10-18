import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientRequestHandler 
{
	private String host;
	private int port;
	private Socket clientSocket = null;
	private DataOutputStream outToServer = null;
	private DataInputStream inFromServer = null;
	
	public ClientRequestHandler(String host, int port)
	{
		this.host = host;
		this.port = port;
		
	}
	
	public void send(byte [] msg) throws IOException, InterruptedException
	{
		Boolean success = false;
		while(!success) {
			try {
				clientSocket = new Socket(host, port);
				outToServer = new DataOutputStream(clientSocket.getOutputStream());
				success = true;
			} catch (IOException e) {
				success = false;
			}
			
		}
		
		
		outToServer.writeInt(msg.length);
		outToServer.write(msg);
		outToServer.flush(); 
	}
	
	public byte [] receive() throws IOException, InterruptedException
	{
		inFromServer = new DataInputStream(clientSocket.getInputStream());
		
		int size = inFromServer.readInt();
		byte [] msg = new byte [size];
		inFromServer.read(msg, 0, size);
		
		
		outToServer.close();
		inFromServer.close();
		clientSocket.close();
		return msg;
	}
	
}