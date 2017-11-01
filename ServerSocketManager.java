import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class ServerSocketManager {
	private static Map <Integer, ServerSocket> lookUpMap = new HashMap <>();
	
	public static ServerSocket get (int port) throws IOException {
		
		if(lookUpMap.containsKey(port)) {
			return lookUpMap.get(port);
		}
		
		ServerSocket socket = new ServerSocket(port);
		lookUpMap.put(port, socket);
		return socket;
	}
}
