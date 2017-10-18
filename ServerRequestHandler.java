import java.io.*;
import java.net.*;
import java.util.Scanner;
public class ServerRequestHandler 
{
	private int portNumber;
	private ServerSocket welcomeSocket = null;
	private Socket connectionSocket = null;
	private InetAddress IPAddress = null;
	private int clientPort;
	
	private DataOutputStream outToClient = null;
	private DataInputStream inFromClient = null;
	
	public ServerRequestHandler(int port)
	{
		this.portNumber = port;
	}
	
	public byte [] receive() throws IOException, Throwable
	{
		
		Boolean success = false;
		while(!success) {
			try {
				welcomeSocket = new ServerSocket(portNumber);
				connectionSocket = welcomeSocket.accept();
				
				success = true;
			} catch (IOException e) {
				success = false;
			}
		}
		
		inFromClient = new DataInputStream(connectionSocket.getInputStream());
		
		int size = inFromClient.readInt();
		byte [] msg = new byte [size];
		inFromClient.read(msg, 0, size);
		
		return msg;
	}
	
	public void send(byte [] msg) throws IOException, InterruptedException
	{
		outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		
		outToClient.writeInt(msg.length);
		outToClient.write(msg);
		outToClient.flush();
		

		outToClient.close();
		inFromClient.close();
		connectionSocket.close();
		welcomeSocket.close();
	}
	
}