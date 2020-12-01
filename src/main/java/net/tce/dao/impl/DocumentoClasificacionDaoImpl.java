package net.tce.dao.impl;

import java.util.List;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Query;
import net.tce.dao.DocumentoClasificacionDao;
import net.tce.dto.DocumentoClasificacionDto;
import net.tce.model.DocumentoClasificacion;
import net.tce.util.Constante;
import net.tce.util.DBInterpreter;

@Repository("docClasificacionDao")
public class DocumentoClasificacionDaoImpl extends PersistenceGenericDaoImpl<DocumentoClasificacion, Object> 
implements DocumentoClasificacionDao{
	
	Logger log4j = Logger.getLogger( DocumentoClasificacionDaoImpl.class );

	@Inject
	DBInterpreter dbInterpreter;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<DocumentoClasificacionDto> getDtos(){
		log4j.info("<getDtos> Inicio...");
		
		StringBuilder htSql = new StringBuilder(
			"select new net.tce.dto.DocumentoClasificacionDto(")
			//.append("to_char(DOCUMENTOCLASIFICACION.idDocumentoClasificacion), ")
			.append( dbInterpreter.fnToChar("DOCUMENTOCLASIFICACION.idDocumentoClasificacion",0)).append( ", ")
//			.append("to_char(DOCUMENTOCLASIFICACION.area.idArea), ")
			.append( dbInterpreter.fnToChar("DOCUMENTOCLASIFICACION.area.idArea",0)).append( ", ")
//			.append("to_char(DOCUMENTOCLASIFICACION.estatusClasificacion), ")
			.append( dbInterpreter.fnToChar("DOCUMENTOCLASIFICACION.estatusClasificacion",0)).append( ", ")
			.append(" DOCUMENTOCLASIFICACION.textoClasificacion ")
			.append(") ")
			.append("from DocumentoClasificacion as DOCUMENTOCLASIFICACION ") ;
		
		Query query = getSession().createQuery(htSql.toString());
		
		List<DocumentoClasificacionDto> lsDtos = (List<DocumentoClasificacionDto>) query.list();

		log4j.info("<getPartial> list size :" + lsDtos.size());
		log4j.info("<getPartial> Fin...");
		return lsDtos;
	}

	/**
     * Remueve todos los objetos
	 *@param none
	 */
	@Transactional
	public void deleteAllRecords() {
		String hql = String.format("delete from %s","DocumentoClasificacion");
		Query query = this.getSession().createQuery(hql);	
		query.executeUpdate();
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<DocumentoClasificacion> getDocs() {
		return this.getSession().createQuery(new StringBuilder(" from DocumentoClasificacion ")
											.append(" where estatusClasificacion in (")
											.append(Constante.CLASSIFICATION_STATUS_IN_CLAUSE).
											append(") ").toString()).list();
	}
	
	
	
	
	/**
	 * Se obtiene una lista de los docs que se van a actualizar en la tabla DocumentoClasificacion 
	 * y/o confirmar automaticamente 
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<DocumentoClasificacion> getDocsNoSql() {
		
		return this.getSession().createSQLQuery(
		//para persona
		new StringBuilder(" select DC.* ")
		.append(" from  documento_clasificacion DC ")
		.append(" inner join persona PER  on DC.id_persona=PER.id_persona ")
		.append(" inner join estatus_inscripcion EDOI ")
		.append(" on PER.id_estatus_inscripcion=EDOI.id_estatus_inscripcion ")
		.append(" where EDOI.id_estatus_inscripcion=")
		.append(Constante.ESTATUS_INSCRIPCION_PUBLICADO)
		.append(" and PER.clasificado=true ")
		.append(" and PER.fecha_modificacion > DC.fecha_modificacion ")
		
		.append(" UNION ")
		
		//Para posicion
		.append("select DC.* ")
		.append(" from  documento_clasificacion DC ")
		.append(" inner join posicion POS  on DC.id_posicion=POS.id_posicion ")
		.append(" inner join estatus_posicion EDOP ")
		.append(" on POS.id_estatus_posicion=EDOP.id_estatus_posicion ")
		.append("  where EDOP.id_estatus_posicion=")
		.append(Constante.ESTATUS_POSICION_PUBLICADA)
		.append(" and POS.clasificado=true ")
		.append(" and POS.fecha_modificacion > DC.fecha_modificacion ").toString())
	    .addEntity(DocumentoClasificacion.class).list();
				
	}

	/**
	 * Se obtiene una lista de los docs que se han confirmado manualmente
	 * documento_clasificacion
	 */
	@SuppressWarnings("unchecked")
	public List<DocumentoClasificacion> getDocsConfir() {
		return this.getSession().createSQLQuery(
				//para persona
				new StringBuilder(" select DC.* ")
				.append(" from  documento_clasificacion DC ")
				.append(" inner join persona PER  on DC.id_persona=PER.id_persona ")
				.append(" inner join estatus_inscripcion EDOI ")
				.append(" on PER.id_estatus_inscripcion=EDOI.id_estatus_inscripcion ")
				.append(" where EDOI.id_estatus_inscripcion=")
				.append(Constante.ESTATUS_INSCRIPCION_PUBLICADO)
				.append(" and PER.clasificado=true ")
				.append(" and DC.estatus_clasificacion in ( ")
				.append(Constante.ESTATUS_CLAS_VERIFICADO_MODELO).append(",")
				.append(Constante.ESTATUS_CLAS_VERIFICADO_NOMODELO).append(" ) ")
				.append(" and DC.sincronizado=false ")
				
				.append(" UNION ")
				
				//Para posicion
				.append("select DC.* ")
				.append(" from  documento_clasificacion DC ")
				.append(" inner join posicion POS  on DC.id_posicion=POS.id_posicion ")
				.append(" inner join estatus_posicion EDOP ")
				.append(" on POS.id_estatus_posicion=EDOP.id_estatus_posicion ")
				.append("  where EDOP.id_estatus_posicion=")
				.append(Constante.ESTATUS_POSICION_PUBLICADA)
				.append(" and POS.clasificado=true ")
				.append(" and DC.estatus_clasificacion in ( ")
				.append(Constante.ESTATUS_CLAS_VERIFICADO_MODELO).append(",")
				.append(Constante.ESTATUS_CLAS_VERIFICADO_NOMODELO).append(" ) ")
				.append(" and DC.sincronizado=false ").toString())
			    .addEntity(DocumentoClasificacion.class).list();
	}
	
	
	
	                                
	
}
