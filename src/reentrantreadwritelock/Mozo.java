package reentrantreadwritelock;

import java.util.concurrent.TimeUnit;

public class Mozo implements Runnable {
	
	private Almacen almacen;
	private int num;

	public Mozo(Almacen almacen, int i) {
		this.almacen = almacen;
		num=i;
	}

	@Override
	public void run() {
	
		
		while(!Thread.currentThread().isInterrupted()) {
			
			try {
				TimeUnit.SECONDS.sleep(2);
				almacen.consultarStock(num);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				return;
			}
		}
		
	}

}
