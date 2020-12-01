package net.tce.dao.impl;

import net.tce.dao.BitacoraCompetenciaPerfilDao;
import net.tce.dao.BitacoraDomicilioDao;
import net.tce.model.BitacoraCompetenciaPerfil;
import net.tce.model.BitacoraDomicilio;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository("bitacoraCompetenciaPerfilDao")
public class BitacoraCompetenciaPerfilDaoImpl  extends PersistenceGenericDaoImpl<BitacoraCompetenciaPerfil, Object> 
implements BitacoraCompetenciaPerfilDao{
	Logger log4j = Logger.getLogger( this.getClass());

	

}
