import java.io.*;
import java.net.*;

public class Requestor 
{
	public Termination invoke(Invocation inv) throws UnknownHostException, IOException, Throwable
	{
		ClientRequestHandler crh = new ClientRequestHandler(inv.getHost(), inv.getPort());
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
