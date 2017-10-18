import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientRequestHandler 
{
	private String host;
	private int port;
	private String protocol;
	private Socket clientSocket = null;
	private DatagramSocket clientDatagram = null;
	private DataOutputStream outToServer = null;
	private DataInputStream inFromServer = null;
	
	public ClientRequestHandler(String host, int port)
	{
		this.host = host;
		this.port = port;
		
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
	
	public void send(byte [] msg) throws IOException, InterruptedException
	{
		
		if(protocol.equals("UDP")) {
			clientDatagram = new DatagramSocket(); 
			
			InetAddress IPAddress = InetAddress.getByName(host);
			
			DatagramPacket sendPacket = new DatagramPacket(msg, msg.length, IPAddress, port);
			
			clientDatagram.send(sendPacket);
			
		}else{
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
		
		
	}
	
	public byte [] receive() throws IOException, InterruptedException
	{
		if(protocol.equals("UDP")) {
			byte[] tempData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(tempData,	tempData.length);
			clientDatagram.receive(receivePacket);		
			
			clientDatagram.close();
			return new String(receivePacket.getData()).trim().getBytes();	
		}else {
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
	
}