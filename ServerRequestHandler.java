import java.io.*;
import java.net.*;

public class ServerRequestHandler implements Cloneable
{
	private int port;
	private ServerSocket welcomeSocket = null;
	private Socket connectionSocket = null;
	private DataOutputStream outToClient = null;
	private DataInputStream inFromClient = null;
	
	public ServerRequestHandler(int port)
	{
		this.port = port;
	}
	
	private void init() throws IOException {		
		welcomeSocket = new ServerSocket(this.port);
		connectionSocket = welcomeSocket.accept();
		inFromClient = new DataInputStream(connectionSocket.getInputStream());
		outToClient = new DataOutputStream(connectionSocket.getOutputStream());
	}
	
	public byte [] receive() throws IOException, Throwable
	{
		if (connectionSocket == null || connectionSocket.isClosed()) {
			init();
		}
		
		int size = inFromClient.readInt();		
		
		byte [] msg = new byte [size];
		inFromClient.read(msg, 0, size);
		
		return msg;
	}
	
	public void send(byte [] msg) throws IOException, InterruptedException
	{		
		outToClient.writeInt(msg.length);
		outToClient.write(msg);
		outToClient.flush();
	}
	
	public void close() throws IOException {
		outToClient.close();
		inFromClient.close();
		connectionSocket.close();
		welcomeSocket.close();
		connectionSocket = null;
	}
}
