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
		//System.out.println("ComLect CantLectores: "+cantLectores+" CantLectoresLeyendo: "+cantLectoresLeyendo);
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
		//System.out.println("ComLect CantLectores: "+cantLectores+" CantLectoresLeyendo: "+cantLectoresLeyendo);
	}			   
	public synchronized void terminoLectura()
	{
		//System.out.println("TermLect CantLectores: "+cantLectores+" CantLectoresLeyendo: "+cantLectoresLeyendo);
		//System.out.println(cantLectores==0);
		if (cantLectores == 0)
		{	        		
			if(cantLectoresLeyendo > 0)
			{
				//System.out.println("Dentro del segundo if"+" CantLectoresLeyendo: "+cantLectoresLeyendo);
				cantLectoresLeyendo--;
				//System.out.println("Dentro del segundo if resta"+" CantLectoresLeyendo: "+cantLectoresLeyendo);
				if(cantLectoresLeyendo == 0)
					leyendo = false;
			}	        					        	
			if (intetaEscribir)
				OKEscribir.notify();
		}
		else
			OKLeer.notify();
		//System.out.println("TermLect CantLectores: "+cantLectores+" CantLectoresLeyendo: "+cantLectoresLeyendo);
	}			    
	public synchronized void comienzoEscritura()
	{
		//System.out.println("ComEsc CantLectores: "+cantLectores+" CantLectoresLeyendo: "+cantLectoresLeyendo);
		//System.out.println("ComEsc leyendo: "+leyendo+" escribiendo: "+escribiendo);
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
		//System.out.println("ComEsc CantLectores: "+cantLectores+" CantLectoresLeyendo: "+cantLectoresLeyendo);
		//System.out.println("ComEsc leyendo: "+leyendo+" escribiendo: "+escribiendo);
	}			    
	public synchronized void terminoEscritura()
	{
		//System.out.println("TermEsc CantLectores: "+cantLectores+" CantLectoresLeyendo: "+cantLectoresLeyendo);
		escribiendo=false;
		if(cantLectores > 0)
			OKLeer.notify();
		else if(intetaEscribir)
			OKEscribir.notify();
		//System.out.println("TermEsc CantLectores: "+cantLectores+" CantLectoresLeyendo: "+cantLectoresLeyendo);
	}
}
