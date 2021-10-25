package logica;

import java.sql.SQLException;

import persistencia.daos.DAORevisiones;

public class Folio {
	
	// Atributos
	private String codigo;
	private String caratula;
	private int paginas;
	private DAORevisiones revisiones;
	
	public Folio (String cod, String car, int pag){
		codigo = cod;
		caratula = car;
		paginas = pag;
		revisiones = new DAORevisiones(cod);
	}
	
	public String getCodigo() {
		return codigo;
	}

	public String getCaratula() {
		return caratula;
	}

	public int getPaginas() {
		return paginas;
	}
	
	public boolean tieneRevision(int numR) throws SQLException
	{
		return revisiones.member(codigo, numR);
	}
	
	public int cantidadRevisiones ()
	{
		return 0;
	}
	
	
	

}
