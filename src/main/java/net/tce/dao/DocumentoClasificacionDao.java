package net.tce.dao;


import java.util.List;
import net.tce.model.DocumentoClasificacion;

public interface DocumentoClasificacionDao extends PersistenceGenericDao<DocumentoClasificacion, Object> {

	void deleteAllRecords();
	List<DocumentoClasificacion>  getDocs();
	List<DocumentoClasificacion> getDocsNoSql();
	List<DocumentoClasificacion> getDocsConfir();

}
