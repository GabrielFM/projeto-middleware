import java.io.IOException;

public class StringProcessorInvoker {
	public void invoke(ClientProxy clientProxy) throws IOException, Throwable{
		ServerRequestHandler srh = new ServerRequestHandler(clientProxy.getPort());
		byte[] msgToBeUnmarshalled = null;
		byte[] msgMarshalled = null;
		Message msgUnmarshalled = null;
		Termination ter = new Termination();
		
		//create remote object
		StringProcessorImpl rObj = new StringProcessorImpl();
		
		// inversion loop
		while (true) {
			// @ Receive Message
			msgToBeUnmarshalled = srh.receive();
			
			//@ Unmarshall received message
			msgUnmarshalled = Marshaller.unmarshall(msgToBeUnmarshalled);
			
			switch (msgUnmarshalled.getBody().getRequestHeader().getOperation()) {
				case "toUpper":
					// @ Invokes the remote object
					String _toUpper_str_ = (String) msgUnmarshalled.getBody().getRequestBody().getParameters().get(0);
					ter.setResult(rObj.toUpper(_toUpper_str_));
					
					Message _toUpper_msgToBeMarshalled = new Message(new MessageHeader("protocolo", 0, false, 0, 0),
							new MessageBody(null, 
									null, new ReplyHeader("", 0, 0), new ReplyBody(
											ter.getResult())));
					
					// @ Marshall the response
					msgMarshalled = Marshaller.marshall(_toUpper_msgToBeMarshalled);
					
					// @ Send response
					srh.send(msgMarshalled);
					break;
				
				case "revert":
					// @ Invokes the remote object
					String _str_ = (String) msgUnmarshalled.getBody().getRequestBody().getParameters().get(0);
					ter.setResult(rObj.revert(_str_));
					
					Message _revert_msgToBeMarshalled = new Message(new MessageHeader("protocolo", 0, false, 0, 0),
							new MessageBody(null, 
									null, new ReplyHeader("", 0, 0), new ReplyBody(
											ter.getResult())));
					
					// @ Marshall the response
					msgMarshalled = Marshaller.marshall(_revert_msgToBeMarshalled);
					
					// @ Send response
					srh.send(msgMarshalled);
					break;
					
				default:
					break;
			}
		}
	}
}
