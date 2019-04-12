package shared;

import java.util.LinkedList;

/**
 * A synchronized buffer.
 *
 * @author Oscar Strandmark
 *
 * @param <T> Any type.
 */
public class Buffer<T> {
	private LinkedList<T> buffer = new LinkedList<T>();

	public synchronized void put(T obj) {
		buffer.addLast(obj);
		notifyAll();
	}

	public synchronized T get() throws InterruptedException {
		while(buffer.isEmpty()) {
			wait();
		}
		return buffer.removeFirst();
	}

	public int size() {
		return buffer.size();
	}
}
