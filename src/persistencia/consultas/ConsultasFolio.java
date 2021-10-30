package persistencia.consultas;

public class ConsultasFolio {

	// Insertar un folio
	public String InsertarFolio() {
		return "Insert into Folios values(?,?,?);";
	}
	
	// Eliminar un folio 
	public String EliminarFolio() {
		return "delete from Folios where codigo = (?)";
	}
	
	// Listar todos los folios ordenados por codigo
	// Tambien lo usaremos de manera auxiliar para chequear que existe al menos un folio
	public String ListarFolios() {
		return "Select * from Folios order by codigo;";
	}
	
	// Listar el folio con mas revisiones, junto con su cantidad
	public String FolioMasRevisado() {
		return "Select f.*, count(r.codigoFolio) as Cantidad from folios as f, revisiones as r where f.codigo = r.codigoFolio "
				+ "group by r.codigoFolio order by count(r.codigoFolio) desc limit 1;";
	}
	
	// Consulta auxiliar para chequear que un folio este registrado
	public String ExisteFolio() {
		return "Select * from folios where codigo = (?);";
	}
}
