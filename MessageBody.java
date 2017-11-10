import java.io.*;

public class MessageBody implements Serializable
{
	private static final long serialVersionUID = 1L;
	private RequestHeader requestHeader;
	private RequestBody requestBody;
	private ReplyHeader replyHeader;
	private ReplyBody replyBody;
	
	public MessageBody(RequestHeader requestHeader, RequestBody requestBody, ReplyHeader replyHeader, ReplyBody replyBody) 
	{
		this.requestHeader = requestHeader;
		this.requestBody = requestBody;
		this.replyHeader = replyHeader;
		this.replyBody = replyBody;
	}
	
	public RequestHeader getRequestHeader()
	{
		return this.requestHeader;
	}
	
	public RequestBody getRequestBody()
	{
		return this.requestBody;
	}
	
	public ReplyHeader getReplyHeader()
	{
		return this.replyHeader;
	}
	
	public ReplyBody getReplyBody()
	{
		return this.replyBody;
	}
}
