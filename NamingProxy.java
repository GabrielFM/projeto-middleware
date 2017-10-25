import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class NamingProxy extends ClientProxy implements INaming {

	private static final long serialVersionUID = 1L;
	
	
	public NamingProxy(String host, int port) {
		super(host,port);
		this.host = host;
		this.port = port;
	}

	public void bind(String serviceName, ClientProxy clientProxy) throws UnknownHostException, IOException, Throwable {
		Invocation inv = new Invocation();
		ArrayList<Object> parameters = new ArrayList<Object>();
		class Local {};
		String methodName = null;
		Requestor requestor = new Requestor(this.host, this.port);
		
		// information received from Client
		methodName = Local.class.getEnclosingMethod().getName();
		parameters.add(serviceName);
		parameters.add(clientProxy);
		
		// information sent to Requestor
		inv.setObjectId(this.getObjectId());
		inv.setHost(this.getHost());
		inv.setPort(this.getPort());
		inv.setMethodName(methodName);
		inv.setParameters(parameters);
		
		// invoke Requestor
		requestor.invoke(inv);
	}

	public ClientProxy lookup(String serviceName) throws UnknownHostException, IOException, Throwable {
		Invocation inv = new Invocation();
		Termination ter = new Termination();
		ArrayList<Object> parameters = new ArrayList<Object>();
		class Local {};
		String methodName = null;
		Requestor requestor = new Requestor(this.host, this.port);
		
		// information received from Client
		methodName = Local.class.getEnclosingMethod().getName();
		parameters.add(serviceName);
		
		// information sent to Requestor
		inv.setObjectId(this.getObjectId());
		inv.setHost(this.getHost());
		inv.setPort(this.getPort());
		inv.setMethodName(methodName);
		inv.setParameters(parameters);
		
		// invoke Requestor
		ter = requestor.invoke(inv);
		
		//@ Result sent back to Client
		return (ClientProxy) ter.getResult();
	}
	
	public ArrayList<String> list() throws UnknownHostException, IOException, Throwable {
		Invocation inv = new Invocation();
		Termination ter = new Termination();
		ArrayList<Object> parameters = new ArrayList<Object>();
		class Local {};
		String methodName = null;
		Requestor requestor = new Requestor(this.host, this.port);
		
		// information received from Client
		methodName = Local.class.getEnclosingMethod().getName();
		
		// information sent to Requestor
		inv.setObjectId(this.getObjectId());
		inv.setHost(this.getHost());
		inv.setPort(this.getPort());
		inv.setMethodName(methodName);
		inv.setParameters(parameters);
		
		// invoke Requestor
		ter = requestor.invoke(inv);
		
		//@ Result sent back to Client
		return (ArrayList<String>) ter.getResult();
	}

}
