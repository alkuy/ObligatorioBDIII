package logica.poolConexiones;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;

public class MonitorPool implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int cantLectores = 0;
	private int cantLectoresLeyendo = 0;
	private boolean escribiendo = false;
	private boolean leyendo = false;
	private boolean intetaEscribir = false;
	private Condition OKLeer;
	private Condition OKEscribir;
	private static MonitorPool instancia; // Singleton
	
	public synchronized static MonitorPool getInstancia ()
	{ 
		if (instancia == null)
			instancia = new MonitorPool(); // Singleton
		return instancia;
	}
	
	public synchronized void comienzoLectura()
	{
		while(escribiendo)
		{ 
			
			try 
			{
				cantLectores++;
				OKLeer.wait();
				if(cantLectores > 0)
					cantLectores--;
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}  
		}		       
		leyendo = true;
		cantLectoresLeyendo++;
	}			   
	public synchronized void terminoLectura()
	{
		if (cantLectores == 0 )
		{	        		
			if(cantLectoresLeyendo > 0)
			{
				cantLectoresLeyendo--;
				if(cantLectoresLeyendo == 0)
					leyendo = false;
			}	        					        	
			if (intetaEscribir)
				OKEscribir.notify();
		}
		else
			OKLeer.notify();
	}			    
	public synchronized void comienzoEscritura()
	{
		while (leyendo || escribiendo)
		{
			try 
			{
				intetaEscribir = true;
				OKEscribir.wait();
				intetaEscribir = false;
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		escribiendo=true;			        
	}			    
	public synchronized void terminoEscritura()
	{
		escribiendo=false;
		if(cantLectores > 0)
			OKLeer.notify();
		else if(intetaEscribir)
			OKEscribir.notify();
	}
}
