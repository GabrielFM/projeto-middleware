import java.io.*;
import java.net.*;

public class Requestor implements Serializable
{
	private static final long serialVersionUID = 1L;
	private ClientRequestHandler crh;
	
	public Requestor(String host, int port)
	{
		crh = new ClientRequestHandler(host, port);
	}
	
	public Termination invoke(Invocation inv) throws UnknownHostException, IOException, Throwable
	{
		RequestHeader requestHeader = new RequestHeader("", 0, true, 0, inv.getMethodName());
		RequestBody requestBody = new RequestBody(inv.getParameters());
		MessageHeader messageHeader = new MessageHeader("MIOP", 0, false, 0, 0);
		MessageBody messageBody = new MessageBody(requestHeader, requestBody, null, null);
		Message msgToBeMarshalled = new Message(messageHeader,messageBody);
		
		byte[] msgMarshalled = Marshaller.marshall(msgToBeMarshalled);
		crh.send(msgMarshalled);
		
		byte[] msgToBeUnmarshalled = crh.receive();
		
		Message msgUnmarshalled = Marshaller.unmarshall(msgToBeUnmarshalled);
		
		Termination termination = new Termination();
		termination.setResult(msgUnmarshalled.getBody().getReplyBody().getOperationResult());
		
		return termination;
	}
}
