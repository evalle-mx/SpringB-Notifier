package net.tce.admin.service.impl;


import java.util.Iterator;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.tce.admin.service.BitacoraTrackingService;
import net.tce.dao.BitacoraFaseDao;
import net.tce.dao.BitacoraModelorscDao;
import net.tce.dao.BitacoraMonitorDao;
import net.tce.dao.BitacoraTrackCandDao;
import net.tce.dao.BitacoraTrackContDao;
import net.tce.dao.BitacoraTrackDao;
import net.tce.dao.BitacoraTrackMonDao;
import net.tce.dao.BitacoraTrackPostDao;
import net.tce.dao.BitacoraTrackRecdrioDao;
import net.tce.dto.BtcRecdrioDto;
import net.tce.dto.BtcTrackingDto;
import net.tce.dto.MensajeDto;
import net.tce.model.BitacoraFase;
import net.tce.model.BitacoraModelorsc;
import net.tce.model.BitacoraMonitor;
import net.tce.model.BitacoraTrack;
import net.tce.model.BitacoraTrackCand;
import net.tce.model.BitacoraTrackCont;
import net.tce.model.BitacoraTrackMon;
import net.tce.model.BitacoraTrackPost;
import net.tce.model.BitacoraTrackRecdrio;
import net.tce.util.Constante;
import net.tce.util.DateUtily;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

/**
 * Clase donde se aplica los eventos de la bitacora del tracking
 * @author DothrDeveloper
 *
 */
@Transactional
@Service("bitacoraTrackingService")
public class BitacoraTrackingServiceImpl implements BitacoraTrackingService {
	Logger log4j = Logger.getLogger( this.getClass());
	BitacoraTrack bitacoraTrack;
	BitacoraModelorsc bitacoraModelorsc;
	BitacoraFase bitacoraFase;
	BitacoraMonitor bitacoraMonitor;
	BitacoraTrackCand bitacoraTrackCand;
	BitacoraTrackMon bitacoraTrackMon;
	BitacoraTrackPost bitacoraTrackPost;
	BitacoraTrackRecdrio bitacoraTrackRecdrio;
	Iterator<BtcRecdrioDto> itBtcRecdrioDto;
	BtcRecdrioDto btcRecdrioDto;
	BitacoraTrackCont bitacoraTrackCont;
	
	
	long idBitacoraTrack;
	
	@Autowired
	private BitacoraTrackDao bitacoraTrackDao;
	
	@Autowired
	private BitacoraModelorscDao bitacoraModelorscDao;
	
	@Autowired
	private BitacoraFaseDao bitacoraFaseDao;
	
	@Autowired
	private BitacoraMonitorDao bitacoraMonitorDao;
	
	@Autowired
	private BitacoraTrackCandDao bitacoraTrackCandDao;
	
	@Autowired
	private BitacoraTrackMonDao bitacoraTrackMonDao;
	
	@Autowired
	private BitacoraTrackPostDao bitacoraTrackPostDao;
	
	@Autowired
	private BitacoraTrackRecdrioDao bitacoraTrackRecdrioDao;
	
	@Autowired
	private BitacoraTrackContDao bitacoraTrackContDao;
	
	@Inject
	private ConversionService converter;
	
	/**
	 * 
	 * @param btcTrackingDto
	 * @return
	 * @throws Exception
	 */
	public String create(BtcTrackingDto btcTrackingDto) {
		log4j.debug("<create> ->  idBitacoraTrackRel="+btcTrackingDto.getIdBitacoraTrackRel()+
				" IdRelacionEmpresaPersona="+btcTrackingDto.getIdRelacionEmpresaPersona()+
				" getIdPosicion="+btcTrackingDto.getIdPosicion()+
				" idTipoEventoBitacora="+btcTrackingDto.getIdTipoEventoBitacora()+
				" idTipoModuloBitacora="+btcTrackingDto.getIdTipoModuloBitacora()+
				" fecha="+btcTrackingDto.getFecha()+
				" porSistema="+btcTrackingDto.getPorSistema()+
				" observacion="+btcTrackingDto.getObservacion());
				
		
		filtros(btcTrackingDto,Constante.F_CREATE);
		log4j.debug("<create> -> getCode="+btcTrackingDto.getCode());
		
		if(btcTrackingDto.getCode() == null){
			
			//tabla principal
			bitacoraTrack=converter.convert(btcTrackingDto, BitacoraTrack.class);			
			bitacoraTrack.setFecha(DateUtily.getToday());
			log4j.debug("<create> -> getPosicion="+bitacoraTrack.getPosicion()+
					" observacion="+bitacoraTrack.getObservacion());
		
			//Modelos RSC y Modelos RSC Pos
			if(btcTrackingDto.getIdTipoModuloBitacora().shortValue() == Constante.TIPO_MOD_BTC_MODRSC ||
			   btcTrackingDto.getIdTipoModuloBitacora().shortValue() ==Constante.TIPO_MOD_BTC_MODRSC_POS) {
				bitacoraModelorsc=converter.convert(btcTrackingDto.getBtcModeloRscDto(), 
													BitacoraModelorsc.class);								
				//create
				bitacoraModelorsc.setIdBitacoraModelorsc((long)bitacoraModelorscDao.create(bitacoraModelorsc));
				log4j.debug("<create> -> se crea Bitacora Modelos RSC - getIdBitacoraModelorsc="+
																bitacoraModelorsc.getIdBitacoraModelorsc());
				bitacoraTrack.setBitacoraModelorsc(bitacoraModelorsc);
				
			//Modelos RSC Faces y Modelos RSC Faces Pos
			}else if(btcTrackingDto.getIdTipoModuloBitacora().shortValue() ==Constante.TIPO_MOD_BTC_MODRSC_FASE ||
					   btcTrackingDto.getIdTipoModuloBitacora().shortValue() ==Constante.TIPO_MOD_BTC_MODRSC_POS_FASE) {
				//seters
				bitacoraFase=converter.convert(btcTrackingDto.getBtcModeloRscDto(),BitacoraFase.class);	
				
				//create 
				bitacoraFase.setIdBitacoraFase((long)bitacoraFaseDao.create(bitacoraFase));
				log4j.debug("<create> -> se crea Bitacora Modelos RSC Fase - getIdBitacoraFase="+
																		bitacoraFase.getIdBitacoraFase());
				//add
				bitacoraTrack.setBitacoraFase(bitacoraFase);
				
			//Monitor
			}else if(btcTrackingDto.getIdTipoModuloBitacora().shortValue() ==Constante.TIPO_MOD_BTC_MONITOR) {
				
				//setters
				bitacoraMonitor=converter.convert(btcTrackingDto.getBtcTrackingMonPosDto(),BitacoraMonitor.class);	
								
				//create
				bitacoraMonitor.setIdBitacoraMonitor((long)bitacoraMonitorDao.create(bitacoraMonitor));
				log4j.debug("<create> -> se crea Bitacora Monitor - idBitacoraMonitor="+
													bitacoraMonitor.getIdBitacoraMonitor());
				
				//add
				bitacoraTrack.setBitacoraMonitor(bitacoraMonitor);
				
			//Pre-candidato
			}else if(btcTrackingDto.getIdTipoModuloBitacora().shortValue() ==Constante.TIPO_MOD_BTC_PRE_CAND ||
					btcTrackingDto.getIdTipoModuloBitacora().shortValue() ==Constante.TIPO_MOD_BTC_CAND) {
				
				//setters
				bitacoraTrackCand=converter.convert(btcTrackingDto.getBtcTrackCandDto(),BitacoraTrackCand.class);	
				
				//create
				bitacoraTrackCand.setIdBitacoraTrackCand((long)bitacoraTrackCandDao.create(bitacoraTrackCand));
				
				log4j.debug("<create> -> se crea Bitacora Candidato - idBitacoraTrackCand="+
														bitacoraTrackCand.getIdBitacoraTrackCand());
				
				//add
				bitacoraTrack.setBitacoraTrackCand(bitacoraTrackCand);
				
			// trackingMonitor
			}else if(btcTrackingDto.getIdTipoModuloBitacora().shortValue() == Constante.TIPO_MOD_BTC_TRACK_MON ||
					btcTrackingDto.getIdTipoModuloBitacora().shortValue() == Constante.TIPO_MOD_BTC_TRACK_MON_RCTRIO ) {
				
				//setters
				bitacoraTrackMon=converter.convert(btcTrackingDto.getBtcTrackingMonPosDto(),BitacoraTrackMon.class);
				
				//create
				bitacoraTrackMon.setIdBitacoraTrackMon((long)bitacoraTrackMonDao.create(bitacoraTrackMon));
				
				log4j.debug("<create> -> se crea Bitacora Tracking_Monitor - idBitacoraTrackMon="+
							bitacoraTrackMon.getIdBitacoraTrackMon());
				
				//recordatorios
				if(btcTrackingDto.getBtcTrackingMonPosDto().getLsBtcRecdrioDto() != null) {
					itBtcRecdrioDto=btcTrackingDto.getBtcTrackingMonPosDto().getLsBtcRecdrioDto().iterator();
					while(itBtcRecdrioDto.hasNext()) {
						bitacoraTrackRecdrio=converter.convert(itBtcRecdrioDto.next(),BitacoraTrackRecdrio.class);
						bitacoraTrackRecdrio.setBitacoraTrackMon(bitacoraTrackMon);
						bitacoraTrackRecdrioDao.create(bitacoraTrackRecdrio);
					}
				}				
				
				//add
				bitacoraTrack.setBitacoraTrackMon(bitacoraTrackMon);
			
			// trackingPostulado
			}else if(btcTrackingDto.getIdTipoModuloBitacora().shortValue() ==Constante.TIPO_MOD_BTC_TRACK_POS||
					btcTrackingDto.getIdTipoModuloBitacora().shortValue() == Constante.TIPO_MOD_BTC_TRACK_POS_RCTRIO ) {
				log4j.debug("<create> -> se crea Bitacora Tracking_Postulante - BtcTrackingMonPosDto="+
						btcTrackingDto.getBtcTrackingMonPosDto());
				//setters
				bitacoraTrackPost=converter.convert(btcTrackingDto.getBtcTrackingMonPosDto(),BitacoraTrackPost.class);
				log4j.debug("<create> -> se crea Bitacora Tracking_Postulante - bitacoraTrackPost="+bitacoraTrackPost);
				
				//create
				bitacoraTrackPost.setIdBitacoraTrackPost((long)bitacoraTrackPostDao.create(bitacoraTrackPost));
				
				log4j.debug("<create> -> se crea Bitacora Tracking_Postulante - idBitacoraTrackMon="+
						bitacoraTrackPost.getIdBitacoraTrackPost());
				
				//recordatorios
				if(btcTrackingDto.getBtcTrackingMonPosDto().getLsBtcRecdrioDto() != null) {
					itBtcRecdrioDto=btcTrackingDto.getBtcTrackingMonPosDto().getLsBtcRecdrioDto().iterator();
					while(itBtcRecdrioDto.hasNext()) {
						bitacoraTrackRecdrio=converter.convert(itBtcRecdrioDto.next(),BitacoraTrackRecdrio.class);
						bitacoraTrackRecdrio.setBitacoraTrackPost(bitacoraTrackPost);
						bitacoraTrackRecdrioDao.create(bitacoraTrackRecdrio);
					}
				}	
				
				//add
				bitacoraTrack.setBitacoraTrackPost(bitacoraTrackPost);
			
			//contenidos
			}else if(btcTrackingDto.getIdTipoModuloBitacora().shortValue() == Constante.TIPO_MOD_BTC_TRACK_MON_CONT ||
					btcTrackingDto.getIdTipoModuloBitacora().shortValue() == Constante.TIPO_MOD_BTC_TRACK_POS_CONT ) {
				
				log4j.debug("<create> -> contenido - se crea Bitacora Tracking_Monitor - BtcTrackMonPosContDto="+
							btcTrackingDto.getBtcTrackMonPosContDto());
							
				//bitacora contenido
				bitacoraTrackCont=converter.convert(btcTrackingDto.getBtcTrackMonPosContDto(),BitacoraTrackCont.class);
				
				log4j.info("<create> contenido -> rutaAbsoluta:"+bitacoraTrackCont.getRutaAbsoluta());
				
				//para TrackingMonitor
				if(btcTrackingDto.getBtcTrackMonPosContDto().getIdTrackingMonitor() != null) {
					bitacoraTrackMon=new BitacoraTrackMon();
					bitacoraTrackMon.setIdTrackingMonitor(btcTrackingDto.getBtcTrackMonPosContDto().
																				getIdTrackingMonitor());
					//create
					bitacoraTrackMon.setIdBitacoraTrackMon((long)bitacoraTrackMonDao.create(bitacoraTrackMon));
					
					log4j.debug("<create> -> contenido - se crea Bitacora Tracking_Monitor - idBitacoraTrackMon="+
								bitacoraTrackMon.getIdBitacoraTrackMon());
					
					//bitacora contenido
					bitacoraTrackCont.setBitacoraTrackMon(bitacoraTrackMon);
					bitacoraTrackCont.setIdBitacoraTrackCont((long)bitacoraTrackContDao.create(bitacoraTrackCont));
					
					log4j.debug("<create> -> contenido - se crea Bitacora Tracking_Monitor - idBitacoraTrackCont="+
							bitacoraTrackCont.getIdBitacoraTrackCont());
					
					//add al principal
					bitacoraTrack.setBitacoraTrackMon(bitacoraTrackMon);
				
				//Para TrackingPostulante
				}else {
					bitacoraTrackPost=new BitacoraTrackPost();
					bitacoraTrackPost.setIdTrackingPostulante(btcTrackingDto.getBtcTrackMonPosContDto().
																				getIdTrackingPostulante());					
					//create
					bitacoraTrackPost.setIdBitacoraTrackPost((long)bitacoraTrackPostDao.create(bitacoraTrackPost));
					
					log4j.debug("<create> -> contenido - se crea Bitacora Tracking_Postulante- idBitacoraTrackPost="+
								bitacoraTrackPost.getIdBitacoraTrackPost()+ " idTrackingPostulante="+
								btcTrackingDto.getBtcTrackMonPosContDto().getIdTrackingPostulante());
					
					//bitacora contenido
					bitacoraTrackCont.setBitacoraTrackPost(bitacoraTrackPost);
					bitacoraTrackCont.setIdBitacoraTrackCont((long)bitacoraTrackContDao.create(bitacoraTrackCont));
					
					log4j.debug("<create> -> contenido - se crea Bitacora Tracking_Post - idBitacoraTrackCont="+
								bitacoraTrackCont.getIdBitacoraTrackCont());
					
					//add al principal
					bitacoraTrack.setBitacoraTrackPost(bitacoraTrackPost);
				}
			}
						
			//create
			idBitacoraTrack=(long)bitacoraTrackDao.create(bitacoraTrack);
			
			log4j.debug("<create> -> se crea idBitacoraTrack="+idBitacoraTrack);
			
			//create BitacoraTrack y respuesta
			btcTrackingDto.setMessages(UtilsTCE.getJsonMessageGson(null,new MensajeDto("idBitacoraTrack",
																	String.valueOf(idBitacoraTrack),
																	Mensaje.SERVICE_CODE_004,
																	Mensaje.SERVICE_TYPE_INFORMATION,null)));
			
		}else{
			btcTrackingDto.setMessages(UtilsTCE.getJsonMessageGson(null, 
										new MensajeDto(null,null,
										btcTrackingDto.getCode(),
										btcTrackingDto.getType(),
										btcTrackingDto.getMessage())));
			log4j.error("<create Bitacora Tracking> Error al crear registro -> "+btcTrackingDto.getMessages());	
		}
		log4j.debug("<create> -> getMessages="+btcTrackingDto.getMessages());
		return btcTrackingDto.getMessages();
	}
	
	
	
	/**
	 * Se aplican criterios de negocio para analizar si es viable la ejecucion correspondiente
	 * @param btcTrackingDto, el objeto a anlizar
	 * @param funcion, el criterio para aplicar los filtros
	 * @throws Exception 
	 */
	private void filtros(BtcTrackingDto btcTrackingDto, int funcion)  {
		 boolean error=false;
		 if(btcTrackingDto != null){
			 if(funcion == Constante.F_CREATE && (
				 btcTrackingDto.getIdRelacionEmpresaPersona() == null ||
				 btcTrackingDto.getIdTipoEventoBitacora()  == null ||
				 btcTrackingDto.getIdTipoModuloBitacora()  == null  )){
				 error=true;
			 }
			 
			
			 
		 }else{
			 error=true;
		 }
		 log4j.debug("<filtros> -> error="+error);
		 if(error){
			 btcTrackingDto.setMessage(Mensaje.MSG_ERROR);
			 btcTrackingDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			 btcTrackingDto.setCode(Mensaje.SERVICE_CODE_006);
			 log4j.fatal("Error en filtros al crear registro en Bitacora Tracking");
		 }
		 
	}
}
