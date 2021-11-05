package logica;

import java.io.Serializable;

public class Revision implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numero;
	private String codigoFolio;
	private String descripcion;
	
	public Revision (int num, String codF, String desc)
	{
		numero = num;
		codigoFolio = codF;
		descripcion = desc;
	}
	
	public int getNumero()
	{
		return numero;
	}
	
	public String getCodigoFolio ()
	{
		return codigoFolio;
	}
	
	public String getDescripcion ()
	{
		return descripcion;
	}
}
