import javax.xml.transform.Templates;

public class sample1 {
	
	public static void main(String[] args) {
		Counter c = new Counter();
//		c.inc(); 
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				for(int i=0;i<10000;i++)
					c.inc();
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			public void run() {
				for(int i=0;i<10000;i++)
					c.inc();
			}
		});
		
		t1.start();
		t2.start();
		
		System.out.println(t1.getName()+"    "+t2.getName());
		
//		Thread t3 = Thread.currentThread();
//		System.out.println(Thread.);
//		Thread.
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(c.counter);
	}

}



