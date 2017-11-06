import java.io.*;

public class Marshaller 
{
	public static byte[] marshall(Message msgToMarshalled) throws IOException 
	{
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
		objectStream.writeObject(msgToMarshalled);
		return byteStream.toByteArray();
	}
	
	public static Message unmarshall(byte [] msgToBeUnmarshalled) throws IOException, ClassNotFoundException 
	{
		ByteArrayInputStream byteStream = new ByteArrayInputStream(msgToBeUnmarshalled);
		ObjectInputStream objectStream = new ObjectInputStream(byteStream);
		
		return (Message) objectStream.readObject();
	}
	
	
}
