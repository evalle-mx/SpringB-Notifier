package net.tce.assembler;

import org.apache.log4j.Logger;
import net.tce.dto.ModeloRscDto;
import net.tce.model.ModeloRscPos;
import net.tce.model.PerfilPosicion;
import net.tce.model.ModeloRscPosFase;
import net.tce.util.Constante;
import net.tce.util.DateUtily;

public class TrackingAssembler extends CommonAssembler {

	Logger log4j = Logger.getLogger( this.getClass());

	ModeloRscPosFase modeloRscPosFaseOut;
	
	/* -----------------  IMPLEMENTACION DE PLANTILLA ------------------- */
	/**
	 * Obtiene objeto (MODEL) de ModeloRscPosFase a partir de un ModeloRscFaseDto
	 * @param model
	 * @param trackPlantillaDto
	 * @return
	 */
	public ModeloRscPosFase getModeloRscPosFase(ModeloRscPosFase model,
												ModeloRscDto modeloRscDto) throws Exception{
		if(model==null){
			model = new ModeloRscPosFase();
		}
		
		if(modeloRscDto.getOrden()!=null){
			model.setOrden(new Short(modeloRscDto.getOrden()));
		}
		if(modeloRscDto.getActividad()!=null){
			model.setActividad(new Short(modeloRscDto.getActividad()));
		}
		
		if(modeloRscDto.getDias()!=null){
			model.setDias(new Short(modeloRscDto.getDias()));
		}
		
		if(modeloRscDto.getNombre()!=null){
			model.setNombre(modeloRscDto.getNombre());
		}
		if(modeloRscDto.getDescripcion()!=null){
			model.setDescripcion(modeloRscDto.getDescripcion());
		}
		
		if(modeloRscDto.getFechaInicio()!=null){
			log4j.debug("fecha Inicio (DTO):" + modeloRscDto.getFechaInicio() );
			java.util.Date fIni = DateUtily.string2Date(modeloRscDto.getFechaInicio().substring(0,10), Constante.DATE_FORMAT);
			log4j.debug("fIni:" + fIni );
			model.setFechaInicio(fIni);
		}
		if(modeloRscDto.getFechaFin()!=null){
			log4j.debug("fecha FIN (DTO):" + modeloRscDto.getFechaFin() );
			java.util.Date fFin = DateUtily.string2Date(modeloRscDto.getFechaFin().substring(0,10), Constante.DATE_FORMAT);
			log4j.debug("fFin):" + fFin );
			model.setFechaFin( fFin );
		}
		/* Los booleanos se insertan por FALSE por defecto a menos que sea TRUE en el Dto */
		model.setSubirArchivo(modeloRscDto.getSubirArchivo()!=null?modeloRscDto.getSubirArchivo().equals("1")?true:false:false);
		model.setBajarArchivo(modeloRscDto.getBajarArchivo()!=null?modeloRscDto.getBajarArchivo().equals("1")?true:false:false);
		model.setEditarComentario(modeloRscDto.getEditarComentario()!=null?modeloRscDto.getEditarComentario().equals("1")?true:false:false);
		
		return model;
	}
	
	/**
	 * Obtiene un objeto (MODEL) de Esquema_perfil a partir de un EsquemaPerfilDto
	 * @param trackDto
	 * @param esqPerfil
	 * @return
	 */
	public ModeloRscPos getModeloRscPos(ModeloRscDto modeloRscDto,
			ModeloRscPos modeloRscPos) {
		
		if(modeloRscPos==null){
			modeloRscPos = new ModeloRscPos();
		}
//		if(modeloRscDto.getIdEsquemaPerfil() !=null){
//			esquemaPerfil.setIdEsquemaPerfil(new Long(modeloRscDto.getIdEsquemaPerfil()));
//		}
		if(modeloRscDto.getIdPerfilPosicion()!=null){
			PerfilPosicion ppos = new PerfilPosicion();
			ppos.setIdPerfilPosicion(new Long(modeloRscDto.getIdPerfilPosicion()));
			modeloRscPos.setPerfilPosicion(ppos);
		}
		if(modeloRscDto.getNombre()!=null){
			modeloRscPos.setNombre(modeloRscDto.getNombre());
		}
		if(modeloRscDto.getDescripcion()!=null){
			modeloRscPos.setDescripcion(modeloRscDto.getDescripcion());
		}
		if(modeloRscDto.getActivo()!=null){
			modeloRscPos.setActivo( modeloRscDto.getActivo().equals("1")?true:false );
		}
		if(modeloRscDto.getMonitor()!=null){
			modeloRscPos.setMonitor(modeloRscDto.getMonitor().equals("1")?true:false );
		}

		log4j.debug("esquemaPerfil: id="+modeloRscPos.getIdModeloRscPos()
				+", idPerfilPos="+modeloRscPos.getPerfilPosicion()
				+", nombre="+modeloRscPos.getNombre()
				+", descripcion="+modeloRscPos.getDescripcion()
				+", activo="+modeloRscPos.isActivo());
		return modeloRscPos;
	}
	

	/**
	 * Obtiene un nuevo objeto ModeloRscPosFase a partir de un objeto ModeloRscPosFase
	 * @param modeloRscPosFaseIn
	 * @return
	 */
	public ModeloRscPosFase getModeloRscPosFase(ModeloRscPosFase modeloRscPosFaseIn) {
		modeloRscPosFaseOut=new ModeloRscPosFase();
		
		if(modeloRscPosFaseIn.getNombre() != null){
			modeloRscPosFaseOut.setNombre(modeloRscPosFaseIn.getNombre());
		}
		if(modeloRscPosFaseIn.getDescripcion() != null){
			modeloRscPosFaseOut.setDescripcion(modeloRscPosFaseIn.getDescripcion());
		}
		modeloRscPosFaseOut.setOrden(modeloRscPosFaseIn.getOrden());
		modeloRscPosFaseOut.setActividad(modeloRscPosFaseIn.getActividad());
		
		if(modeloRscPosFaseIn.getDias() != null){
			modeloRscPosFaseOut.setDias(modeloRscPosFaseIn.getDias());
		}
		
		if(modeloRscPosFaseIn.getFechaInicio() != null){
			modeloRscPosFaseOut.setFechaInicio(modeloRscPosFaseIn.getFechaInicio());
		}
		
		if(modeloRscPosFaseIn.getFechaFin() != null){
			modeloRscPosFaseOut.setFechaFin(modeloRscPosFaseIn.getFechaFin());
		}
		modeloRscPosFaseOut.setSubirArchivo(modeloRscPosFaseIn.isSubirArchivo());
		modeloRscPosFaseOut.setBajarArchivo(modeloRscPosFaseIn.isBajarArchivo());
		modeloRscPosFaseOut.setEditarComentario(modeloRscPosFaseIn.isEditarComentario());		
		modeloRscPosFaseOut.setActivo(modeloRscPosFaseIn.isActivo());
		
		return modeloRscPosFaseOut;
	}
}
