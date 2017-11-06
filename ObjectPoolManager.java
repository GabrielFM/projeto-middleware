import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ObjectPoolManager {
	private static Queue<StringProcessorImpl> pool = new ConcurrentLinkedQueue<>();
	private static final int CAPACITY = 2;
	
	public static void init() {
		for (int i = 0; i < CAPACITY; i++) {
			pool.add(new StringProcessorImpl());
		}
	}
	
	public static synchronized StringProcessorImpl get() {
		try {
			return pool.remove();
		} catch(NoSuchElementException e) {
			return null;
		}
	}
	
	public static void put(StringProcessorImpl object) {
		if (pool.size() < CAPACITY) {
			pool.add(object);
		}
	}
}
