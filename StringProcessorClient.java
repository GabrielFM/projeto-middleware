import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class StringProcessorClient {
	public static void main(String[] args) throws UnknownHostException, IOException, Throwable{
		String s;
		// create an instance of Naming Service
		NamingProxy namingService = new NamingProxy("localhost", 1313);
	
		// look for StringProcessor in Naming Service 
		StringProcessorProxy stringProcessorProxy = (StringProcessorProxy) namingService.lookup("StringProcessor");
	
		int sampleSize = 10000;
		
		Random generator = new Random();
		long totalTime = 0;
		long duration;
		long startTime;
		
		for (int i= 0; i < sampleSize; i++) {
			// invoke calculator
			startTime = System.nanoTime();
			s = stringProcessorProxy.toUpper("ABCD" + i);
//			System.out.println(s);
			duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
			totalTime = totalTime + duration;
			Thread.sleep((long) (generator.nextGaussian()*0.1+10));
		}
		
		System.out.println("Total Time Duration: " + totalTime + " ms");
		System.out.println("Mean Time Duration: " + totalTime / ((double) sampleSize) + " ms");
	}
}
