package net.tce.dao.impl;


import java.util.List;
import net.tce.dao.ModeloRscDao;
import net.tce.dto.ModeloRscDto;
import net.tce.model.ModeloRsc;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository("modeloRscDao")
public class ModeloRscDaoImpl extends 
	PersistenceGenericDaoImpl<ModeloRsc, Object> implements ModeloRscDao {

	StringBuilder sb;
	Logger log4j = Logger.getLogger( this.getClass());
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ModeloRscDto> getByModeloRsc(Long idModeloRsc) {
		log4j.debug("idModeloRsc: " + idModeloRsc );
		sb=new StringBuilder("SELECT new net.tce.dto.ModeloRscDto(")
			.append(" MRSCF.idModeloRscFase, MRSC.idModeloRsc, ") 
			.append(" MRSCF.orden,MRSCF.actividad, MRSCF.nombre, MRSCF.descripcion) ")
			.append(" FROM ModeloRscFase as MRSCF ")
			.append(" inner join MRSCF.modeloRsc as MRSC ")
			.append(" WHERE MRSC.idModeloRsc = ").append(idModeloRsc);
		return (List<ModeloRscDto>) getSession().createQuery(sb.toString()).list();
	}
}
