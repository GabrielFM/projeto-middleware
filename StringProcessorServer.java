import java.io.IOException;

public class StringProcessorServer {
	public static void main(String[] args) throws IOException, Throwable {
		// obtain instance of Naming Service
		NamingProxy namingService = new NamingProxy("localhost", 1313);
		
		StringProcessorProxy stringProcessor = new StringProcessorProxy("localhost", 2046);

		// register StringProcessor in Naming service
		namingService.bind("StringProcessor", stringProcessor);
		
		// invoker Invoker
		StringProcessorInvoker invoker = new StringProcessorInvoker();
		invoker.invoke(stringProcessor);
	}
}