public class NamingRecord {
	private String serviceName;
	private ClientProxy clientProxy;
	
	NamingRecord(String serviceName, ClientProxy clientProxy) {
		this.serviceName = serviceName;
		this.clientProxy = clientProxy;
	}
	
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public String getServiceName() {
		return serviceName;
	}
	
	public void setClientProxy (ClientProxy clientProxy) {
		this.clientProxy = clientProxy;
	}
	
	public ClientProxy getClientProxy() {
		return clientProxy;
	}
}
