import java.io.IOException;
import java.util.*;

public class NamingInvoker implements Runnable 
{
	private ServerRequestHandler srh;
	private NamingImpl namingImpl = new NamingImpl();
	
	public NamingInvoker() {}
		
	public NamingInvoker(ServerRequestHandler srh) {
		this.srh = srh;
	}
	
	public void invoke(int port) throws IOException, Throwable{
		while(true) {
			(new Thread(new NamingInvoker(new ServerRequestHandler(port)))).start(); 
		}
		
	}

	@Override
	public void run() {
		byte[] rcvBuffer, sndBuffer;
		Message rcvMessage, sndMessage;
		String serviceName;
		ClientProxy clientProxy;
		Termination terminator = new Termination();
		
		try {
			while(true)
			{
				try {
					rcvBuffer = srh.receive();
				} catch (IOException e) {
					break;
				}
				rcvMessage = Marshaller.unmarshall(rcvBuffer);
				ArrayList<Object> params = rcvMessage.getBody().getRequestBody().getParameters();
				
				switch(rcvMessage.getBody().getRequestHeader().getOperation())
				{
					case "bind":
						serviceName = params.get(0).toString();
						clientProxy = (ClientProxy) params.get(1);
						
						namingImpl.bind(serviceName, clientProxy);
						terminator.setResult(serviceName);
						
						sndMessage = new Message(
								new MessageHeader("MIOP", 0, false, 0, 0), 
								new MessageBody(null, null, new ReplyHeader("", 0, 0), new ReplyBody(
										terminator.getResult()))
							);
						
						sndBuffer = Marshaller.marshall(sndMessage);
						srh.send(sndBuffer);
						break;
					
					case "lookup":
						serviceName = params.get(0).toString();
						clientProxy = namingImpl.lookup(serviceName);
						
						terminator.setResult(clientProxy);
						
						sndMessage = new Message(
								new MessageHeader("MIOP", 0, false, 0, 0), 
								new MessageBody(null, null, new ReplyHeader("", 0, 0), new ReplyBody(
										terminator.getResult()))
							);
						
						sndBuffer = Marshaller.marshall(sndMessage);
						srh.send(sndBuffer);
						break;
						
					case "list":
						terminator.setResult(namingImpl.list());
						
						sndMessage = new Message(
								new MessageHeader("MIOP", 0, false, 0, 0), 
								new MessageBody(null, null, new ReplyHeader("", 0, 0), new ReplyBody(
										terminator.getResult()))
							);
						
						sndBuffer = Marshaller.marshall(sndMessage);
						
						srh.send(sndBuffer);
						break;
						
					default:
						break;
				}
			}
		}catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
