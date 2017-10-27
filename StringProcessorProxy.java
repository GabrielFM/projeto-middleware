import java.io.IOException;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class StringProcessorProxy extends ClientProxy implements IStringProcessor, Serializable {
	private static final long serialVersionUID = 1L;
	private Requestor requestor;
	
	public StringProcessorProxy(String host, int port) {
		super(host,port);
		requestor = new Requestor(host, port);
	}

	public String revert(String s) throws Throwable {
		class Local {};
				
		// information received from Client
		String methodName = Local.class.getEnclosingMethod().getName();
		ArrayList<Object> parameters = new ArrayList<Object>();
		parameters.add(s);
		
		// information sent to Requestor
		Invocation inv = new Invocation();
		inv.setObjectId(this.getObjectId());
		inv.setHost(this.getHost());
		inv.setPort(this.getPort());
		inv.setMethodName(methodName);
		inv.setParameters(parameters);
		
		// invoke Requestor
		Termination ter = requestor.invoke(inv);
		
		//@ Result sent back to Client
		return (String) ter.getResult();
	}

	public String toUpper(String s) throws Throwable {
		class Local {};
		
		// information received from Client
		String methodName = Local.class.getEnclosingMethod().getName();
		ArrayList<Object> parameters = new ArrayList<Object>();
		parameters.add(s);
		
		// information sent to Requestor
		Invocation inv = new Invocation();
		inv.setObjectId(this.getObjectId());
		inv.setHost(this.getHost());
		inv.setPort(this.getPort());
		inv.setMethodName(methodName);
		inv.setParameters(parameters);
		
		// invoke Requestor
		
		Termination ter = requestor.invoke(inv);
		
		//@ Result sent back to Client
		return (String) ter.getResult();
	}

}
