import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.omg.CORBA.portable.UnknownException;

public class NamingImpl implements INaming
{
	private NamingRepository namingRepository = new NamingRepository();
	
	public void bind(String serviceName, ClientProxy clientProxy) throws UnknownException, IOException, Throwable
	{
		namingRepository.addRecord(serviceName, clientProxy);
	}
	
	public ClientProxy lookup(String serviceName) throws UnknownException, IOException, Throwable
	{
		ClientProxy clientProxy = namingRepository.getNamingRecord(serviceName);
		
		if(clientProxy == null)
		{
			throw new UnknownHostException();
		}
		return clientProxy;
		
	}
	
	public ArrayList<String> list() throws UnknownHostException, IOException, Throwable{
		ArrayList<String> names = namingRepository.list();
		
		return names;
	}
	

	
	
}
