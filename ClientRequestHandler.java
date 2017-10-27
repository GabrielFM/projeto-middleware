import java.io.*;
import java.net.*;

public class ClientRequestHandler implements Serializable, Cloneable
{
	private static final long serialVersionUID = 1L;
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
	
	private void init() throws IOException {
		this.clientSocket = new Socket(this.host, this.port);
		this.outToServer = new DataOutputStream(clientSocket.getOutputStream());
		this.inFromServer = new DataInputStream(clientSocket.getInputStream());
	}
	
	public void send(byte [] msg) throws IOException
	{	
		if(clientSocket == null || !clientSocket.isConnected()) {
			init();
		}
		
		outToServer.writeInt(msg.length);
		outToServer.write(msg);
		outToServer.flush(); 
	}
	
	public byte [] receive() throws IOException, InterruptedException
	{		
		int size = inFromServer.readInt();
		byte [] msg = new byte [size];
		inFromServer.read(msg, 0, size);
		
		return msg;
	}
	
	public void close() throws IOException {
		outToServer.close();
		inFromServer.close();
		clientSocket.close();
	}
}