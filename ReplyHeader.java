import java.io.*;
public class ReplyHeader implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String serviceContext;
	private int requestId;
	private int replyStatus;
	
	public ReplyHeader(String serviceContext, int requestId, int replyStatus) {
		this.serviceContext = serviceContext;
		this.requestId = requestId;
		this.replyStatus = replyStatus;
	}

	public String getServiceContext() {
		return serviceContext;
	}

	public int getRequestId() {
		return requestId;
	}

	public int getReplyStatus() {
		return replyStatus;
	}
	
}
