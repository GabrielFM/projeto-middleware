import java.util.ArrayList;

public class NamingRepository {
	private static ArrayList<NamingRecord> namingRecords = new ArrayList<NamingRecord>();
	
	public static void addRecord(String recordName, ClientProxy clientProxy)
	{
		NamingRecord namingRecord = new NamingRecord(recordName, clientProxy);
		namingRecords.add(namingRecord);
	}
	
	public static void setNamingRecord(ArrayList<NamingRecord> newNamingRecords){
		namingRecords = newNamingRecords;
	}
	
	public static ClientProxy getNamingRecord(String serviceName)
	{
		ClientProxy clientProxy = null;
		for(int i = 0; i < namingRecords.size(); i++)
		{
			NamingRecord namingRecord = namingRecords.get(i);
			if(namingRecord.getServiceName().compareTo(serviceName) == 0)
			{
				clientProxy = namingRecord.getClientProxy();
			}
		}
		return clientProxy;
	}

	public static ArrayList<String> list(){
		ArrayList<String> serviceNames = new ArrayList<String>();
		
		for(int i = 0; i < namingRecords.size(); ++i){
			NamingRecord namingRecord = namingRecords.get(i);
			serviceNames.add(namingRecord.getServiceName());
		}
		
		return serviceNames;
	}
}
