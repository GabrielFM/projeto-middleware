import java.io.IOException;

public class StringProcessorInvoker implements Runnable{
	private ServerRequestHandler srh;
	
	public StringProcessorInvoker() {}
	
	public StringProcessorInvoker(ServerRequestHandler srh) {
		this.srh = srh;
	}
	
	public void invoke(ClientProxy clientProxy) throws IOException, Throwable{
		ObjectPoolManager.init();
		
		while(true) {
			(new Thread(new StringProcessorInvoker(new ServerRequestHandler(clientProxy.getPort())))).start(); 
		}
	}

	@Override
	public void run() {

		byte[] msgToBeDecripted = null;
		byte[] msgMarshalled = null;
		byte[] msgToBeUnmarshalled = null;
		byte[] msgEncripted = null;
		Message msgUnmarshalled = null;
		Termination ter = new Termination();
		
		StringProcessorImpl rObj;
		// inversion loop
		try {
			Crypter crypter = new Crypter();
			crypter.init(srh);
			
			
			while (true) {
				// @ Receive Message
				try {
					msgToBeDecripted = srh.receive();
				}catch (IOException e) {
					break;
				}
				
				//@ Unmarshall received message
				msgToBeUnmarshalled = crypter.decrypt(msgToBeDecripted);
				msgUnmarshalled = Marshaller.unmarshall(msgToBeUnmarshalled);
				
				switch (msgUnmarshalled.getBody().getRequestHeader().getOperation()) {
					case "toUpper":
						// @ Invokes the remote object
						String _toUpper_str_ = (String) msgUnmarshalled.getBody().getRequestBody().getParameters().get(0);
						
						synchronized (ObjectPoolManager.class) {
							rObj = ObjectPoolManager.get();
							if (rObj == null) {
								ObjectPoolManager.class.wait();
								rObj = ObjectPoolManager.get();
							}
							
							ter.setResult(rObj.toUpper(_toUpper_str_));
							
							ObjectPoolManager.put(rObj);
							ObjectPoolManager.class.notify();
						}
						
						Message _toUpper_msgToBeMarshalled = new Message(new MessageHeader("protocolo", 0, false, 0, 0),
								new MessageBody(null, 
										null, new ReplyHeader("", 0, 0), new ReplyBody(
												ter.getResult())));
						
						// @ Marshall the response
						
						msgMarshalled = Marshaller.marshall(_toUpper_msgToBeMarshalled);
						msgEncripted = crypter.encrypt(msgMarshalled);
						// @ Send response
						srh.send(msgEncripted);
						break;
					
					case "revert":
						// @ Invokes the remote object
						String _str_ = (String) msgUnmarshalled.getBody().getRequestBody().getParameters().get(0);
						
						
						synchronized (ObjectPoolManager.class) {
							rObj = ObjectPoolManager.get();
							if (rObj == null) {
								ObjectPoolManager.class.wait();
								rObj = ObjectPoolManager.get();
							}
							
							ter.setResult(rObj.revert(_str_));
							
							ObjectPoolManager.put(rObj);
							ObjectPoolManager.class.notify();
						}
						
						Message _revert_msgToBeMarshalled = new Message(new MessageHeader("protocolo", 0, false, 0, 0),
								new MessageBody(null, 
										null, new ReplyHeader("", 0, 0), new ReplyBody(
												ter.getResult())));
						
						// @ Marshall the response
						msgMarshalled = Marshaller.marshall(_revert_msgToBeMarshalled);
						msgEncripted = crypter.encrypt(msgMarshalled);
						// @ Send response
						srh.send(msgEncripted);
						break;
						
					default:
						break;
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
	}
}
