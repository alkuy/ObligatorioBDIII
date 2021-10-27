package logica.poolConexiones;

import java.util.LinkedList;

public class Cola {

	private LinkedList lista;
	private static Cola instancia; // Singleton
	
	private Cola ()
	{ 
		lista = new LinkedList(); 
	}
	
	public synchronized static Cola getInstancia ()
	{ 
		if (instancia == null)
			instancia = new Cola(); // Singleton
		return instancia;
	}
	
	public synchronized Integer quitar()
	{
		while (lista.isEmpty())
		{
			// espero hasta que la cola no esté vacía
			try
			{ 
				this.wait(); 
			}
			catch (InterruptedException e)
			{ 
				// no hago nada }
			}
		}	
		// quito un carácter de la cola
		return (Integer) lista.removeFirst();
	}
	
	public synchronized void insertar (Integer num)
	{
		// inserto un valor en la cola y notifico a
		// algún consumidor que esté bloqueado esperando
		// que la cola no esté vacía
		lista.add(num);
		this.notify();
	}
}//Fin de la clase cola
