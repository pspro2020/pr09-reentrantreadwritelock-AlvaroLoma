package reentrantreadwritelock;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Almacen implements Runnable {

	private List<Producto> stock= new ArrayList<Producto>();
	private final ReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(true);
	private final Lock readLock= reentrantReadWriteLock.readLock();
	private final Lock writeLock= reentrantReadWriteLock.writeLock();
	
	
//	public Almacen() {
//		//primerosProductos();
//	}
//	private void primerosProductos() {
//		for (int i = 0; i < 5; i++) {
//			stock.add(crearProducto());
//		}
//		
//	}
	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {

			try {
				TimeUnit.MILLISECONDS.sleep(500);
				añadirProducto(crearProducto());
			} catch (InterruptedException e) {
				
				return;
			}
		}
		
		
	}
	private void añadirProducto(Producto producto) {
		writeLock.lock();
		try {
			
			stock.add(producto);
			System.out.printf("El producto %d fue añadido a las %s \n",producto.getId()
					,DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));
		} finally {
			writeLock.unlock();
		}
		
	}
	private Producto crearProducto() {
		
		return new Producto(new Random().nextInt(3)+1);
	}
	public void consultarStock(int num) {
		 int contador=0;
		readLock.lock();
		try {
			for(Producto producto: stock) {
				if(producto.getId()==num) {
					contador++;
				}
			}
			System.out.printf("El mozo %d ha consultado el stock y hay %d unidades del producto %d a las %s \n",
					num,contador,num,DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));
			
		} finally {
			readLock.unlock();
		}
		
	}
}
