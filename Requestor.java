import java.io.*;
import java.net.*;

public class Requestor 
{
	
	private ClientRequestHandler crh;
	public Requestor(String host, int port)
	{
		crh = new ClientRequestHandler(host, port);
	}
	
	public Termination invoke(Invocation inv) throws UnknownHostException, IOException, Throwable
	{
		Termination termination = new Termination();
		byte[] msgMarshalled;
		byte[] msgToBeUnmarshalled;
		
		RequestHeader requestHeader = new RequestHeader("", 0, true, 0, inv.getMethodName());
		RequestBody requestBody = new RequestBody(inv.getParameters());
		MessageHeader messageHeader = new MessageHeader("MIOP", 0, false, 0, 0);
		MessageBody messageBody = new MessageBody(requestHeader, requestBody, null, null);
		Message msgToBeMarshalled = new Message(messageHeader,messageBody);
		
		msgMarshalled = Marshaller.marshall(msgToBeMarshalled);
		
		crh.send(msgMarshalled);
		msgToBeUnmarshalled = crh.receive();
		
		Message msgUnmarshalled = Marshaller.unmarshall(msgToBeUnmarshalled);
		
		termination.setResult(msgUnmarshalled.getBody().getReplyBody().getOperationResult());
		
		return termination;
		
	}
	
}
