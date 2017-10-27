import java.util.ArrayList;

public class NamingRepository {
	private ArrayList<NamingRecord> namingRecords = new ArrayList<NamingRecord>();
	
	public void addRecord(String recordName, ClientProxy clientProxy)
	{
		NamingRecord namingRecord = new NamingRecord(recordName, clientProxy);
		namingRecords.add(namingRecord);
	}
	
	public void setNamingRecord(ArrayList<NamingRecord> namingRecord){
		this.namingRecords = namingRecord;
	}
	
	public ClientProxy getNamingRecord(String serviceName)
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

	public ArrayList<String> list(){
		ArrayList<String> serviceNames = new ArrayList<String>();
		
		for(int i = 0; i < namingRecords.size(); ++i){
			NamingRecord namingRecord = namingRecords.get(i);
			serviceNames.add(namingRecord.getServiceName());
		}
		
		return serviceNames;
	}
}
