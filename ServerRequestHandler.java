import java.io.*;
import java.net.*;
import java.util.Scanner;
public class ServerRequestHandler 
{
	private int portNumber;
	private String protocol;
	private ServerSocket welcomeSocket = null;
	private Socket connectionSocket = null;
	private DatagramSocket serverDatagram = null;
	private InetAddress IPAddress = null;
	private int clientPort;
	
	private DataOutputStream outToClient = null;
	private DataInputStream inFromClient = null;
	
	public ServerRequestHandler(int port)
	{
		this.portNumber = port;
		
		try {
			Scanner scanner = new Scanner(new FileReader("config.txt"));
			StringBuilder stringBuilder = new StringBuilder();
			while(scanner.hasNext()){
				stringBuilder.append(scanner.next());
			}
			scanner.close();
			protocol = stringBuilder.toString();
		} catch (FileNotFoundException e) {
			protocol = "TCP";
		}
		
	}
	
	public byte [] receive() throws IOException, Throwable
	{
		if(protocol.equals("UDP")) {
			serverDatagram = new DatagramSocket(portNumber);
			byte[] receiveData = new byte[1024];
			
			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);
			
			serverDatagram.receive(receivePacket);
			IPAddress = receivePacket.getAddress();
			clientPort = receivePacket.getPort();
			
			return receivePacket.getData();
			
		}else {
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
	}
	
	public void send(byte [] msg) throws IOException, InterruptedException
	{
		if(protocol.equals("UDP")) {
			DatagramPacket sendPacket = new DatagramPacket(msg, msg.length, IPAddress, clientPort);
			serverDatagram.send(sendPacket);
			serverDatagram.close();
			
		}else {
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
	
}