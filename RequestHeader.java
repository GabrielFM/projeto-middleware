import java.io.*;

public class RequestHeader implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String context;
	private int requestId;
	private boolean responseExpected;
	private int objectKey;
	private String operation;
	
	public RequestHeader(String context, int requestId, boolean responseExpected, int objectKey, String operation) 
	{
		this.context = context;
		this.requestId = requestId;
		this.responseExpected = responseExpected;
		this.objectKey = objectKey;
		this.operation = operation;
	}

	public String getContext() 
	{
		return context;
	}

	public int getRequestId() 
	{
		return requestId;
	}

	public boolean isResponseExpected() 
	{
		return responseExpected;
	}

	public int getObjectKey() 
	{
		return objectKey;
	}

	public String getOperation() 
	{
		return operation;
	}
}
