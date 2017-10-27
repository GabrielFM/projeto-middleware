import java.util.*;
// Felipe
// Têtê Laporte
// Angola
public class NamingInvoker 
{
	private NamingImpl namingImpl = new NamingImpl();
	
	public void invoke(int port) throws Throwable
	{
		ServerRequestHandler srh = new ServerRequestHandler(port);
		byte[] rcvBuffer, sndBuffer;
		Message rcvMessage, sndMessage;
		String serviceName;
		ClientProxy clientProxy;
		Termination terminator = new Termination();
		
		while(true)
		{
			rcvBuffer = srh.receive();
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
			
			srh.close();
		}
	}
}
