import java.io.*;
public class ReplyBody implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object operationResult;
	
	public ReplyBody(Object operationResult) {
		this.operationResult = operationResult;
	}

	public Object getOperationResult() {
		return operationResult;
	}
	
}
