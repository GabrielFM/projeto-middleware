import java.io.IOException;

public class StringProcessorServer {

	public static void main(String[] args) throws IOException, Throwable {
		StringProcessorInvoker invoker = new StringProcessorInvoker();
		
		// remote object
		NamingProxy namingService = new NamingProxy("localhost", 1313);
		StringProcessorProxy stringProcessor = new StringProcessorProxy("localhost", 2046);
		
		// obtain instance of Naming Service
		
		
		// register StringProcessor in Naming service
		namingService.bind("StringProcessor", stringProcessor);
		
		// invoker Invoker
		invoker.invoke(stringProcessor);
	}
}