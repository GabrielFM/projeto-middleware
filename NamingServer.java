import java.io.IOException;

public class NamingServer {
	
	public static void main(String[] args) throws IOException, Throwable {
		NamingInvoker invoker = new NamingInvoker();
	
		// invoker Invoker
		invoker.invoke(1313);
	}
}
