
import java.util.concurrent.*;


public class TTT {

	public static void main(String[] args)
	{
		System.out.println("Thread main started");
		ExecutorService executorService = Executors.newFixedThreadPool(3);

		executorService.execute(new PlaySession());
		executorService.execute(new PlaySession());
		executorService.shutdown();

//		Thread t1 = new PlaySession("T1");
//		Thread t2 = new PlaySession("T2");
//
//		t1.start();
//		t2.start();

//		Thread thread1 = new Thread(new PlaySession(), "Thread 1.1");
//		Thread thread2 = new Thread(new PlaySession(), "Thread 2.1");
//		thread1.start();
//		thread2.start();



		System.out.println("Thread main finished");


	}
}
