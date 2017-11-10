import java.io.*;

public class Message implements Serializable
{
	private static final long serialVersionUID = 1L;
	private MessageHeader header;
	private MessageBody body;
	
	public Message(MessageHeader header, MessageBody body)
	{
		this.header = header;
		this.body = body;
	}
	
	public MessageHeader getHeader()
	{
		return this.header;
	}
	
	public MessageBody getBody()
	{
		return this.body;
	}
}
