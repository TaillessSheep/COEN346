import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HelloWorld {
	public static void do_other_stuff() {
		System.out.println("ifuhe");
	}
	public static void main(String[] args) {

		ExecutorService service = Executors.newSingleThreadExecutor();
		Hello task = new Hello();
		Future<Integer> future = service.submit(task);
		
		
		try {
			Integer result = future.get();
			
			}catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		do_other_stuff(); 
		
		
		int[] a = {1, 34, 563, 12, 65, 68};
		System.out.println(a);
		System.out.println(a);
		
	}


}

class Hello implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("haha");
		return new Integer(0);
	}

}
