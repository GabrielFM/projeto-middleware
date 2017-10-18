import java.util.ArrayList;


public class StringProcessorProxy extends ClientProxy implements IStringProcessor {

	private static final long serialVersionUID = 1L;
	
	public StringProcessorProxy(String host, int port) {
		super(host,port);
	}

	public String revert(String s) throws Throwable {
		Invocation inv = new Invocation();
		Termination ter = new Termination();
		ArrayList<Object> parameters = new ArrayList<Object>();
		class Local {};
		String methodName = null;
		Requestor requestor = new Requestor();
		
		// information received from Client
		methodName = Local.class.getEnclosingMethod().getName();
		parameters.add(s);
		
		// information sent to Requestor
		inv.setObjectId(this.getObjectId());
		inv.setHost(this.getHost());
		inv.setPort(this.getPort());
		inv.setMethodName(methodName);
		inv.setParameters(parameters);
		
		// invoke Requestor
		ter = requestor.invoke(inv);
		
		//@ Result sent back to Client
		return (String) ter.getResult();
	}

	public String toUpper(String s) throws Throwable {
		Invocation inv = new Invocation();
		Termination ter = new Termination();
		ArrayList<Object> parameters = new ArrayList<Object>();
		class Local {};
		String methodName = null;
		Requestor requestor = new Requestor();
		
		// information received from Client
		methodName = Local.class.getEnclosingMethod().getName();
		parameters.add(s);
		
		// information sent to Requestor
		inv.setObjectId(this.getObjectId());
		inv.setHost(this.getHost());
		inv.setPort(this.getPort());
		inv.setMethodName(methodName);
		inv.setParameters(parameters);
		
		// invoke Requestor
		ter = requestor.invoke(inv);
		
		//@ Result sent back to Client
		return (String) ter.getResult();
	}

}
