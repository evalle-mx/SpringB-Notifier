package net.tce.admin.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Date;
import java.util.regex.Pattern;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.inject.Inject;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.tce.admin.service.ApplicantService;
import net.tce.admin.service.EmpresaParametroService;
import net.tce.admin.service.LanguageService;
import net.tce.admin.service.PersonCertService;
import net.tce.admin.service.RestJsonService;
import net.tce.admin.service.SolrService;
import net.tce.app.exception.SystemTCEException;
import net.tce.assembler.CurriculumAssembler;
import net.tce.cache.AvisoCache;
import net.tce.cache.PoliticaValorElementoCache;
import net.tce.cache.ThreadExceptionCache;
import net.tce.dao.AmbitoGeograficoDao;
import net.tce.dao.ApplicantDao;
import net.tce.dao.AvisoCandidatoDao;
import net.tce.dao.CandidatoDao;
import net.tce.dao.EmpresaPerfilDao;
import net.tce.dao.EscolaridadDao;
import net.tce.dao.EstatusEscolarDao;
import net.tce.dao.ExperienciaLaboralDao;
import net.tce.dao.GradoAcademicoDao;
import net.tce.dao.MunicipioAdyacenciaDao;
import net.tce.dao.PerfilPosicionDao;
import net.tce.dao.PerfilTextoNgramDao;
import net.tce.dao.PersonaDao;
import net.tce.dao.PersonaPerfilDao;
import net.tce.dao.PoliticaValorDao;
import net.tce.dao.PosicionDao;
import net.tce.dto.AreaPersonaDto;
import net.tce.dto.AvisoDto;
import net.tce.dto.CandidatoDetalleDto;
import net.tce.dto.CandidatoDto;
import net.tce.dto.CandidatoTotalDto;
import net.tce.dto.CertificacionDto;
import net.tce.dto.CurriculumDto;
import net.tce.dto.EmpresaParametroDto;
import net.tce.dto.FileDto;
import net.tce.dto.IdiomaDto;
import net.tce.dto.MensajeDto;
import net.tce.dto.MunicipioAdyacenciaDto;
import net.tce.dto.PerfilDto;
import net.tce.dto.PerfilNgramDto;
import net.tce.dto.PerfilPosicionDto;
import net.tce.dto.PosicionDto;
import net.tce.model.AmbitoGeografico;
import net.tce.model.AreaPersona;
import net.tce.model.Aviso;
import net.tce.model.AvisoCandidato;
import net.tce.model.Candidato;
import net.tce.model.Empresa;
import net.tce.model.EmpresaPerfil;
import net.tce.model.Escolaridad;
import net.tce.model.EstatusCandidato;
import net.tce.model.EstatusInscripcion;
import net.tce.model.EstatusOperativo;
import net.tce.model.ExperienciaLaboral;
import net.tce.model.Perfil;
import net.tce.model.PerfilPosicion;
import net.tce.model.Persona;
import net.tce.model.PersonaPerfil;
import net.tce.model.Politica;
import net.tce.model.PoliticaMValor;
import net.tce.model.PoliticaValor;
import net.tce.model.GradoAcademico;
import net.tce.model.EstatusEscolar;
import net.tce.model.Posicion;
import net.tce.util.Constante;
import net.tce.util.DateUtily;
import net.tce.util.Mensaje;
import net.tce.util.ParametersTCE;
import net.tce.util.UtilsTCE;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Clase donde se aplica las politicas de negocio del servicio Applicant
 * @author Osy y Goyo
 *
 */
@Transactional
@Service("applicantService")
public class ApplicantServiceImpl implements ApplicantService{
	static final Logger log4j = Logger.getLogger("FILE");
	
	String texto;
	StringBuilder sbTexto;
	StringBuilder sbKey=new StringBuilder();
	HashMap<String,List<PoliticaValor>> hmPoliticaValorEscolar;
	float castigoXcaducidad;
	float escolaridadSuperior;
	float estatusIncompletos;
	byte gradoEscolarNivel;
	byte estatusEscolarNivel;
	HashMap <String, Object> htFilters;
	List<PerfilDto> lsPerfilDto;
	List<CandidatoDto> lsCandidatoDto, lsCandidatoDto1;
	List<CandidatoDto> lsCandidatoEnterpriseDto;
	Posicion posicion;
	ArrayList<String> orderby;
	HashMap<String, Object> currFilters;
	Boolean algunCvModificadoONuevo;
	HashMap<String,List<PoliticaValor>> hmPoliticaValor;
	HashMap<String,List<PerfilNgramDto>> hmPerfilNgranDto;
	List<PerfilPosicion> lsPerfilPosicion;
	HashMap<String,DescriptiveStatistics> hmStatsPerfilExpLab;
	HashMap<String,DescriptiveStatistics> hmStatsPerfilEscolaridad;
	HashMap<String,DescriptiveStatistics> hmStatsPerfilEmpresa;
	DescriptiveStatistics statis;
	List<PoliticaValor> lsPoliticaValor;
	CandidatoDto positionInfoDto;
	Map<String,CandidatoTotalDto> mapSalidaCandidato;
	Iterator<CandidatoDto> itCandidatoDto;
	StringBuilder sbAreas;
	CandidatoDto antCandidatoDto;
	List<CandidatoDto> lsCandDtoOut;
	int cont;
	Object object;
	HashMap<Short,List<MunicipioAdyacenciaDto>> hmMunicipioAdyacencia;
	int contadorCuotaCandidatos;
	int rangosAdyecencia;
	CandidatoDetalleDto candidatoDetalleDto=null,
	candidatoDetalleIASDto=null,
	candidatoDetalleIPGDto=null,
	candidatoDetalleDemoDto=null;
	List<CandidatoDto> lsCandDtoTempPolitica;
	CandidatoDetalleDto candidatoDetallePoliDto;
	float totalA, totalB,totalC,totalD,totalE,totalF;
	List<PerfilNgramDto> lsFuncionParcial;
	List<PerfilNgramDto> lsHabilidPosicionSimilar;
	boolean unaVezListaHabilidadPos;
	Long idAmbitoGeografico;
	AvisoDto avisoDto;
	CandidatoDto candidatoDto;
	
	@Inject
	Gson gson;
	
	@Autowired
	private GradoAcademicoDao gradoAcademicoDao;
	
	@Autowired
	private  EstatusEscolarDao estatusEscolarDao;
	
	@Autowired
	private EscolaridadDao escolaridadDao;
	
	@Autowired
	private PoliticaValorDao politicaValorDao;
	
	@Autowired
	private PerfilPosicionDao perfilPosicionDao;
	
	@Autowired
	private PersonaDao personaDao;
	
	@Autowired
	private CandidatoDao candidatoDao;
	
	@Autowired
	private ExperienciaLaboralDao experienciaLaboralDao;
	
	@Autowired
	private ApplicantDao applicantDao;	
	
	@Autowired
	private PersonaPerfilDao personaPerfilDao;
	
	@Autowired
	private EmpresaPerfilDao empresaPerfilDao;
	
	@Autowired
	private PerfilTextoNgramDao perfilTextoNgramDao;
	
	@Autowired
	AmbitoGeograficoDao ambitoGeograficoDao;
	
	@Autowired
	MunicipioAdyacenciaDao municipioAdyacenciaDao;
	
	@Autowired
	private PosicionDao posicionDao;
	
	
	@Autowired
	private AvisoCandidatoDao avisoCandidatoDao;
	
	
	@Autowired
	private EmpresaParametroService empresaParametroService;	
	
	@Autowired
	private SolrService solrService;
	
	@Autowired
	private RestJsonService restJsonService;
	
	@Autowired
	private PersonCertService personCertService;
	
	@Autowired
	private LanguageService langService;
	
	@Inject
	private CurriculumAssembler curriculumAssembler;

	private StringBuilder sbTempo = new StringBuilder("POLITICAS APLICADAS:\n");//TODO <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Eliminar
	
	
	
	/**
	 * Obtiene las personas registradas en la aplicacion para conformar la lista inicial de candidatos a procesar
	 * a traves de los filtros de busqueda 
	 * candidatoDto Objeto con los parametros iniciales, posicion y maxima adyacencia 
	 * @return List<CandidatoDto> Lista de candidatos ACEPTADOS 
	 */	
	public String searchApplicants(PosicionDto posicionDto) throws Exception {
		log4j.debug("<searchApplicants> Posicion:" + posicionDto.getIdPosicion()+
				" getIdEmpresaConf="+posicionDto.getIdEmpresaConf()+
    			" isHuboCambioPosicion="+posicionDto.isHuboCambioPosicion());
		boolean soloUnaVez=true;
		boolean resp=true;
		contadorCuotaCandidatos=0;
		positionInfoDto = applicantDao.getPositionInfo(posicionDto.getIdPosicion());
		
//		log4j.debug("<searchApplicants> positionInfoDto:" + positionInfoDto);
		if(positionInfoDto != null ){
		  log4j.debug("<searchApplicants>  Posicion Info: \n Id="+ positionInfoDto.getIdPosicion() +
					" GoogleLatitudeDomPos="+ positionInfoDto.getGoogleLatitudeDomPos() +   
					" GoogleLongitudeDomPos="+ positionInfoDto.getGoogleLongitudeDomPos()+
					" maximaAdyacencia=" + positionInfoDto.getIdAmbitoGeografico()+
					" empresa="+positionInfoDto.getIdEmpresa()+
					" modificado="+positionInfoDto.getModificado());
		 
		  //si isHuboCambioPosicion=true se obliga a hacer el search
		  //de otra manera se investiga si hubo algun cambio en la posicion
		  if(!posicionDto.isHuboCambioPosicion().booleanValue()){
			    posicionDto.setHuboCambioPosicion(positionInfoDto.getModificado());
		  }		
				  
		  //Se asigna la max. adjacencia
		  posicionDto.setMaxAdjacency(positionInfoDto.getIdAmbitoGeografico().shortValue());
		    
		  //Se obtiene los perfiles
		  hmPoliticaValor=new HashMap<String, List<PoliticaValor>>();
		  hmPerfilNgranDto=new HashMap<String,List<PerfilNgramDto>>();
		  List<PerfilPosicionDto> lsPerfilPosicion=perfilPosicionDao.get(posicionDto.getIdPosicion());
		  log4j.debug("<searchApplicants> lsPerfilPosicion.size="+lsPerfilPosicion.size());
		  Iterator<PerfilPosicionDto> itPerfilPosicion= lsPerfilPosicion.iterator();
		  while(itPerfilPosicion.hasNext()){
			  PerfilPosicionDto perfilPosicion=itPerfilPosicion.next();
			  log4j.debug("<searchApplicants> "+perfilPosicion.getPerfil().getIdPerfil()+
					  	" getPonderacion="+perfilPosicion.getPonderacion());
			  //Se obtienen los objetos politica_valor
			  if(soloUnaVez){
			  		lsPoliticaValor= politicaValorDao.getUnionParametrosEntrada(
									  				perfilPosicion.getPerfil().getIdPerfil(),
									  				posicionDto.getIdPosicion(),null);
			  		soloUnaVez=false;
			  }else{
				  	lsPoliticaValor= politicaValorDao.get(perfilPosicion.getPerfil().getIdPerfil(),null,null);
			  }
			  log4j.debug("<searchApplicants> soloUnaVez="+soloUnaVez+
				 		" lsPoliticaValor="+(lsPoliticaValor != null ? lsPoliticaValor.size():null));		
		  
			  hmPoliticaValor.put(new StringBuilder(String.valueOf(perfilPosicion.getPerfil().getIdPerfil())).
				  			  append("_").append(perfilPosicion.getPonderacion()).toString(),
				  			  lsPoliticaValor == null ? new ArrayList<PoliticaValor>():lsPoliticaValor);
		
			  // Se obtienen los ngram de perfil
			  getPerfilNgram(hmPerfilNgranDto,perfilPosicion.getPerfil().getIdPerfil()); 
		  }
		  
		  //Se crea un map de los municipios adyancentes de la  posicion
		  // keys => los rangos
		  // value => municipios adyancentes del rango correspondiente
		  //Primero se obtienen los rangos de la tabla AMBITO-GEOGRAFICO
		  Iterator<AmbitoGeografico> itAmbitoGeografico=ambitoGeograficoDao.findAll().iterator();
		  hmMunicipioAdyacencia=new HashMap<Short,List<MunicipioAdyacenciaDto>>();
		  while(itAmbitoGeografico.hasNext()){
			  idAmbitoGeografico =itAmbitoGeografico.next().getIdAmbitoGeografico();
			  
			  //Se obtiene la lista de adyancencias por cada rango
			  List<MunicipioAdyacenciaDto> lsMunicipioAdyacenciaDto= municipioAdyacenciaDao.
					  getAdjacency(positionInfoDto.getIdMunicipioPos(),idAmbitoGeografico.longValue());
			  log4j.debug("<searchApplicants> lsMunicipioAdyacenciaDto="+lsMunicipioAdyacenciaDto.size()+" rango="+idAmbitoGeografico);
			  //Hay datos
			  if(lsMunicipioAdyacenciaDto != null && lsMunicipioAdyacenciaDto.size() > 0){
				  hmMunicipioAdyacencia.put(idAmbitoGeografico.shortValue(), lsMunicipioAdyacenciaDto); 
				  rangosAdyecencia++;
			  }
		  }		  
		  log4j.debug("<searchApplicants> rangosAdyecencia="+rangosAdyecencia+" hmMunicipioAdyacencia="+hmMunicipioAdyacencia.size());
		 
		  /* **************************************************** */
		  /* ***** SE OBTIENEN LOS CANDIDATOS DE PERSONAS   ***** */
		  /* **************************************************** */
		  algunCvModificadoONuevo=false;
		  //Se obtiene una lista de posibles candidatos
		  lsCandidatoDto = applicantDao.getPeople(posicionDto.getIdPosicion(),positionInfoDto.getIdEmpresa());
		  log4j.debug("<searchApplicants> getPeople() -> lsCandidatoDto="+lsCandidatoDto.size());
		  
		  //Se obtiene una lista de candidatos
		  lsCandidatoDto1=applicantDao.getPeopleCandidate(posicionDto.getIdPosicion(),positionInfoDto.getIdEmpresa());
		  log4j.debug("<searchApplicants> getPeopleCandidate() -> lsCandidatoDto1="+lsCandidatoDto1.size());
		  if(lsCandidatoDto1 != null && lsCandidatoDto1.size() > 0){
			  lsCandidatoDto.addAll(lsCandidatoDto1);
		  }		  
	   
		  log4j.debug("<searchApplicants> final-> lsCandidatoDto:"+lsCandidatoDto.size()+ 
				  	" IdPosicion="+posicionDto.getIdPosicion());
		  if(lsCandidatoDto.size() > 0){
	    	//Se complementa la lista de candidatos
	    	positionInfoDto.setIdEmpresaConf(posicionDto.getIdEmpresaConf());
	    	complementarLsCandidatoDto(lsCandidatoDto,positionInfoDto);
	    	log4j.debug("<searchApplicants> persona -> algunCvModificadoONuevo="+algunCvModificadoONuevo+
	    			" isHuboCambioPosicion="+posicionDto.isHuboCambioPosicion());
			//Solo se aplican los indices si algun cv es nuevo para la posicion o fue modificado
			if(algunCvModificadoONuevo.booleanValue() || posicionDto.isHuboCambioPosicion().booleanValue()){
				resp=getIndexesApplicantsPeople(lsCandidatoDto, posicionDto.getMaxAdjacency(),
											   posicionDto.isHuboCambioPosicion().booleanValue(),
											   positionInfoDto.getGoogleLatitudeDomPos(),
											   positionInfoDto.getGoogleLongitudeDomPos(),
											   positionInfoDto.getIdMunicipioPos());
			}
		  }
		    
		  //algo anda mal
		  if(!resp){
	    	 return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
	    			 Mensaje.SERVICE_CODE_000,Mensaje.SERVICE_TYPE_FATAL,
	    			 Mensaje.MSG_ERROR_SISTEMA));
		  }
	    
		    //Se obtienen los candidatos de empresas
		  /* lsCandidatoEnterpriseDto = applicantDao.getEnterprise(posicionDto.getIdPosicion());
		    log4j.debug("<searchApplicants> lsCandidatoEnterpriseDto="+lsCandidatoEnterpriseDto);
		    if(lsCandidatoEnterpriseDto.size() > 0){
		    	//Se complementa la lista de candidatos
		    	algunCvModificadoONuevo=complementarLsCandidatoDto(lsCandidatoEnterpriseDto,positionInfoDto);
		    	log4j.debug("<searchApplicants> empresas -> algunCvModificadoONuevo="+algunCvModificadoONuevo+" isHuboCambioPosicion="+posicionDto.isHuboCambioPosicion());
		    	//Solo se aplican los indices si alguna empresa es nueva para la posicion o fue modificada
				if(algunCvModificadoONuevo.booleanValue() || posicionDto.isHuboCambioPosicion().booleanValue()){
					resp=getIndexesApplicantsEnterprise(lsCandidatoEnterpriseDto, posicionDto.getMaxAdjacency(),
							   							posicionDto.isHuboCambioPosicion().booleanValue(),
														positionInfoDto.getGoogleLatitudeDomPos(),
														positionInfoDto.getGoogleLongitudeDomPos(),
														positionInfoDto.getIdMunicipioPos());
				}
		     }
		    
		    //algo anda mal
		    if(!resp){
		    	 return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
		    			 Mensaje.SERVICE_CODE_000,Mensaje.SERVICE_TYPE_FATAL,
		    			 Mensaje.MSG_ERROR_SISTEMA));
		    }*/
	   
		  if((algunCvModificadoONuevo != null && algunCvModificadoONuevo.booleanValue() ) || 
				  posicionDto.isHuboCambioPosicion().booleanValue()){
		   /* log4j.debug("<searchApplicants> lsCandidatoDto="+lsCandidatoDto+
					" lsCandidatoEnterpriseDto="+lsCandidatoEnterpriseDto);
		    
		    //Construir lista de candidatos final
		    if((lsCandidatoDto != null && lsCandidatoDto.size() > 0) ){
		    	if(lsCandidatoEnterpriseDto.size() > 0){
		    		lsCandidatoDto.addAll(lsCandidatoEnterpriseDto);
		    	}
		    }else {
		    	if(lsCandidatoEnterpriseDto.size() > 0){
		    		lsCandidatoDto=new ArrayList<CandidatoDto>();
		    		lsCandidatoDto.addAll(lsCandidatoEnterpriseDto);
		    		lsCandidatoEnterpriseDto = null;
		    	}
		    } */
		    log4j.debug("<searchApplicants> lsCandidatoDto="+lsCandidatoDto);
		    
		    
		    //Se persiste la lista final y se crea el mensaje
		    if(lsCandidatoDto.size() > 0 ){    
				// Ultimo paso, persistiendo el arreglo de candidatos resultante 
	    	    log4j.debug("<searchApplicants> Persistiendo la lista final lsCandidatoDto="+lsCandidatoDto.size()); 
	    	    Candidato candidato =null;
				Iterator<CandidatoDto> itPersistenceCandidatoDto = lsCandidatoDto.iterator();
				while(itPersistenceCandidatoDto.hasNext()){
					CandidatoDto candidatoDto = itPersistenceCandidatoDto.next();
		    	    log4j.debug("<searchApplicants> idCandidato="+candidatoDto.getIdCandidato()+
		    	    		" IdPersona="+candidatoDto.getIdPersona()+
		    	    		" idPoliticaKo="+candidatoDto.getIdPoliticaKo()+
		    	    		" getIdEmpresaCandidato="+candidatoDto.getIdEmpresaCandidato()+
		    	    		" FechaCreacion="+candidatoDto.getFechaCreacion()+
		    	    		" LsPerfilDto="+candidatoDto.getLsPerfilDto()+
		    	    		" getIas="+candidatoDto.getIas()+
		    	    		" getIpg="+candidatoDto.getIpg()+
		    	    		" isIpgEnCola="+candidatoDto.getIpgEnCola()+
		    	    		" getIap="+candidatoDto.getIap()+
		    	    		" isIasEnCola="+candidatoDto.getIasEnCola()+
		    	    		" getIapPuntosZ="+candidatoDto.getIapPuntosZ()+
		    	    		" getAliasPersona="+ candidatoDto.getNombre()+
		    	    		" getRechazoEstatus="+candidatoDto.getIdEstatusCandidato()+
		    	    		" getNuevoOModificado="+candidatoDto.getNuevoOModificado().booleanValue()+
		    	    		" getIdGradoAcademico="+candidatoDto.getIdGradoAcademico()+
		    	    		" getIdEstatusEscolar="+candidatoDto.getIdEstatusEscolar()+
		    	    		" getIdEstatusOperativo="+candidatoDto.getIdEstatusOperativo()+
		    	    		" getModificado="+candidatoDto.getModificado()+
		    	    		" getHandshake="+candidatoDto.getHandshake()+
		    	    		" getDiaNacimiento="+candidatoDto.getDiaNacimiento()+
		    	    		" getMesNacimiento="+candidatoDto.getMesNacimiento()+
		    	    		" getAnioNacimiento="+candidatoDto.getAnioNacimiento());
		    	    
		    	    //Si es nuevo el candidato o fue modificado
		    	    if(candidatoDto.getIdCandidato() == null ||
		    	       candidatoDto.getNuevoOModificado().booleanValue()){
			    	    // Setting candidatos list
			    	    candidato = new Candidato();
			    	    candidato.setPosicion(new Posicion());
			    	    candidato.getPosicion().setIdPosicion(candidatoDto.getIdPosicion());
			    	    //Si no es persona entonces es empresa
			    	    if(candidatoDto.getIdPersona() != null){
			    	    	 candidato.setPersona(new Persona());
			    	    	 candidato.getPersona().setIdPersona(candidatoDto.getIdPersona());
			    	    }else{
			    	    	candidato.setEmpresa(new Empresa());
			    	    	candidato.getEmpresa().setIdEmpresa(candidatoDto.getIdEmpresaCandidato());
			    	    }
			    	   
			    	    candidato.setEstatusCandidato(new EstatusCandidato());
			    	    candidato.getEstatusCandidato().setIdEstatusCandidato(
			    	    								candidatoDto.getIdEstatusCandidato());
			    	    //Se asigna el IdEstatusOperativo, si es nuevo el candidato o ha sido modificado
			    	    candidato.setEstatusOperativo(new EstatusOperativo());
			    	    candidato.getEstatusOperativo().setIdEstatusOperativo(
								    	    		  	candidatoDto.getIdEstatusOperativo() == null ? 
					    	    					    Constante.ESTATUS_CANDIDATO_OPERATIVO_INDEXADO:
					    	    						candidatoDto.getIdEstatusOperativo());
			    	    
			    	    candidato.setIasCodigo(candidatoDto.getIas());
			    	    candidato.setIpgCodigo(candidatoDto.getIpg());
			    	    candidato.setIap(candidatoDto.getIapPuntosZ() == null ? null:
			    	    				new BigDecimal(UtilsTCE.redondear(candidatoDto.getIapPuntosZ(),
			    	    				4,RoundingMode.CEILING)));
			    	    candidato.setIapBruto(candidatoDto.getIapBrutoTotal() == null ? null:
			    	    					UtilsTCE.redondear(candidatoDto.getIapBrutoTotal(),
			    	    					4,RoundingMode.CEILING));
			    	    candidato.setIapCodigo(candidatoDto.getIap());
			    	    candidato.setDistanciaReal(candidatoDto.getDistanciaReal() == null ? 
			    	    						   null:candidatoDto.getDistanciaReal());
			    	    candidato.setRangoGeografico(candidatoDto.getRangoGeografico());
			    	    
			    	    //Ya que solo hay una area para la posicion se pone el valor de 1 por default
			    	    candidato.setRankingArea(1);
			    	    candidato.setModificado(candidatoDto.getModificado());
			    	    
			    	    //Para los KO DEMOGRAFICOS
			    	    if(candidatoDto.getIdPoliticaKo() != null){			    	    	 
				    	    candidato.setPolitica(new Politica());
				    	    candidato.getPolitica().setIdPolitica(candidatoDto.getIdPoliticaKo());
			    	    }/**/
			    	    
			    	    if(candidatoDto.getHandshake() != null){
			    	    	candidato.setHandshake(candidatoDto.getHandshake());
			    	    }
			    	    if(candidatoDto.getDemografico() != null){
				    	    candidato.setDemograficoBruto(new BigDecimal(UtilsTCE.redondear(
				    	    			candidatoDto.getDemografico(),4,RoundingMode.CEILING)));
			    	    }
			    	    //colas
			    	    candidato.setIasEnCola(candidatoDto.getIasEnCola());
			    	    candidato.setIpgEnCola(candidatoDto.getIpgEnCola());
			    	    
			    	    //si hay orden
			    	    if(candidatoDto.getOrden() != null){
			    	    	candidato.setOrden(candidatoDto.getOrden());
			    	    }
			    	    
			    	    if(candidatoDto.getConfirmado() != null){ 
			    	    	candidato.setConfirmado(candidatoDto.getConfirmado());
			    	    }
			    	    
			    	  
			    	    //si hay comentario
			    	    if(candidatoDto.getComentario() != null){
			    	    	candidato.setComentario(candidatoDto.getComentario());
			    	    }
			    	    
			    	    log4j.debug("<searchApplicants> Existe idCandidato="+candidatoDto.getIdCandidato()+
			    	    		" getLsPerfilDto="+candidatoDto.getLsPerfilDto()+ 
			    	    		" getOrden="+candidatoDto.getOrden()+
			    	    		" getConfirmado="+candidatoDto.getConfirmado()+
			    	    		" getActivo"+candidatoDto.getActivo());
			    	    //si ya existe el candidato
			    	    if(candidatoDto.getIdCandidato() != null){
			    	    	candidato.setIdCandidato(candidatoDto.getIdCandidato());
			    	    	candidato.setFechaModificacion(DateUtily.getToday());
			    	    	candidato.setFechaCreacion(candidatoDto.getFechaCreacion());
			    	    	applicantDao.update(candidato);
			    	    }else{			    	    	
			    	    	candidato.setFechaCreacion(DateUtily.getToday());
			    	    	candidatoDto.setIdCandidato((Long)applicantDao.create(candidato));
			    	    }
			    	    //Se persisten los mensajes
			    	    if(candidatoDto.getAvisos() != null && candidatoDto.getAvisos().size() > 0){
			    	    	Iterator<AvisoDto> itAvisoDto=candidatoDto.getAvisos().iterator();
			    	    	AvisoCandidato avisoCandidato;
			    	    	while(itAvisoDto.hasNext()){
			    	    		avisoDto=itAvisoDto.next();
			    	    		avisoCandidato=new AvisoCandidato();
			    	    		
			    	    		//existe el aviso
			    	    		if(avisoDto.getIdAvisoCandidato() != null){
			    	    			avisoCandidato.setIdAvisoCandidato(avisoDto.getIdAvisoCandidato());
			    	    		}
			    	    		avisoCandidato.setCandidato(new Candidato());
			    	    		avisoCandidato.getCandidato().setIdCandidato(candidatoDto.getIdCandidato());
			    	    		avisoCandidato.setAviso(new Aviso());
			    	    		avisoCandidato.getAviso().setIdAviso(avisoDto.getIdAviso());
			    	    		
			    	    		//Se aplica delete
			    	    		if(avisoDto.getBorrado() != null && avisoDto.getBorrado().booleanValue()){
			    	    			avisoCandidatoDao.delete(avisoCandidato);
			    	    		}else{
			    	    			//persiste el objeto aviso
			    	    			avisoCandidatoDao.merge(avisoCandidato);
			    	    		}
			    	    	}			    	    	
			    	    }
			    	 
			    	    //Se persiste en persona_perfil
			    	   if(candidatoDto.getLsPerfilDto() != null &&
			    		  candidatoDto.getIdEstatusCandidato().byteValue() == Constante.ESTATUS_CANDIDATO_ACEPTADO){
							Iterator<PerfilDto> itPerfilDto=candidatoDto.getLsPerfilDto().iterator();
							PersonaPerfil personaPerfil;
							EmpresaPerfil empresaPerfil;
							while(itPerfilDto.hasNext()){
								PerfilDto perfilDto=itPerfilDto.next();
	
								log4j.debug("<searchApplicants> saveOrUpdate getIdPersona="+candidatoDto.getIdPersona()+
										" getIdEmpresaCandidato="+candidatoDto.getIdEmpresaCandidato() +
										" getIdPersonaPerfil="+perfilDto.getIdPersonaPerfil()+
										" getIdEmpresaPerfil="+perfilDto.getIdEmpresaPerfil());
								
								//Para persona
								if(candidatoDto.getIdPersona() != null){
									personaPerfil=new PersonaPerfil();
									//si ya existe la PersonaPerfil
									if(perfilDto.getIdPersonaPerfil() > 0){
										personaPerfil.setIdPersonaPerfil(perfilDto.getIdPersonaPerfil());
										personaPerfil.setFechaModificacion(DateUtily.getToday());
										personaPerfil.setFechaCreacion(perfilDto.getFechaCreacionPersonaPerfil());
									}else{
										personaPerfil.setFechaCreacion(DateUtily.getToday());
									}
									personaPerfil.setPersona(new Persona());
									personaPerfil.getPersona().setIdPersona(candidatoDto.getIdPersona());
									personaPerfil.setPerfil(new Perfil());
									personaPerfil.getPerfil().setIdPerfil(perfilDto.getIdPerfil().longValue());
									personaPerfil.setIap(new BigDecimal(UtilsTCE.redondear(perfilDto.getIap(),
														4,RoundingMode.CEILING)));
									personaPerfil.setIapBruto(new BigDecimal(UtilsTCE.redondear(perfilDto.getIapBruto(),
															  4,RoundingMode.CEILING)));
									personaPerfil.setIapAcademicoBruto(new BigDecimal(UtilsTCE.redondear(perfilDto.getEscolaridadBruto(),
																  		4,RoundingMode.CEILING)));
									personaPerfil.setIapLaboralBruto(new BigDecimal(UtilsTCE.redondear(perfilDto.getExperienciaLaboralBruto(),
																	4,RoundingMode.CEILING)));
									personaPerfil.setIapAcademico(new BigDecimal(UtilsTCE.redondear(perfilDto.getEscolaridad(),
																4,RoundingMode.CEILING)));
									personaPerfil.setIapLaboral(new BigDecimal(UtilsTCE.redondear(perfilDto.getEscolaridad(),
											  					4,RoundingMode.CEILING)));
									log4j.debug("<searchApplicants> SaveOrUpdate getIdPersonaPerfil="+perfilDto.getIdPersonaPerfil()+
											 " getIdPerfil="+perfilDto.getIdPerfil().longValue()+
											 " getIap="+personaPerfil.getIap()+
											 " getIapAcademicoBruto="+personaPerfil.getIapAcademicoBruto()+
											 " getIapLaboralBruto="+personaPerfil.getIapLaboralBruto());
									
									//Se persiste el registro
									personaPerfilDao.merge(personaPerfil);
								}
								//Para empresa
								if(candidatoDto.getIdEmpresaCandidato() != null){
									empresaPerfil=new EmpresaPerfil();
									//si ya existe la EmpresaPerfil
									if(perfilDto.getIdEmpresaPerfil() > 0){
										empresaPerfil.setIdEmpresaPerfil(perfilDto.getIdEmpresaPerfil());
										empresaPerfil.setFechaModificacion(DateUtily.getToday());
										empresaPerfil.setFechaCreacion(perfilDto.getFechaCreacionEmpresaPerfil());
									}else{
										empresaPerfil.setFechaCreacion(DateUtily.getToday());
									}
									
									log4j.debug("<searchApplicants> saveOrUpdate getIdEmpresaCandidato="+perfilDto.getIdEmpresaPerfil()+
											 " getIdPerfil="+perfilDto.getIdPerfil().longValue()+
											 " getIap="+perfilDto.getIap()+
											 " getIapBruto="+perfilDto.getIapBruto());
									empresaPerfil.setEmpresa(new Empresa());
									empresaPerfil.getEmpresa().setIdEmpresa(candidatoDto.getIdEmpresaCandidato());
									empresaPerfil.setPerfil(new Perfil());
									empresaPerfil.getPerfil().setIdPerfil(perfilDto.getIdPerfil().longValue());
									empresaPerfil.setIap(new BigDecimal(UtilsTCE.redondear(perfilDto.getIap(),
														 4,RoundingMode.CEILING)));
									empresaPerfil.setIapBruto(new BigDecimal(UtilsTCE.redondear(perfilDto.getIapBruto(),
															  4,RoundingMode.CEILING)));
									candidatoDto.setIdEmpresa(candidatoDto.getIdEmpresaCandidato());
									//Se persiste el registro
									empresaPerfilDao.merge(empresaPerfil);								
								}
							}
						}
			    	   //Persistir en posicion
			    	   Posicion posicion=posicionDao.read(posicionDto.getIdPosicion());
			    	   posicion.setFechaUltimaBusqueda(DateUtily.getToday());
			    	   
			    	   //solo si hubo modificacion
			    	  /* if(posicionDto.isHuboCambioPosicion()){
			    		   posicion.setModificado(false);
			    	   }*/
			    	   posicionDao.merge(posicion);
		    	    }
				}
				lsCandidatoDto=null;
				return Mensaje.SERVICE_MSG_OK_JSON;
		    }else{
		    	//no hay candidatos para esa posicion
		    	return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
								Mensaje.SERVICE_CODE_003,Mensaje.SERVICE_TYPE_WARNING,
								Mensaje.MSG_WARNING));	
		    }
	    }else{
	    	lsCandidatoDto=null;
			return Mensaje.SERVICE_MSG_OK_JSON;
	    }
	}else{
		//no existe la posicion
    	return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_003,Mensaje.SERVICE_TYPE_WARNING,
						Mensaje.MSG_WARNING));	
	}
  }
	
	/**
	 * Calcula el indice de apego salarial para el candidato proporcionado
	 * @param candidatoDto a analizar
	 * @return candidatoDto con el IAP y el estatus del candidato correspondiente
	 */		
	private void calculateIas(CandidatoDto candidatoDto){
		
			//Informacion de Persona
			log4j.debug("<calculateIas> " 
					+ " idPersona=" + candidatoDto.getIdPersona()
					+ " SalMin=" + candidatoDto.getSalarioMinPer()
					+ " SalMax=" + candidatoDto.getSalarioMaxPer()				
					+ " SalAverage=" + candidatoDto.getSalarioAvgPer()				
					);
			
			/* Info de Posicion */
			log4j.debug("<calculateIas> " 
					+ " idPosicion=" + candidatoDto.getIdPosicion()
					+ " Pos.SalMin=" + candidatoDto.getSalarioMinPos()
					+ " Pos.SalMax=" + candidatoDto.getSalarioMaxPos()				
					+ " Pos.SalAverage=" + candidatoDto.getSalarioAvgPos()				
					);
			
			//Se obtiene la dif entre salarioMaxPosicion y salarioPromnedioPersona, para analizar cual es el mas apto
			candidatoDto.setIasDistancia(candidatoDto.getSalarioMaxPos().intValue() - candidatoDto.getSalarioAvgPer().intValue());
			
			//Se obtiene la dif entre salarioMaxPosicion y salarioMinimoPersona, para saber si se mete a la pila
			//Si es negativa la dif. se mete a la pila
			candidatoDto.setIasEnCola(((candidatoDto.getSalarioMaxPos().longValue() - candidatoDto.getSalarioMinPer().longValue()) < 0) 
										? true:false);
			//Se asigna el estatus de candidato de rechazo por salario, al estar en la cola
			if(candidatoDto.getIasEnCola().booleanValue()){
				candidatoDto.setIdEstatusCandidato(Constante.ESTATUS_CANDIDATO_RECHAZADO_SALARIO);
			}

			log4j.debug("<calculateIas> iasDistancia="+candidatoDto.getIasDistancia()+
					" iasEnCola="+candidatoDto.getIasEnCola());
	}	

	/**
	 * 	Se calcula IPG
	 * @param candidatoDto, info del candidato
	 * @param googleLatitudeDomPos, latitud de la ubicación de la posicion
	 * @param googleLongitudeDomPos, longitud de la ubicación de la posicion
	 * @param maxAdjacency, rango de cercania de los candidatos con respecto a la posicion
	 */
	private void calculateIpg(CandidatoDto candidatoDto,BigDecimal googleLatitudeDomPos,
			  BigDecimal googleLongitudeDomPos,short maxAdjacency) {
		log4j.debug("<calculateIpgData> Inicio...");
		
		
		// Lee informacion de la posicion
		log4j.debug("<calculateIpgData> Ubicación de Posicion "+ candidatoDto.getIdPosicion() + ": \n\t"+
				" lat="	 + googleLatitudeDomPos + 
				" long=" + googleLongitudeDomPos);
		// Lee informacion de la persona
		
		log4j.debug("<calculateIpgData> Ubicación de Persona "+ candidatoDto.getIdPersona() + ": \n\t"+
				" Ipg="	 + candidatoDto.getIpg() +
				" lat="	 + candidatoDto.getGoogleLatitudeDom() + 
				" long=" + candidatoDto.getGoogleLongitudeDom() +
				" Municipio="	 + candidatoDto.getIdMunicipio());
	
			
		//se analiza la adyencencia del candidato con respecto a la posicion
		addCandidatosPorAdyacencia(candidatoDto, maxAdjacency, false);
		
		/* Distancia Real Re-calculation */
		log4j.debug("<calculateIpgData> Distancia Real Re-calculation"); 
		candidatoDto.setDistanciaReal(new BigDecimal(UtilsTCE.distGeo(
					candidatoDto.getGoogleLatitudeDom().doubleValue(),
					candidatoDto.getGoogleLongitudeDom().doubleValue(),
					googleLatitudeDomPos.doubleValue(),
					googleLongitudeDomPos.doubleValue())));
		
		log4j.debug(" DistanciaReal="+candidatoDto.getDistanciaReal()+
				" IpgEnCola="+candidatoDto.getIpgEnCola());	
		log4j.debug("<calculateIpgData> Fin...");
	}	
	
	/**
	 * Se obtienen candidatos de las colas para completar la cuota
	 * @param lsCandidatoDto, lista de candidatos
	 * @param rangoProximidad, rango de cercania de los candidatos con respecto a la posicion
	 */
	private void completarCuotaDeCandidatos(List<CandidatoDto> lsCandidatoDto, Short rangoProximidad)throws Exception{
		
		 // Get QOS internal parameter for TCE enterprise 
		 Integer qos = getQOS();
		
		 // 1 = IPG, 2 = IAS
		 byte index=1;
				  
		 log4j.debug("completarCuotaDeCandidatos() -> contadorCuotaCandidatos_ini= "+contadorCuotaCandidatos+" cuota_qos="+qos+" rangosAdyecencia="+rangosAdyecencia);
		//si rangoProximidad es el ultimo, se obtienen los candidatos de la cola
		 //hasta completar la cuota
		 if(rangoProximidad == Constante.AMBITO_GEOGRAFICO_VALOR_ULTIMO){
			 Collections.sort(lsCandidatoDto);  
			 Iterator<CandidatoDto> itCandidatoDto=lsCandidatoDto.iterator();			  
			  while(itCandidatoDto.hasNext()){				  
				 CandidatoDto candidatoDto=itCandidatoDto.next();
				 log4j.debug("completarCuotaDeCandidatos() -> getIdPersona="+candidatoDto.getIdPersona()+
						 " getIdCandidato="+candidatoDto.getIdCandidato()+
						 " getIpgEnCola="+candidatoDto.getIpgEnCola());
				 if(candidatoDto.getIpgEnCola() != null 
					&& candidatoDto.getIpgEnCola()){
					 //cumple cuota
					 if(contadorCuotaCandidatos < qos.intValue()){
						 log4j.debug("completarCuotaDeCandidatos() -> getIdCandidato="+candidatoDto.getIdCandidato()+" getDistanciaReal= "+candidatoDto.getDistanciaReal());
						 //posicionLog.info("  -- Se toma de la cola_IPG -- idPersona="+candidatoDto.getIdPersona()+ " idEmpresa="+ candidatoDto.getIdEmpresa()+
								 		  //" getDistanciaReal= "+candidatoDto.getDistanciaReal());
						 candidatoDto.setIpgEnCola(false);
						 candidatoDto.setIdEstatusCandidato(Constante.ESTATUS_CANDIDATO_ACEPTADO);
						 contadorCuotaCandidatos++;
					 }else{
						 break;
					 }
				  }
			  }
		 }else{		 
			 //Hasta que se cumpla la cuota
			 while(contadorCuotaCandidatos < qos.intValue()){		    		  
				 // Obtenemos candidatos de la cola de IPG	
				  if(index == 1){	
					  log4j.debug("completarCuotaDeCandidatos() -> 1");			 
					  if(rangoProximidad <= rangosAdyecencia){
						  log4j.debug("completarCuotaDeCandidatos() -> 2");
						  Iterator<CandidatoDto> itCandidatoDto=lsCandidatoDto.iterator();
						  
						  while(itCandidatoDto.hasNext()){
							 CandidatoDto candidatoDto=itCandidatoDto.next();
							  
							 if(candidatoDto.getIdEstatusCandidato().byteValue() == Constante.ESTATUS_CANDIDATO_RECHAZADO_DISTANCIA){
								  //se suman candidatos con un nuevo rango de proximidad
								  addCandidatosPorAdyacencia(candidatoDto, rangoProximidad,true);
							  }				  
						  }
						  
						   // se aumenta el rango de proximidad hasta cumplir la cuota
						  rangoProximidad++;
						  
					  //Se salta al siguiente index si ya se aplicaron todos los rangos de adyecencia
					  }else{	
						  log4j.debug("completarCuotaDeCandidatos() -> 4");
						  index++;
					  }
				  //Obtenemos candidatos de la cola de IAS, pero otro dia con mas calma
				  }else if(index == 2){
					  break;
				  }
			
				  log4j.debug("completarCuotaDeCandidatos() -> contadorCuotaCandidatos= "+contadorCuotaCandidatos+" rangoProximidad="+rangoProximidad+" index="+index	);
			  }
			 
		 }
		  log4j.debug("despues del ciclo -> contadorCuotaCandidatos= "+contadorCuotaCandidatos+" rangoProximidad="+rangoProximidad);
	}
	

	/**
	 * Dado el candidato se analiza si éste es adyancente a la posición dependiendo del rango de proximidad y al estatus de candidato
	 * @param candidatoDto, datos del candidato
	 * @param rangoProximidad
	 * @param idEstatusCandidato
	 * @param aplicaSoloElRango, true se aplica solo ese rango
	 * 							 false se aplica del rango hacia abajo
	 */
	private void addCandidatosPorAdyacencia(CandidatoDto candidatoDto, Short rangoProximidad, boolean aplicaSoloElRango){
		log4j.debug("<analizaAdyacencia> rangoProximidad="+rangoProximidad+" hmMunicipioAdyacencia="+hmMunicipioAdyacencia.size());
		boolean esAdyancente=false;
		while( rangoProximidad > 0){	
			List<MunicipioAdyacenciaDto> lsMunicipioAdyacenciaDto=hmMunicipioAdyacencia.get(rangoProximidad);
			//log4j.debug("<analizaAdyacencia> lsMunicipioAdyacenciaDto="+lsMunicipioAdyacenciaDto);
			if(lsMunicipioAdyacenciaDto != null && lsMunicipioAdyacenciaDto.size() > 0){
				Iterator<MunicipioAdyacenciaDto> itMunicipioAdyacenciaDto= lsMunicipioAdyacenciaDto.iterator();
				while(itMunicipioAdyacenciaDto.hasNext()){
					MunicipioAdyacenciaDto municipioAdyacenciaDto=itMunicipioAdyacenciaDto.next();
					/*log4j.debug("<analizaAdyacencia>  idMunicipio_Ady_pos="+municipioAdyacenciaDto.getIdMunicipioAdyacente().longValue()+
							" idMunicipio_cand="+candidatoDto.getIdMunicipio()+" idEstatusCandidato_cand="+candidatoDto.getIdEstatusCandidato());*/
							
					//si el idmunicipio de candidato se encuentra como adyancente al idmunicipio de posicion
					//para el estatus de candidato
					if(municipioAdyacenciaDto.getIdMunicipioAdyacente().longValue() == candidatoDto.getIdMunicipio().longValue()){	
						log4j.debug("<analizaAdyacencia>  idMunicipio_Ady_pos="+municipioAdyacenciaDto.getIdMunicipioAdyacente().longValue()+
								" idMunicipio_cand="+candidatoDto.getIdMunicipio()+" idEstatusCandidato_cand="+candidatoDto.getIdEstatusCandidato());
						esAdyancente=true;
						contadorCuotaCandidatos++;
						break;
					}
				}
				//Si hay adyancencia, salir
				if(esAdyancente){
					break;
				}
			}
			//Se aplica solo el rango o del rango hac1a abajo
			if(aplicaSoloElRango){
				rangoProximidad=0;
			}else{
				rangoProximidad--;
			}
			
		}
		//Hubo adyancencia
		if(esAdyancente){
			//posicionLog.info("  -- SI Cumple con adyancencia -- idPersona="+candidatoDto.getIdPersona()+ " idEmpresa="+candidatoDto.getIdEmpresa());
			log4j.debug("  -- SI Cumple con adyancencia -- idPersona="+candidatoDto.getIdPersona()+ " idEmpresa="+candidatoDto.getIdEmpresa());
			candidatoDto.setIpgEnCola(false);
			candidatoDto.setIdEstatusCandidato(Constante.ESTATUS_CANDIDATO_ACEPTADO);
		}else{
			candidatoDto.setIpgEnCola(true);
			//Solo si el candidato tiene estatus de aceptado
			candidatoDto.setIdEstatusCandidato(Constante.ESTATUS_CANDIDATO_RECHAZADO_DISTANCIA);
			
			
			////posicionLog.info("  -- NO Cumple con adyancencia -- idPersona="+candidatoDto.getIdPersona());
			log4j.debug("  -- NO Cumple con adyancencia -- idPersona="+candidatoDto.getIdPersona()+ " idEmpresa="+candidatoDto.getIdEmpresa());
		}
	}
	
	

	/**
	 * Obtiene el parametro de QualityOfService QOS global (empresa TCE) de la base de datos, el cual es el numero minimo de candidatos 
	 * que se deben regresar en una busqueda antes de hacer uso de los candidatos no aceptados 
	 * @param Ninguno
	 * @return QOS Valor del parametro QualityOfService
	 */			
	@SuppressWarnings("unchecked")
	private Integer getQOS()throws Exception{
		log4j.debug("<getQOS> Inicio...");

		EmpresaParametroDto empresaParametroDto=new EmpresaParametroDto();
		empresaParametroDto.setIdEmpresaConf(String.valueOf(Constante.IDCONF_NULL));
		empresaParametroDto.setIdTipoParametro(Constante.TIPO_PARAMETRO_APLICACION_GENERAL);
		empresaParametroDto.setContexto(Constante.QOS_APPLICANTS);
		Object object=empresaParametroService.get(empresaParametroDto,true);
		if(object instanceof EmpresaParametroDto ){
			return new Integer(Constante.QOS_APPLICANTS_DEFAULT);
		}else{
			List<EmpresaParametroDto> lsEmpresaParametroDto = (List<EmpresaParametroDto>)object;
			log4j.debug("<getQueryForModel> lsEmpresaParametroDto.size :" +  lsEmpresaParametroDto.size());
			return Integer.parseInt(lsEmpresaParametroDto.get(0).getValor());
		}	
	}	
	
	
	/**
	 * Obtiene una lista de candidatos aceptados para la posicion correspondiente
	 * posicionDto Objeto con los parametros iniciales de posicion
	 * @return List<CandidatoDto> Lista de candidatos  
	 */	
	public Object getApplicants(PosicionDto posicionDto) throws Exception{
		log4j.debug("<getApplicants> IdEmpresaConf :" + posicionDto.getIdEmpresaConf());
		if(posicionDto.getIdEmpresaConf() != null){
			CandidatoTotalDto candidatoTotalDto=null;
			mapSalidaCandidato=new HashMap<String,CandidatoTotalDto>();
			
			// Se obtiene el map de candidatos de personas y empresas 
			//Se obtiene el json de salida de los candidatos_persona
			candidatoTotalDto=construirSalidaCandidatos(candidatoTotalDto,
									  applicantDao.getCanditatosPersonas(
									  posicionDto.getIdPosicion()),
									  posicionDto.getIdEmpresaConf());
			log4j.debug("<getApplicants> salida candidatoTotalDto_personas :" + candidatoTotalDto);
			//solo personas
			if(candidatoTotalDto != null){
				mapSalidaCandidato.put("personas", candidatoTotalDto);
			}
			
			//Se adicionan los candidatos empresa al map de salida
			 candidatoTotalDto=null;
			 candidatoTotalDto=construirSalidaCandidatos(candidatoTotalDto,
					 					applicantDao.getCanditatosEmpresa(
					 					posicionDto.getIdPosicion()),
					 					posicionDto.getIdEmpresaConf());
			//solo empresas
			if(candidatoTotalDto != null){
				mapSalidaCandidato.put("empresas", candidatoTotalDto);
			}
			
			log4j.debug("salida candidatoTotalDto_empresas :" + candidatoTotalDto);
			log4j.debug("salida mapSalidaCandidato :" + mapSalidaCandidato.size());
			
			//Se analiza salida
			if(mapSalidaCandidato != null && mapSalidaCandidato.size() > 0){
				return mapSalidaCandidato;
			}else{
				return Mensaje.SERVICE_MSG_OK_JSON;
			}			
		}else{
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
											Mensaje.SERVICE_CODE_002,
											Mensaje.SERVICE_TYPE_FATAL,
											Mensaje.MSG_ERROR));
		}
	}
	
	/**
	 * Construye los candidatos de salida de personas y empleados
	 * @param mapCandidato, map de salida
	 * @param lsCandDtoIn, la lista de candidatos a manipular
	 * @param esPersona, true si es candidato persona
	 * 					 false si es candidato empresa
	 * @throws Exception 
	 */
	private CandidatoTotalDto construirSalidaCandidatos(CandidatoTotalDto candidatoTotalDto,
											List<CandidatoDto> lsCandDtoIn,
											String idEmpresaConf) throws Exception{
		log4j.debug("<construirSalidaCandidatos>  # de candidatos a procesar= "+(lsCandDtoIn == null ? "0":lsCandDtoIn.size()));
		int cantidad=0, cantidadRechazo=0,total=0;
		//Hay info
		if(lsCandDtoIn != null && lsCandDtoIn.size() > 0){
			candidatoTotalDto=new CandidatoTotalDto();
			candidatoTotalDto.setEsKO(new Boolean(false));
			candidatoTotalDto.setEntroPrimeravez(new Boolean(false));
			candidatoTotalDto.setEntroPrimeravezDemo(new Boolean(false));
			byte idEstatusCandidatoAnt=-1;
			List<CandidatoDto> lsCandDtoTemp= new ArrayList<CandidatoDto>();
			itCandidatoDto=lsCandDtoIn.iterator();
			while(itCandidatoDto.hasNext()){
				candidatoDto=itCandidatoDto.next();
				candidatoDto.setIdEmpresaConf(idEmpresaConf);
				log4j.debug("<construirSalidaCandidatos> getIdEstatusCandidato="+candidatoDto.getIdEstatusCandidato()+
						" idEstatusCandidatoAnt="+idEstatusCandidatoAnt);
						
				//es nuevo el estatus
				if(candidatoDto.getIdEstatusCandidato().byteValue() != idEstatusCandidatoAnt){
					
					//Se adiciona objetos dependiendo del estatus de candidato
					completaSalidaPorEstatus(lsCandDtoTemp,idEstatusCandidatoAnt,candidatoTotalDto, cantidad,cantidadRechazo);				
					
					//se aplica cuando no es el inicial
					if(idEstatusCandidatoAnt != -1){
						cantidad=0;
						lsCandDtoTemp= new ArrayList<CandidatoDto>();
					}
					idEstatusCandidatoAnt=candidatoDto.getIdEstatusCandidato().byteValue();
				}				
				
				//Se verifica si hay handcheck
				/* if(candidatoDto.getHandshake() != null && 
					!candidatoDto.getHandshake().booleanValue()){
						  candidatoDto.setNombre("-1");			  
				  }	*/
				 
				 
				 //solo para rechazados
				 if(candidatoTotalDto.getEsKO().booleanValue()){
					 candidatoDto.setIas(null);
					 candidatoDto.setIpg(null);
					 candidatoDto.setIap(null);
					 cantidadRechazo++;
				 }
				 
				 log4j.debug("<construirSalidaCandidatos> lsCandDtoTemp="+lsCandDtoTemp.size()+
						 	" esKO="+candidatoTotalDto.getEsKO().booleanValue()+
						 	" cantidadRechazo="+cantidadRechazo);
				 log4j.debug("<construirSalidaCandidatos> getIdCandidato="+candidatoDto.getIdCandidato());
				 
				//solo para personas
				if(candidatoDto.getIdPersona() != null){
					log4j.debug("<construirSalidaCandidatos> DiaNacimiento="+candidatoDto.getDiaNacimiento()+
							" MesNacimiento="+candidatoDto.getMesNacimiento()+
							" AnioNacimiento="+candidatoDto.getAnioNacimiento());
					
					//Se calcula la edad
					candidatoDto.setEdad(DateUtily.calcularEdad(new StringBuilder().
			  				append(candidatoDto.getDiaNacimiento()).append("/").
    					append(candidatoDto.getMesNacimiento()).append("/").
    					append(candidatoDto.getAnioNacimiento()).toString(),
    					"dd/MM/yyyy"));
					
					//Se obtiene el tiempo de experiencia laboral en años, meses y dias
					candidatoDto.setTiempoExperienciaLab(DateUtily.daysToYearsMonthDays(candidatoDto.getDiasExperienciaLaboral().intValue()));
					
					//aplicar formato a salario
					//candidatoDto.setSalario(candidatoDto.getSalarioMinPer().toString());
					log4j.debug("<construirSalidaCandidatos> salMin: "+candidatoDto.getSalarioMinPer() + ", salMax: "+candidatoDto.getSalarioMaxPer());
					candidatoDto.setSalarioMinPer(candidatoDto.getSalarioMinPer());
					candidatoDto.setSalarioMaxPer(candidatoDto.getSalarioMaxPer());
					
					//ver handcheck
					//Se adiciona la uri del contenido
					setUriContenido(candidatoDto,true);
				//Empresas
				}else{
					//Se adiciona la uri del contenido
					setUriContenido(candidatoDto,false);
				}
				candidatoDto.setHandshake(null);
//				candidatoDto.setIdEstatusCandidato(null);
				candidatoDto.setDiaNacimiento(null);
				candidatoDto.setMesNacimiento(null);
				candidatoDto.setAnioNacimiento(null);
//				candidatoDto.setDiasExperienciaLaboral(null);
				
				lsCandDtoTemp.add(candidatoDto);
				cantidad++;
				total++;
				
				log4j.debug(" total="+total);
				
				//Se analiza el ultimo registro
				if(total == lsCandDtoIn.size()){
					log4j.debug("<construirSalidaCandidatos> ultimo registro");
					completaSalidaPorEstatus(lsCandDtoTemp,idEstatusCandidatoAnt,candidatoTotalDto, cantidad,cantidadRechazo);
				}					
			}
			
			log4j.debug("<construirSalidaCandidatos> cantidadTotal="+total);
			candidatoTotalDto.setTotal(String.valueOf(total));
			candidatoTotalDto.setEsKO(null);
			candidatoTotalDto.setEntroPrimeravez(null);
			candidatoTotalDto.setEntroPrimeravezDemo(null);

		}
		return candidatoTotalDto;
	}
	
	
	/**
	 * Dado el estatus de candidato se crea el objeto correspondiente
	 * @param lsCandDtoTemp, lista de candidatos
	 * @param idEstatusCandidato, estatus del candidato
	 * @param candidatoTotalDto, el objeto principal
	 * @param cantidad, el numero de candidatos
	 * @param cantidadRechazo, numero de candidatos con rechazo
	 */
	private void completaSalidaPorEstatus(List<CandidatoDto> lsCandDtoTemp,byte idEstatusCandidato,
								CandidatoTotalDto candidatoTotalDto,int cantidad,
								int cantidadRechazo){
		
		//candidato aceptado
		if(idEstatusCandidato == Constante.ESTATUS_CANDIDATO_ACEPTADO){
			log4j.debug(" entra ESTATUS_CANDIDATO_ACEPTADO -> lsCandDtoTemp="+lsCandDtoTemp.size());						
			candidatoDetalleDto=new CandidatoDetalleDto();
			candidatoDetalleDto.setCantidad(String.valueOf(cantidad));
			candidatoDetalleDto.setListado(lsCandDtoTemp);
			candidatoTotalDto.setAceptados(candidatoDetalleDto);
			candidatoTotalDto.setEsKO(new Boolean(true));
		}else{
			//Ahora los ko
			if(idEstatusCandidato != -1){
				//Primera vez que entra un ko
				if(!candidatoTotalDto.getEntroPrimeravez().booleanValue()){
					log4j.debug(" entra a rechazados por primera vez");
					candidatoDetalleDto=new CandidatoDetalleDto();
					candidatoDetalleDto.setCantidad("0");
					candidatoTotalDto.setRechazados(candidatoDetalleDto);
					candidatoTotalDto.setEntroPrimeravez(new Boolean(true));					
				}
											
				//rechazado por IAS
				if(idEstatusCandidato == Constante.ESTATUS_CANDIDATO_RECHAZADO_SALARIO){
					log4j.debug(" entra ESTATUS_CANDIDATO_RECHAZADO_SALARIO -> lsCandDtoTemp="+lsCandDtoTemp.size());	
					candidatoDetalleIASDto=new CandidatoDetalleDto();
					candidatoDetalleIASDto.setCantidad(String.valueOf(cantidad));
					candidatoDetalleIASDto.setListado(lsCandDtoTemp);
					candidatoDetalleDto.setIas(candidatoDetalleIASDto);
					
				//rechazado por IPG
				}else if(idEstatusCandidato == Constante.ESTATUS_CANDIDATO_RECHAZADO_DISTANCIA){
					log4j.debug(" entra ESTATUS_CANDIDATO_RECHAZADO_DISTANCIA -> lsCandDtoTemp="+lsCandDtoTemp.size());	
					candidatoDetalleIPGDto=new CandidatoDetalleDto();
					candidatoDetalleIPGDto.setCantidad(String.valueOf(cantidad));
					candidatoDetalleIPGDto.setListado(lsCandDtoTemp);
					candidatoDetalleDto.setIpg(candidatoDetalleIPGDto);
					
				//rechazado por demograficos, demograficos_academico y demograficos_laboral
				}else if(idEstatusCandidato == Constante.ESTATUS_CANDIDATO_RECHAZADO_DEMOGRAFICOS ||
						idEstatusCandidato == Constante.ESTATUS_CANDIDATO_RECHAZADO_ACADEMICA ||
						idEstatusCandidato == Constante.ESTATUS_CANDIDATO_RECHAZADO_LABORAL){
					log4j.debug(" entra DEMOGRAFICOS -> lsCandDtoTemp="+lsCandDtoTemp.size() +
								" idEstatusCandidatoAnt="+idEstatusCandidato);	
					//solo una vez
					if(!candidatoTotalDto.getEntroPrimeravezDemo().booleanValue()){
						log4j.debug(" entra a rechazados_demografico por primera vez");
						candidatoDetalleDemoDto=new CandidatoDetalleDto();
						candidatoDetalleDemoDto.setCantidad("0");
						candidatoDetalleDemoDto.setDetalle(new ArrayList<CandidatoDetalleDto>());
						candidatoDetalleDto.setDemografico(candidatoDetalleDemoDto);
						candidatoTotalDto.setEntroPrimeravezDemo(new Boolean(true));
					}
				
					//adicionar los objetos por politicas
					completaSalidaPorPoliticas(lsCandDtoTemp);
					
					candidatoDetalleDemoDto.setCantidad(String.valueOf(Integer.parseInt(candidatoDetalleDemoDto.getCantidad())+ cantidad));
				
				//rechazado por	habilidad
				}else if(idEstatusCandidato == Constante.ESTATUS_CANDIDATO_RECHAZADO_HABILIDADES){
					log4j.debug(" entra ESTATUS_CANDIDATO_RECHAZADO_HABILIDADES -> lsCandDtoTemp="+lsCandDtoTemp.size());	
					candidatoDetalleIPGDto=new CandidatoDetalleDto();
					candidatoDetalleIPGDto.setCantidad(String.valueOf(cantidad));
					candidatoDetalleIPGDto.setListado(lsCandDtoTemp);
					candidatoDetalleDto.setHabilidad(candidatoDetalleIPGDto);/**/
					
				}
				
				log4j.debug(" rechazados -> getCantidad="+candidatoDetalleDto.getCantidad()+" cantidadRechazo="+cantidadRechazo);
				candidatoDetalleDto.setCantidad(String.valueOf(cantidadRechazo));
			}	
		}
		log4j.debug("completaSalida() -> esKO="+candidatoTotalDto.getEsKO()+" entroPrimeravez="+candidatoTotalDto.getEntroPrimeravez()); 
	}
	
	/**
	 * Se adicionan objetos por politicas de rechazo
	 * @param lsCandDtoTemp, lista de candidatos por politica
	 */
	private void completaSalidaPorPoliticas(List<CandidatoDto> lsCandDtoTemp){
		int idPoliticaAnt=-1;
		int cont=0,cantidad=0;
		String descripcionAnt=null;
		Iterator<CandidatoDto> itCandidatoDto=lsCandDtoTemp.iterator();
		while(itCandidatoDto.hasNext()){
			CandidatoDto candidatoDto=itCandidatoDto.next();
			log4j.debug(" getIdPoliticaKo="+candidatoDto.getIdPoliticaKo()+
					" idPoliticaAnt="+idPoliticaAnt);
			
			if(candidatoDto.getIdPoliticaKo().intValue() != idPoliticaAnt){
				if(idPoliticaAnt != -1){
					//se adiciona el objeto correspondiente
					detalleSalidaPorPoliticas(descripcionAnt,cantidad);					
				}
				cantidad=0;
				lsCandDtoTempPolitica=new ArrayList<CandidatoDto>();					
				idPoliticaAnt=candidatoDto.getIdPoliticaKo().intValue();
				descripcionAnt=candidatoDto.getSignificado();
			}
			
			candidatoDto.setIdPoliticaKo(null);
			candidatoDto.setSignificado(null);
			lsCandDtoTempPolitica.add(candidatoDto);
			cont++;
			cantidad++;
						
			log4j.debug("completaSalidaPorPoliticas() -> cont= "+cont+" lsCandDtoTemp.size()"+lsCandDtoTemp.size());
			//para la ultima iteracion
			if(cont == lsCandDtoTemp.size()){
				detalleSalidaPorPoliticas(descripcionAnt,cantidad);
			}			
		}
		log4j.debug("completaSalidaPorPoliticas() -> Sale while ");
	}
	
	/**
	 * 
	 * @param descripcion
	 */
	private void detalleSalidaPorPoliticas(String descripcion,int cont){
		candidatoDetallePoliDto=new CandidatoDetalleDto();
		candidatoDetallePoliDto.setCantidad(String.valueOf(cont));
		candidatoDetallePoliDto.setDescripcion(descripcion);
		candidatoDetallePoliDto.setListado(lsCandDtoTempPolitica);
		candidatoDetalleDemoDto.getDetalle().add(candidatoDetallePoliDto);
	}
	
	/**
	 * Se complementan propiedades del candidato
	 * @param candidatoDto
	 * @param lsCandDto
	 * @param sbAreas
	 */
	/*private void completaCandidato(CandidatoDto candidatoDto,List<CandidatoDto> lsCandDtoOut,
									StringBuilder sbAreas, boolean esPersona){
		//solo para candidatos personas
		if(esPersona){
			//se calcula el tiempoExperiencia
			candidatoDto.setTiempoExperiencia(UtilsTCE.redondear((double)
			  						(candidatoDto.getDiasExperienciaLaboral().floatValue() / 360),
			  						2,RoundingMode.CEILING));
			log4j.debug("%$% edad="+candidatoDto.getEdad());
			
		  	//Se calcula la edad
			candidatoDto.setEdad(candidatoDto.getEdad() != null ? candidatoDto.getEdad():
										(DateUtily.calcularEdad(new StringBuilder().
			  			  				append(candidatoDto.getDiaNacimiento()).append("/").
				    					append(candidatoDto.getMesNacimiento()).append("/").
				    					append(candidatoDto.getAnioNacimiento()).toString(),
				    					"dd/MM/yyyy")));
			//Se suman las notas por mientras. Esto se va a obtener de otra manera
  		  	List<String> lsNotas=new ArrayList<String>();
			lsNotas.add("nota1");
			lsNotas.add("nota2");
			candidatoDto.setNotas(lsNotas);
			
			//Se verifica si hay handcheck
			 if(candidatoDto.getHandshake() != null && 
				candidatoDto.getHandshake().booleanValue()){
				 //Se adiciona la uri del contenido
				 setUriContenido(candidatoDto,esPersona);
			  }else{
					  candidatoDto.setFoto("-1");
					  candidatoDto.setNombre("-1");			  
			  }			
		}else{
			//Se calcula la antiguedad
			candidatoDto.setTiempoAntiguedad(DateUtily.calcularEdad(new StringBuilder().
							  				append(candidatoDto.getDiaNacimiento()).append("/").
							  				append(candidatoDto.getMesNacimiento()).append("/").
							  				append(candidatoDto.getAnioNacimiento()).toString(),
											"dd/MM/yyyy"));
			//Se adiciona la uri del contenido
			 setUriContenido(candidatoDto,esPersona);
		}
		//Se adiconan las areas
		//candidatoDto.setAreas(sbAreas.substring(0,(sbAreas.length() - 1)).toString());
		
		//Se ponen a nulo
		candidatoDto.setAnioNacimiento(null);
		candidatoDto.setMesNacimiento(null);
		candidatoDto.setDiaNacimiento(null);
		candidatoDto.setIdArea(null);
		candidatoDto.setNuevoOModificado(null);
		candidatoDto.setHandshake(null);
		
		//Se guarda el registro del candidato anterior
		  lsCandDtoOut.add(candidatoDto);
	}*/
	
	/**
	 * Se adiciona la URI de la foto correspondiente al candidato
	 * @param candidatoDto
	 * @param esPersona
	 * @throws Exception 
	 */
	private void setUriContenido(CandidatoDto candidatoDto, boolean esPersona) throws SystemTCEException{
		  try {
			   JSONObject jsonObject = new JSONObject();
			   jsonObject.put("idEmpresaConf", candidatoDto.getIdEmpresaConf());
			   jsonObject.put("idTipoContenido",Constante.TIPO_CONTENIDO_FOTO);
			   
			   log4j.debug("%/&/ fileServer esPersona="+esPersona);
			   if(esPersona){
					jsonObject.put("idPersona",String.valueOf(candidatoDto.getIdPersona()));
			   }else{
				   jsonObject.put("idEmpresa",String.valueOf(candidatoDto.getIdEmpresa()));
			   }
			   List<FileDto> lsFileDto= gson.fromJson((String)restJsonService.serviceRJTransacStruc(jsonObject.toString(),
										new StringBuilder(Constante.URI_FILE).append(Constante.URI_GET).toString()),
										new TypeToken<List<FileDto>>(){}.getType());
			   log4j.debug("%/&/ fileServer lsFileDto="+lsFileDto.size());
			  if(lsFileDto.size() == 1){
				   FileDto fileDto=lsFileDto.get(0);
				   //No hay error
				   if(fileDto.getCode() == null){
					   candidatoDto.setFoto(fileDto.getUrl());
				   }else{
					   //Error
					   log4j.error("Error al obtener Foto de " + (esPersona  ? " idPerdona="+candidatoDto.getIdPersona():
						   													   " idEmpresa="+candidatoDto.getIdEmpresa()));
				   }
			   }
			 } catch (Exception e) {
				 log4j.error("No se pudo obtener el URI de la foto del idPersona:"+
						 	 candidatoDto.getIdPersona()+" -> error:"+e.toString());
				 e.printStackTrace();
				 throw new SystemTCEException("No se pudo obtener el URI de la foto del idPersona:"+
						 		candidatoDto.getIdPersona()+" Error:"+e);
			 }
	}
	
	/**
	 * Se complementa la lista de candidatos dependiendo si son de personas o empresas
	 * @param lsCandidatoDto, lista de candidatos
	 * @return true si alguna persona o empresa es nuevo o se modifico
	 * 		   false lo contrario
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	private void complementarLsCandidatoDto(List<CandidatoDto> lsCandidatoDto, 
											  CandidatoDto positionInfoDto) throws ParseException{
			Iterator<CandidatoDto> itCandidatoDto=lsCandidatoDto.iterator();
			while(itCandidatoDto.hasNext()){
				CandidatoDto candidatoDto = (CandidatoDto) itCandidatoDto.next();
				 log4j.debug("$%$# getIdEmpresaConf="+positionInfoDto.getIdEmpresaConf()+
						 	"idCandidato="+candidatoDto.getIdCandidato()+
		    	    		" IdPersona="+candidatoDto.getIdPersona()+
		    	    		" getIdEmpresaCandidato="+candidatoDto.getIdEmpresaCandidato()+
		    	    		" FechaCreacion="+candidatoDto.getFechaCreacion()+
		    	    		" getContAreas="+candidatoDto.getContAreas()+
		    	    		" getIdEstatusCandidato="+candidatoDto.getIdEstatusCandidato()+
		    	    		" getIdEstatusOperativo="+candidatoDto.getIdEstatusOperativo()+
		    	    		" getIapBrutoTotal="+candidatoDto.getIapBrutoTotal()+
		    	    		" getIas="+candidatoDto.getIas()+
		    	    		" getIpg="+candidatoDto.getIpg()+
		    	    		" getIap="+candidatoDto.getIap()+
		    	    		" getDistanciaReal="+candidatoDto.getDistanciaReal()+
		    	    		" getDiaNacimiento="+candidatoDto.getDiaNacimiento()+
		    	    		" getMesNacimiento="+candidatoDto.getMesNacimiento()+
		    	    		" Edad="+candidatoDto.getEdad()+
		    	    		" getAnioNacimiento="+candidatoDto.getAnioNacimiento()+
		    	    		" getGoogleLatitudeDom="+candidatoDto.getGoogleLatitudeDom()+
		    	    		" getGoogleLongitudeDom="+candidatoDto.getGoogleLongitudeDom()); 
				//Si algun cv o empresa es nuevo para la posicion o fue modificado
	    	    if(candidatoDto.getIdEstatusCandidato() == null ||
	    	       candidatoDto.getIdEstatusCandidato().byteValue() == Constante.ESTATUS_CANDIDATO_POR_CALCULAR){
	    	    		algunCvModificadoONuevo=true;
	    	    }
	    	   // candidatoDto.setGoogleLatitudeDomPos(positionInfoDto.getGoogleLatitudeDomPos());
	    	   // candidatoDto.setGoogleLongitudeDomPos(positionInfoDto.getGoogleLongitudeDomPos());
	    	   // candidatoDto.setIdMunicipioPos(positionInfoDto.getIdMunicipioPos());
	    	    candidatoDto.setIdPosicion(positionInfoDto.getIdPosicion());
	    	    candidatoDto.setIdEmpresa(positionInfoDto.getIdEmpresa());
	    	    
	    	    //Se obtienen los avisos
	    	    if(candidatoDto.getIdCandidato() != null){
	    	    	candidatoDto.setAvisos(avisoCandidatoDao.get(candidatoDto.getIdCandidato(),true));
	    	    }
	    	    
	    	    //Se obtienen los perfiles correspondientes
	    	    if(candidatoDto.getIdEstatusCandidato() != null ){
	    	    	 //persona_candidato
	    	    	 if(candidatoDto.getIdPersona() != null){  
		    	    		candidatoDto.setLsPerfilDto(applicantDao.getProfilesPeople(
		    	    									positionInfoDto.getIdPosicion(), 
		    	    									candidatoDto.getIdPersona()));
	    	    	  }
	    	    	 //empresa_candidato
	    	    	 if(candidatoDto.getIdEmpresaCandidato() != null){  
	    	    		 	candidatoDto.setLsPerfilDto(applicantDao.getProfilesEnterprise(
													 positionInfoDto.getIdPosicion(), 
													 candidatoDto.getIdEmpresaCandidato()));
	    	    	  } 
	    	    }  
	    	    //Solo para un candidato persona
	    	    if(candidatoDto.getIdPersona() != null){    	
		    	    candidatoDto.setSalarioMinPos(positionInfoDto.getSalarioMinPos());
		    	    candidatoDto.setSalarioMaxPos(positionInfoDto.getSalarioMaxPos());
		    	    candidatoDto.setSalarioAvgPos(positionInfoDto.getSalarioAvgPos());	 
		    	    //Se calcula la edad
			  	  	candidatoDto.setEdad(candidatoDto.getEdad() != null ? candidatoDto.getEdad():
				  	  						DateUtily.calcularEdad(new StringBuilder().
				  			  				append(candidatoDto.getDiaNacimiento()).append("/").
					    					append(candidatoDto.getMesNacimiento()).append("/").
					    					append(candidatoDto.getAnioNacimiento()).toString(),
					    					"dd/MM/yyyy"));
			  	    //Se asigna Handshake
		  		  	candidatoDto.setHandshake(candidatoDto.getIdCandidato() != null ? 
		  		  							  candidatoDto.getHandshake():false);
		  		  	
		  		  	//SE AGREGA CERTIFICACIONES DE PERSONA 
		  		  	Object objLs = personCertService.get(new CertificacionDto(positionInfoDto.getIdEmpresaConf(), 
		  		  															String.valueOf(candidatoDto.getIdPersona()))
		  		  			);
		  		  	if(candidatoDto.getIdPersona()==83){ //ESTE LOG SE HIZO PARA PRUEBAS
		  		  		log4j.debug("\n\n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n %% objLs: " +objLs+"\n" );
		  		  		log4j.debug("\n InstanceOf List?"+ (objLs instanceof List) +"\n\n %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n");		  		  		
		  		  	}
		  		  	if(objLs instanceof List){
						List<CertificacionDto> lsCert =  (List<CertificacionDto>)objLs;
		  		  		if(!lsCert.isEmpty()){
		  		  			candidatoDto.setCertificacion((List<CertificacionDto>)objLs);
		  		  		}
		  		  	}
		  		  	
		  		  	//Se AGREGAN IDIOMAS DE PERSONA
		  		  objLs = langService.get(
		  				  	new IdiomaDto(positionInfoDto.getIdEmpresaConf(), String.valueOf(candidatoDto.getIdPersona()), null)
		  				  );
			  		if(objLs instanceof List){
						List<IdiomaDto> lsIdioma =  (List<IdiomaDto>)objLs;
		  		  		if(!lsIdioma.isEmpty()){
		  		  			candidatoDto.setIdioma(lsIdioma);
		  		  		}
		  		  	}
		  		  	
	    	    }
	    	    //Solo para un empresa persona 
	    	    if(candidatoDto.getIdEmpresaCandidato() != null){
	    	    	
	    	    	//Si el candidato es nuevo o fue modificado
	    	    	if(candidatoDto.getIdEstatusCandidato() == null ||
	    		       candidatoDto.getIdEstatusCandidato().byteValue() == Constante.ESTATUS_CANDIDATO_POR_CALCULAR){
	    	    			//Se asigna estatus aceptado
	    	    			candidatoDto.setIdEstatusCandidato(candidatoDto.getIdEstatusCandidato() == null ?
							    	    						Constante.ESTATUS_CANDIDATO_ACEPTADO:
							    	    						candidatoDto.getIdEstatusCandidato());	    	    			
	    	    	}
	    	    } 
	    	    //Se especifica si fue modificado
    	    	candidatoDto.setModificado(candidatoDto.getIdCandidato() == null ? false:(
			    	    				   candidatoDto.getIdEstatusCandidato().byteValue() == 
			    	    				   Constante.ESTATUS_CANDIDATO_POR_CALCULAR ?  true:false));
    	    	candidatoDto.setNuevoOModificado(false);
		    }
	}
	
	/**
	 * Se aplican todos los indices a las empresas candidato, para la posicion correspondiente
	 * @param lsCandidatoDto, lista de candidatos
	 * @param qos
	 * @param maxAdjacency
	 * @param huboCambioPosicion
	 * @return
	 */
	/*private boolean getIndexesApplicantsEnterprise(List<CandidatoDto> lsCandidatoDto,
			  short maxAdjacency,boolean huboCambioPosicion,BigDecimal googleLatitudeDomPos,
			  BigDecimal googleLongitudeDomPos,Long idMunicipioPos) {
		
		 try {
			 //calcula IPG 
			 Iterator<CandidatoDto> itCandidatoDto=lsCandidatoDto.iterator();
			 while(itCandidatoDto.hasNext()){
				 	log4j.debug("**\n ///////////////// Inicia IPG_empresas /////////////////////");
				 	//posicionLog.info("\n///////////////// Inicia IPG_empresas /////////////////////");
					 calculateIpg(itCandidatoDto.next(), googleLatitudeDomPos,googleLongitudeDomPos, maxAdjacency);
					 //posicionLog.info("\n///////////////// Termina IPG_empresas /////////////////////");
					 log4j.debug("///////////////// Termina IPG_empresas ////////////////////");
				}
			 log4j.debug("\n -///////////////// Inicia completarCuotaDeCandidatos_empresas /////////////////////");
			 //posicionLog.info("\n************************************-///////////////// Inicia completarCuotaDeCandidatos_empresas /////////////////////********************************");
			
			 //Se revisa si se completó la cuota minima de candidatos
			 completarCuotaDeCandidatos(lsCandidatoDto, maxAdjacency);
			 //posicionLog.info("\n**************************************-///////////////// Termina completarCuotaDeCandidatos_empresas /////////////////////*****************************");
			 log4j.debug("** -///////////////// Termina completarCuotaDeCandidatos_empresas ////////////////////");
			 //Se obtienen los rangos de IPG
			 UtilsTCE.getRankedDistribution(lsCandidatoDto,"distanciaReal","ipg",Constante.STAT_SD_METHOD, false);
		 } catch (Exception e) {
			  log4j.error("No se puede obtener el indice IPG :"+e);
			  e.printStackTrace();
			 throw new SystemTCEException("No se puede obtener el indice IPG : "+e);
		}
		 log4j.debug("** hmPerfilNgranDto="+hmPerfilNgranDto);
		 
		 //IAP
		 log4j.debug("** //////////////////////////////// IAP_EMPRESA  //////////////////////////////////////////////  **");
		 //Esto no debe pasar
		 if(hmPerfilNgranDto.size() == 0){
			  //hmPerfilNgranDto sin datos, no se aplica el indice IAP
			  log4j.error(" No se puede calcular el indice IAP ya que no hay ngram por perfil, para la posicion="+
			  lsCandidatoDto.get(0).getIdPosicion());
			  return false;
		 }else{
			 	log4j.debug("Se calcula el iap -> hmPoliticaValor="+hmPoliticaValor+
			 			" itCandidatoDto="+lsCandidatoDto.size());
			 	
			 	hmStatsPerfilEmpresa=new HashMap<String, DescriptiveStatistics>() ;
			 	Iterator<CandidatoDto> itCandidatoDto=lsCandidatoDto.iterator();
				while(itCandidatoDto.hasNext()){
					CandidatoDto candidatoDto=itCandidatoDto.next();
					lsPerfilDto=new ArrayList<PerfilDto>();
					//se revisa la lista de perfiles
					Iterator<Map.Entry<String,List<PoliticaValor>>>  itKeyPoliticaValor=hmPoliticaValor.entrySet().iterator();
					while(itKeyPoliticaValor.hasNext()){
						  Map.Entry<String,List<PoliticaValor>> mePoliticaValor =(Map.Entry<String,List<PoliticaValor>>)itKeyPoliticaValor.next();
						  //Se obtiene el objeto perfilDto
						  lsPerfilDto.add(getPerfilDto((String)mePoliticaValor.getKey(),candidatoDto.getLsPerfilDto()));
					  }
					candidatoDto.setLsPerfilDto(lsPerfilDto);
					log4j.debug(" huboCambioPosicion="+huboCambioPosicion+
							  " getIdEstatusCandidato="+candidatoDto.getIdEstatusCandidato()+
							  " getLsPerfilDto="+candidatoDto.getLsPerfilDto().size());
					//solo los candidatos aceptados y nuevos
					if(huboCambioPosicion ||  (candidatoDto.getIdCandidato() == null ||
					   candidatoDto.getIdEstatusCandidato() == Constante.ESTATUS_CANDIDATO_POR_CALCULAR)){
						//Se calcula IAP
						getEnterpriseIAP(candidatoDto,  hmPerfilNgranDto);
						//candidatoDto.setNuevoOModificado(true);
						candidatoDto.setIdEstatusCandidato(Constante.ESTATUS_CANDIDATO_ACEPTADO);
					}
					//solo los aceptados
					if(candidatoDto.getIdEstatusCandidato() == Constante.ESTATUS_CANDIDATO_ACEPTADO){
						 //Se calculan los valores complementarios estadisticos
						if(candidatoDto.getLsPerfilDto() != null){
							  Iterator<PerfilDto> itPerfilDto=candidatoDto.getLsPerfilDto().iterator();
							  while(itPerfilDto.hasNext()){
								PerfilDto perfilDto=itPerfilDto.next();	
								//Se crea y adiciona valores de esperiencia laboral en objetos DescriptiveStatistics
								// para la aplicacion de la estadarizacion estadistica
								if(hmStatsPerfilEmpresa.containsKey(String.valueOf(perfilDto.getIdPerfil().longValue()))){
									hmStatsPerfilEmpresa.get(String.valueOf(perfilDto.getIdPerfil().longValue())).
														addValue(perfilDto.getEmpresaBruto());
								}else{
									statis= new DescriptiveStatistics();
									statis.addValue(perfilDto.getEmpresaBruto());
									hmStatsPerfilEmpresa.put(String.valueOf(perfilDto.getIdPerfil().longValue()), statis);
								}
							  }
						 }
					}
				}
				
				log4j.debug("%%/  hmStatsPerfilEmpresa="+hmStatsPerfilEmpresa+
						" lsCandidatoDto="+lsCandidatoDto);
				log4j.debug("**********************************************   ESTADISTICOS		 ********************************************");
				itCandidatoDto=lsCandidatoDto.iterator();
				while(itCandidatoDto.hasNext()){
				  CandidatoDto candidatoDto=itCandidatoDto.next();
				  //todos los cvs aceptados
				  if(candidatoDto.getIdEstatusCandidato() == Constante.ESTATUS_CANDIDATO_ACEPTADO){
					  double posicionIAPtotalZ=0;
					  double posicionIAPtotal=0;
					  double perfilIAPtotal=0;
					  double perfilIAPtotalZ=0;
					  if(candidatoDto.getLsPerfilDto() != null){
						  Iterator<PerfilDto> itPerfilDto=candidatoDto.getLsPerfilDto().iterator();
						  while(itPerfilDto.hasNext()){
							  double empresaLaboralZ=0;
							  PerfilDto perfilDto=itPerfilDto.next();
							  //Empresa Z
							  if(hmStatsPerfilEmpresa.containsKey(String.valueOf(perfilDto.getIdPerfil().longValue()))){
								  DescriptiveStatistics descStatistics=hmStatsPerfilEmpresa.get( String.valueOf(
										  							    perfilDto.getIdPerfil().longValue()));
								  //evitar errores NaN
								  if(descStatistics.getStandardDeviation() != 0){
									  empresaLaboralZ=((perfilDto.getExperienciaLaboralBruto() - descStatistics.getMean())
								  					   /descStatistics.getStandardDeviation());
								  }else{
									  empresaLaboralZ=0;
								  }
								  log4j.debug(" empresaLaboralZ --> getEmpresaBruto="+perfilDto.getEmpresaBruto()+
											 " media=" +descStatistics.getMean()+
											 " DesvStdr="+descStatistics.getStandardDeviation()+
											 " experienciaLaboralZ="+empresaLaboralZ);
							  }
							  perfilIAPtotal=(perfilDto.getPonderacion() * perfilDto.getEmpresaBruto());
							  posicionIAPtotal+=perfilIAPtotal;
							  perfilIAPtotalZ=(perfilDto.getPonderacion() * empresaLaboralZ);
							  posicionIAPtotalZ+=perfilIAPtotalZ;
							  log4j.debug(" getIdPerfil="+perfilDto.getIdPerfil()+
									  	 " getPonderacion="+perfilDto.getPonderacion()+
									  	 " perfilIAPtotal="+perfilIAPtotal+
									     " posicionIAPtotal+="+posicionIAPtotal+
									     " perfilIAPtotalZ="+perfilIAPtotalZ+
									     " posicionIAPtotalZ+="+posicionIAPtotalZ);
							  log4j.debug("**************** ");
							  //Se guarda el iap total del perfil
							  perfilDto.setIap(perfilIAPtotalZ);
							  perfilDto.setIapBruto(perfilIAPtotal);  
						  }
						  log4j.debug(" getIdPersona="+candidatoDto.getIdPersona()+
									 " getDemografico="+candidatoDto.getDemografico()+
									 " posicionIAPtotalZ="+posicionIAPtotalZ+
								  	 " IapPuntosZ="+(posicionIAPtotalZ));
						  candidatoDto.setIapPuntosZ(posicionIAPtotalZ);
						  candidatoDto.setIapBrutoTotal(new BigDecimal(posicionIAPtotal));
						  candidatoDto.setNuevoOModificado(true);
						  log4j.debug("*************************************************   		 **********************************************");
					  }
				  }
			    }
				try {
					log4j.debug("%%/ lsCandidatoDto="+lsCandidatoDto);
					 //IAP estadisticos de empresa. Se aplica a todos
					 //Puntos Z
					 UtilsTCE.getRankedDistribution(lsCandidatoDto,"iapPuntosZ","iap",Constante.STAT_QUARTILE_METHOD,false);
				} catch (Exception e) {
					log4j.error("No se puede implementar reflexion para la obtencion de estadisticos en el calculo del indice IAP: "+e);
					e.printStackTrace();
					throw new SystemTCEException("No se puede implementar reflexion para la obtencion de estadisticos en el calculo del indice IAP: "+e);
				} 
		 }
		 return true;
	}*/
	
	
	/**
	 * Se aplican todos los indices a los cvs candidato, para la posicion correspondiente
	 * @param lsCandidatoDto, lista de candidatos
	 * @param qos
	 * @param maxAdjacency
	 * @throws Exception 
	 */
	private boolean getIndexesApplicantsPeople(List<CandidatoDto> lsCandidatoDto, 
		  short maxAdjacency,boolean huboCambioPosicion,BigDecimal googleLatitudeDomPos,
		  BigDecimal googleLongitudeDomPos,Long idMunicipioPos) throws Exception {
		 
		  log4j.debug("<getIndexesApplicantsPeople> hmPoliticaValor="+hmPoliticaValor+" idMunicipioPos="+idMunicipioPos);
		  

		  //Solo para la creacion de la lista de habilidades de la Posicion
		  unaVezListaHabilidadPos=true;
	  	  /* ********************************************************************************************** */
		  /* ***  ITERACION 1 DE LISTA-CANDIDATOS PARA DESCARTAR POR DEMOGRAFICOS Y CALCULAR IAS & IPG  *** */
		  /* ********************************************************************************************** */			  
		  Iterator<CandidatoDto> itCandidatoDto= lsCandidatoDto.iterator();
		  hmPoliticaValorEscolar=new HashMap<String, List<PoliticaValor>>();
		  while(itCandidatoDto.hasNext()){
			  CandidatoDto candidatoDto=itCandidatoDto.next();
			  log4j.debug("<getIndexesApplicantsPeople> EVALUANDO CANDIDATO. idCandidato: " + (candidatoDto.getIdCandidato()!=null?candidatoDto.getIdCandidato():"" )+
					   "\n idPersona="+candidatoDto.getIdPersona()+
					   " huboCambioPosicion="+huboCambioPosicion+
					   " IdEstatusCandidato="+candidatoDto.getIdEstatusCandidato()+
					   " getModificado="+candidatoDto.getModificado()+
					   " LsPerfilDto="+candidatoDto.getLsPerfilDto());
			  sbTempo.append("Aplicadas a idCandidato: ").append(candidatoDto.getIdCandidato()).append("\n");
			  //Solo los nuevos candidatos y por calcular(modificados)
			  if(huboCambioPosicion || 
				 (candidatoDto.getIdEstatusCandidato() == null ||
				 candidatoDto.getIdEstatusCandidato() == Constante.ESTATUS_CANDIDATO_POR_CALCULAR)){
					  log4j.debug("** ///////////////// Inicia demografico (Ponderaciones y KO's) /////////////////////");

					  /* Se analizan los demograficos (Ponderacion y KO's) para Candidato */
					  getDemograficosYKO(candidatoDto,hmPoliticaValor,huboCambioPosicion);
					  
					  log4j.debug("** ///////////////// Termina demografico ////////////////////");
					  /* Si no hubo KO en demograficos, se continua con los Indices IAS e IPG */
					  if(candidatoDto.getIdEstatusCandidato() == Constante.ESTATUS_CANDIDATO_ACEPTADO){
						  try{ 
							  log4j.debug("Candidato Sin KO's en demográfico\t\t ** ///////////////// Inicia IAS /////////////////////");
							  
							  // Calculo del IAS
							  calculateIas(candidatoDto);
							  
							  //posicionLog.info("///////////////// Termina IAS /////////////////////");
							  log4j.debug("** ///////////////// Termina IAS ////////////////////");
						  }catch(NullPointerException e){
							  //Dado esta excepcion no se calcula ias
							  candidatoDto.setIas(null);
							  log4j.error("Excepcion al calcular el IAS para la persona:"+candidatoDto.getIdPersona(), e);
						  }						  
						 
						  /* Si no hubo KO para candidato, se continua con IPG */
						  if(candidatoDto.getIdEstatusCandidato() == Constante.ESTATUS_CANDIDATO_ACEPTADO){	
							  log4j.debug("** ///////////////// Inicia IPG /////////////////////");
							  //posicionLog.info("\n**///////////////// Inicia IPG /////////////////////");
							   //Calcula IPG
							  calculateIpg(candidatoDto, googleLatitudeDomPos,googleLongitudeDomPos, maxAdjacency);
							  //posicionLog.info("\n**///////////////// Termina IPG /////////////////////");
							  log4j.debug("** ///////////////// Termina IPG ////////////////////");
							  if(candidatoDto.getIdEstatusCandidato() == Constante.ESTATUS_CANDIDATO_ACEPTADO){
								  log4j.debug("<getIndexesApplicantsPeople> Candidato "+ candidatoDto.getIdCandidato()+" PRE-ACEPTADO e Indexado (IAS,IPG)\n");
							  }
						  }else {
							  log4j.debug("<getIndexesApplicantsPeople> Candidato "+ candidatoDto.getIdCandidato()+" RECHAZADO por Indice Salarial, no se calculan IPG ni IAP \n");
						  }
					  }
					  else {
						  log4j.debug("<getIndexesApplicantsPeople> Candidato "+ candidatoDto.getIdCandidato()+" RECHAZADO (KO's en demográficos), no se calculan Indices \n");
					  }
			   }
		    } 
		  //echar un vistazo al cache
		  // PoliticaValorElementoCache.viewhmPoliticaValorElemento();
		  log4j.debug("sbTempo: "+sbTempo);
		  try {
			  
			  log4j.debug("**\n\n\n -///////////////// Inicia completarCuotaDeCandidatos /////////////////////");
			  //posicionLog.info("\n************************************-///////////////// Inicia completarCuotaDeCandidatos /////////////////////********************************");
			  
			  //Se revisa si se completó la cuota minima de candidatos
			  completarCuotaDeCandidatos(lsCandidatoDto, maxAdjacency);
			  
			  //posicionLog.info("\n**************************************-///////////////// Termina completarCuotaDeCandidatos /////////////////////*****************************");
			  log4j.debug("** -///////////////// Termina completarCuotaDeCandidatos ////////////////////\n\n\n");
			
			  //Se obtienen los rangos de IAS
			  UtilsTCE.getRankedDistribution(lsCandidatoDto,"iasDistancia","ias",Constante.STAT_QUARTILE_METHOD,false);
			 
			  //Se obtienen los rangos de IPG
			  UtilsTCE.getRankedDistribution(lsCandidatoDto,"distanciaReal","ipg",Constante.STAT_SD_METHOD, true);
			
		  } catch (Exception e) {
			  log4j.error("<getIndexesApplicantsPeople> No se puede obtener el indice IAS o IPG: "+e.getMessage(), e);
			  e.printStackTrace();
			  throw new SystemTCEException("<getIndexesApplicantsPeople> No se puede obtener el indice IAS o IPG:"+e);
			 
		  }	
		
		  
		  /* ********************************************************************************************** */
		  /* ** SOLO SE CALCULA IAP PARA LOS ACEPTADOS EN LA ITERACIÓN ANTERIOR (DEMOGRÁFICOS, IAS, IPG) ** */
		  /* ********************************************************************************************** */
		  log4j.debug("** hmPerfilNgranDto= "+hmPerfilNgranDto.size());
		  if(hmPerfilNgranDto.size() > 0){
			  log4j.debug("\n\n** //////////////////////////////// Calculo de IAP   //////////////////////////////////////////////  **");
				
				//inicializar valores de ponderacion para la obtencion del indice IAP
				//Para el indice de escolaridad
				String kerCastigoXcaducidad=new StringBuilder(Constante.NM_POLITICA_VALOR_POSICION).append("_").
											append(String.valueOf(lsCandidatoDto.get(0).getIdPosicion())).
										    append("_").append(Constante.NM_CASTIGO_X_CADUCIDAD).toString();
				String keyEscolaridadSuperior=new StringBuilder(Constante.NM_POLITICA_VALOR_POSICION).append("_").
											append(String.valueOf(lsCandidatoDto.get(0).getIdPosicion())).
										    append("_").append(Constante.NM_ESCOLARIDAD_SUPERIOR).toString();
				String keyEstatusIncompletos=new StringBuilder(Constante.NM_POLITICA_VALOR_POSICION).append("_").
											append(String.valueOf(lsCandidatoDto.get(0).getIdPosicion())).
											append("_").append(Constante.NM_ESTATUS_INCOMPLETOS).toString();
				String keyGradoEscolar=new StringBuilder(Constante.NM_POLITICA_VALOR_POSICION).append("_").
										append(String.valueOf(lsCandidatoDto.get(0).getIdPosicion())).append("_").
										append(Constante.FORM_ACADEMICA_KO_GA).toString();
				String keyEstatusEscolar=new StringBuilder(Constante.NM_POLITICA_VALOR_POSICION).append("_").
										append(String.valueOf(lsCandidatoDto.get(0).getIdPosicion())).append("_").
										append(Constante.FORM_ACADEMICA_KO_EE).toString();
				castigoXcaducidad=(float)(PoliticaValorElementoCache.containsKey(kerCastigoXcaducidad) ?
										PoliticaValorElementoCache.get(kerCastigoXcaducidad):Constante.CASTIGO_X_CADUCIDAD);
				escolaridadSuperior=(float)(PoliticaValorElementoCache.containsKey(keyEscolaridadSuperior) ?
										PoliticaValorElementoCache.get(keyEscolaridadSuperior):
										Constante.ESCOLARIDAD_SUPERIOR);
				estatusIncompletos=(float)(PoliticaValorElementoCache.containsKey(keyEstatusIncompletos) ?
										PoliticaValorElementoCache.get(keyEstatusIncompletos):
										Constante.ESTATUS_INCOMPLETOS);
				gradoEscolarNivel=(byte) (PoliticaValorElementoCache.containsKey(keyGradoEscolar) ?
									PoliticaValorElementoCache.get(keyGradoEscolar):0);
				estatusEscolarNivel=(byte) (PoliticaValorElementoCache.containsKey(keyEstatusEscolar) ?
									PoliticaValorElementoCache.get(keyEstatusEscolar):0);
				//Se verifica si ya existen los valores de ponderacion en cache para grado academico
				if(!PoliticaValorElementoCache.indexOfKey(Constante.NM_GRADO_ACADEMICO.concat("_"))){
					  //Se obtienen los valores de ponderacion de la Tabla Grado_Academico
					  List<GradoAcademico> lsGradoAcademico=gradoAcademicoDao.findAll();
					  if(lsGradoAcademico == null || lsGradoAcademico.size() == 0){
						  log4j.error("No hay valores de Ponderación en la Tabla Grado_Academico");
					  }else{
						  Iterator<GradoAcademico> itGradoAcademico= lsGradoAcademico.iterator();
						  while(itGradoAcademico.hasNext()){
							  GradoAcademico gradoAcademico=itGradoAcademico.next();
							  //se guarda en cache la ponderacion del nivel
							  setElementoPerfilCache(Constante.NM_GRADO_ACADEMICO,
									  				 gradoAcademico.getIdGradoAcademico(),
									  				 Constante.GA_NIVEL,
									  				 String.valueOf(gradoAcademico.getNivel() == null ? 
									  						 		0:gradoAcademico.getNivel()));
							  //se guarda en cache la ponderacion del dominio
							  setElementoPerfilCache(Constante.NM_GRADO_ACADEMICO,
									  				 gradoAcademico.getIdGradoAcademico(),
									  				 Constante.GA_DOMINIO,
									  				 String.valueOf(gradoAcademico.getDominio()));
							  //se guarda en cache la ponderacion de degradacion
							  setElementoPerfilCache(Constante.NM_GRADO_ACADEMICO,
									  				 gradoAcademico.getIdGradoAcademico(),
									  				 Constante.GA_DEGRADACION,
									  				 String.valueOf(gradoAcademico.getDegradacion()));
							  //se guarda en cache la ponderacion de la duracion
							  setElementoPerfilCache(Constante.NM_GRADO_ACADEMICO,
									  				 gradoAcademico.getIdGradoAcademico(),
									  				 Constante.GA_DURACION,
									  				 String.valueOf(gradoAcademico.getDuracion()));
						  }
					  }
				 }
				 //Se verifica si ya existen los valores ponderados en cache para estatus escolar
				 if(!PoliticaValorElementoCache.indexOfKey(Constante.NM_ESTATUS_ESCOLAR.concat("_"))){	
					  //se obtienen los valores de ponderacion de la Tabla Estatus_Escolar
					  List<EstatusEscolar> lsEstatusEscolar=estatusEscolarDao.findAll();
					  if(lsEstatusEscolar == null || lsEstatusEscolar.size() == 0){
						  log4j.error("No hay valores de Ponderacíon en la Tabla Estatus_Escolar");
					  }else{
						  Iterator<EstatusEscolar> itEstatusEscolar= lsEstatusEscolar.iterator();
						  while(itEstatusEscolar.hasNext()){
							  EstatusEscolar estatusEscolar=itEstatusEscolar.next();
							  //se guarda en cache la ponderacion del nivel
							  setElementoPerfilCache(Constante.NM_ESTATUS_ESCOLAR,
									  				 estatusEscolar.getIdEstatusEscolar(),
									  				 Constante.EE_NIVEL,
									  				 String.valueOf(estatusEscolar.getNivel()));
							  //se guarda en cache la ponderacion del dominio
							  setElementoPerfilCache(Constante.NM_ESTATUS_ESCOLAR,
									  				 estatusEscolar.getIdEstatusEscolar(),
									  				 Constante.EE_PREPONDERANCIA,
									  				 String.valueOf(estatusEscolar.getPreponderancia()));
						  }
					  }
				  }
				  //Revisamos valores en cache
				  //PoliticaValorElementoCache.viewhmPoliticaValorElemento();
				  sbTexto=new StringBuilder();
				  StringBuilder sbPerfil=null;
				  StringBuilder sbExpLab=null;
				  StringBuilder sbEscolaridad=null;
				//  StringBuilder sbDemografico=null;
				  //estandarizacion estadistico
				//  DescriptiveStatistics descStacsDemografico=new DescriptiveStatistics();
				  hmStatsPerfilExpLab=new HashMap<String, DescriptiveStatistics>() ;
				  hmStatsPerfilEscolaridad=new HashMap<String, DescriptiveStatistics>();
				  
				  itCandidatoDto=lsCandidatoDto.iterator();
				  /* ********************************************************************************************** */
				  /* *** ITERACION 2 DE LISTA-CANDIDATOS PARA CALCULAR IAP A ACEPTADOS EN LA ITERACIÓN ANTERIOR *** */
				  /* ********************************************************************************************** */
				  while(itCandidatoDto.hasNext()){
					CandidatoDto candidatoDto=itCandidatoDto.next();
					log4j.debug("%**% 1 getFueModificado="+candidatoDto.getNuevoOModificado()+
					  		" getIdPersona="+candidatoDto.getIdPersona()+
					  		" getIdEstatusCandidato="+candidatoDto.getIdEstatusCandidato()+
					  		" getLsPerfilDto()"+candidatoDto.getLsPerfilDto());
					//solo los aceptados
					if(candidatoDto.getIdEstatusCandidato() == Constante.ESTATUS_CANDIDATO_ACEPTADO){
						  //Solo los modificados o nuevos
						  if(candidatoDto.getNuevoOModificado().booleanValue()){
								  //Se aplica ExperienciaLaboralIAP
								  getExperienciaLaboralIAP(candidatoDto,hmPerfilNgranDto);
								  //Se aplica EscolaridadlIAP
								  getEscolaridadIAP(candidatoDto, hmPerfilNgranDto); 
						  }
						  //Para estandarizacion estadistico(z)
						 // descStacsDemografico.addValue(candidatoDto.getDemografico());
						  log4j.debug("%**% 2 getFueModificado="+candidatoDto.getNuevoOModificado()+
							  		" getIdPersona="+candidatoDto.getIdPersona()+
							  		" getIdEstatusCandidato="+candidatoDto.getIdEstatusCandidato()+
							  		" getLsPerfilDto()"+candidatoDto.getLsPerfilDto());
						  //Se calculan los valores complementarios estadisticos
						  if(candidatoDto.getLsPerfilDto() != null){
							  Iterator<PerfilDto> itPerfilDto=candidatoDto.getLsPerfilDto().iterator();
							  while(itPerfilDto.hasNext()){
								PerfilDto perfilDto=itPerfilDto.next();	
								//Se crea y adiciona valores de esperiencia laboral en objetos DescriptiveStatistics
								// para la aplicacion de la estadarizacion estadistica
								if(hmStatsPerfilExpLab.containsKey(String.valueOf(perfilDto.getIdPerfil().longValue()))){
									hmStatsPerfilExpLab.get(String.valueOf(perfilDto.getIdPerfil().longValue())).
														addValue(perfilDto.getExperienciaLaboralBruto());
								}else{
									statis= new DescriptiveStatistics();
									statis.addValue(perfilDto.getExperienciaLaboralBruto());
									hmStatsPerfilExpLab.put(String.valueOf(perfilDto.getIdPerfil().longValue()), statis);
								}
								
								//Se crea y adiciona valores de escolaridad en objetos DescriptiveStatistics
								// para la implementacion de la estadarizacion estadistica
								if(hmStatsPerfilEscolaridad.containsKey(String.valueOf(perfilDto.getIdPerfil().longValue()))){
									hmStatsPerfilEscolaridad.get(String.valueOf(perfilDto.getIdPerfil().longValue())).
															 addValue(perfilDto.getEscolaridadBruto());
								}else{
									statis= new DescriptiveStatistics();
									statis.addValue(perfilDto.getEscolaridadBruto());
									hmStatsPerfilEscolaridad.put(String.valueOf(
									perfilDto.getIdPerfil().longValue()), statis);
								}
							}
						}
						  log4j.debug("<getIndexesApplicantsPeople> Candidato "+ candidatoDto.getIdCandidato()+" ACEPTADO e Indexado (IAP,IAS,IPG)\n");
					}
				  }
				  //IAP estandarizacion estadisticos(z)  
				 // log4j.debug("%%  descStacsDemografico="+descStacsDemografico);
				  log4j.debug("%%  hmStatsPerfilEscolaridad="+hmStatsPerfilEscolaridad);
				  log4j.debug("%%  hmStatsPerfilExpLab="+hmStatsPerfilExpLab);
				  log4j.debug("**********************************************   ESTADISTICOS	********************************************");
				  itCandidatoDto=lsCandidatoDto.iterator();
				  while(itCandidatoDto.hasNext()){
					  CandidatoDto candidatoDto=itCandidatoDto.next();
					  //todos los cvs aceptados
					  if(candidatoDto.getIdEstatusCandidato() == Constante.ESTATUS_CANDIDATO_ACEPTADO){
						  double posicionIAPtotalZ=0;
						  double posicionIAPtotal=0;
						  double perfilIAPtotal=0;
						  double perfilIAPtotalZ=0;
						  double demograficoZ=0;
						  if(candidatoDto.getLsPerfilDto() != null){
							  Iterator<PerfilDto> itPerfilDto=candidatoDto.getLsPerfilDto().iterator();
							  while(itPerfilDto.hasNext()){
								  double experienciaLaboralZ=0;
								  double escolaridadZ=0;
								  PerfilDto perfilDto=itPerfilDto.next();
								  sbPerfil=new StringBuilder(Constante.NM_POLITICA_VALOR_PERFIL).
											append("_").append(perfilDto.getIdPerfil().longValue()).append("_");
								  sbExpLab=new StringBuilder(sbPerfil.toString()).
										   append(Constante.PONDERACION_EXPERIENCIA_LABORAL_KEY);
								  sbEscolaridad=new StringBuilder(sbPerfil.toString()).
					   							append(Constante.PONDERACION_ESCOLARIDAD_KEY);
								  
								  //Experiencia Laboral Z
								  if(hmStatsPerfilExpLab.containsKey(String.valueOf(perfilDto.getIdPerfil().longValue()))){
									  DescriptiveStatistics descStatistics=hmStatsPerfilExpLab.get( String.valueOf(
											  							    perfilDto.getIdPerfil().longValue()));
									 
									  //evitar errores NaN
									  if(descStatistics.getStandardDeviation() != 0){
										  experienciaLaboralZ=(PoliticaValorElementoCache.containsKey(sbExpLab.toString())  ? 
													 	   PoliticaValorElementoCache.get(sbExpLab.toString()).floatValue():
													 	   Constante.PONDERACION_EXPERIENCIA_LABORAL) *
										  				   ((perfilDto.getExperienciaLaboralBruto() - descStatistics.getMean())
									  					   /descStatistics.getStandardDeviation());
									  }else{
										  experienciaLaboralZ=0;
									  }
									  log4j.debug(" ExperienciaLaboralZ --> getExperienciaLaboralBruto="+perfilDto.getExperienciaLaboralBruto()+
												 " media=" +descStatistics.getMean()+
												 " DesvStdr="+descStatistics.getStandardDeviation()+
												 " experienciaLaboralZ="+experienciaLaboralZ);
									  
								  }
								  //Escolaridad Z
								  if(hmStatsPerfilEscolaridad.containsKey(String.valueOf(perfilDto.getIdPerfil().longValue()))){
									  DescriptiveStatistics descStatistics=hmStatsPerfilEscolaridad.
											  							   get(String.valueOf(perfilDto.getIdPerfil()));
									  //evitar errores NaN
									  if(descStatistics.getStandardDeviation() != 0){
										  escolaridadZ=(PoliticaValorElementoCache.containsKey(sbEscolaridad.toString())  ? 
													 PoliticaValorElementoCache.get(sbEscolaridad.toString()).floatValue():
													 Constante.PONDERACION_ESCOLARIDAD) *
										  		   ((perfilDto.getEscolaridadBruto() - descStatistics.getMean())
									  				/descStatistics.getStandardDeviation());
									  }else{
										  escolaridadZ=0;
									  }
									  
									  log4j.debug(" EscolaridadZ --> getEscolaridadBruto="+perfilDto.getEscolaridadBruto()+
												 " media=" +descStatistics.getMean()+
												 " DesvStdr="+descStatistics.getStandardDeviation()+
												 " escolaridadZ="+escolaridadZ);
								  }
								  perfilIAPtotal=(perfilDto.getPonderacion() * (perfilDto.getExperienciaLaboralBruto() + 
										  		  perfilDto.getEscolaridadBruto()));
								  posicionIAPtotal+=perfilIAPtotal;
								  perfilIAPtotalZ=(perfilDto.getPonderacion() * (experienciaLaboralZ + escolaridadZ));
								  posicionIAPtotalZ+=perfilIAPtotalZ;
								  log4j.debug(" getIdPerfil="+perfilDto.getIdPerfil()+
										  	 " getPonderacion="+perfilDto.getPonderacion()+
										  	 " perfilIAPtotal="+perfilIAPtotal+
										     " posicionIAPtotal+="+posicionIAPtotal+
										     " perfilIAPtotalZ="+perfilIAPtotalZ+
										     " posicionIAPtotalZ+="+posicionIAPtotalZ);
								  log4j.debug("**************** ");
								  //Se guarda el iap total del perfil
								  perfilDto.setIap(perfilIAPtotalZ);
								  perfilDto.setIapBruto(perfilIAPtotal);
								  perfilDto.setEscolaridad(escolaridadZ);
								  perfilDto.setExperienciaLaboral(experienciaLaboralZ);
							  }
							  //Por el momento no hay ponderacion por demograficos
							  //Demograficos Z
							/*  sbDemografico=new StringBuilder(Constante.NM_POLITICA_VALOR_POSICION).
   											append("_").append(candidatoDto.getIdPosicion()).append("_")
   											.append(Constante.PONDERACION_DEMOGRAFICOS_KEY);
							  //evitar errores NaN
							  if(descStacsDemografico.getStandardDeviation() != 0){ 
								  demograficoZ=(PoliticaValorElementoCache.containsKey(sbDemografico.toString())  ? 
				 								 PoliticaValorElementoCache.get(sbDemografico.toString()).floatValue():
									             Constante.PONDERACION_DEMOGRAFICOS) *
											    ((candidatoDto.getDemografico() - descStacsDemografico.getMean())
												 / descStacsDemografico.getStandardDeviation());
							  }else{
								  demograficoZ=0;
							  }*/
							  log4j.debug(" getIdPersona="+candidatoDto.getIdPersona()+
										 " getDemografico="+candidatoDto.getDemografico()+
										 " demograficoZ="+demograficoZ+
										 " posicionIAPtotalZ="+posicionIAPtotalZ+
									  	 " IapPuntosZ="+(demograficoZ + posicionIAPtotalZ));
							  candidatoDto.setIapPuntosZ(demograficoZ + posicionIAPtotalZ);
							  candidatoDto.setIapBrutoTotal(new BigDecimal(candidatoDto.getDemografico() + posicionIAPtotal));
							  candidatoDto.setNuevoOModificado(true);
							  
							  log4j.debug("*************************************************   		 **********************************************");
						  }
				  	  }
				  }
				  //FIN de Iteracion de candidatos
				  try {
					 //IAP estadisticos. Se aplica a todos
					 //Puntos Z
					 UtilsTCE.getRankedDistribution(lsCandidatoDto,"iapPuntosZ","iap",Constante.STAT_QUARTILE_METHOD,false);
 
						 //Puntos  
					// UtilsTCE.getRankedDistribution(lsCandidatoDtoFiltroKO,"iapPuntos","iap",Constante.STAT_QUARTILE_METHOD);
				  } catch (Exception e) {
					log4j.error("No se puede implementar reflexion para la obtencion de estadisticos en el calculo del indice IAP: "+e);
					e.printStackTrace();
					throw new SystemTCEException("No se puede implementar reflexion para la obtencion de estadisticos en el calculo del indice IAP:"+e.getMessage());
				  } 
		  }else{
			  //hmPerfilNgranDto sin datos, no se aplica el indice IAP
			  log4j.error(" No se puede calcular el indice IAP ya que no hay ngram por perfil, para la posicion="+
			  lsCandidatoDto.get(0).getIdPosicion());
			  return false;
			  //TODO ver con hector cual seria el valor de iap si ya existe en la tabla
		  }
		 
		  
		 /////////////////////////////////////////////////////////////////////////////////////////////////////////// 
		//Se itera para revisar la lista 
		/*itCandidatoDto= lsCandidatoDto.iterator();
		while(itCandidatoDto.hasNext()){
			CandidatoDto candidatoDto=itCandidatoDto.next();
			log4j.info("************************************************************************		**********************************");
			log4j.info("## final -> getIdPersona="+candidatoDto.getIdPersona()+
				    " getIdCandidato="+candidatoDto.getIdCandidato()+
				    " getIapPuntos="+candidatoDto.getIapPuntos()+
				    " getIapPuntosZ="+candidatoDto.getIapPuntosZ()+
				    " getIpg="+candidatoDto.getIpg()+
				    " getIas="+candidatoDto.getIas()+
				    " getIap="+candidatoDto.getIap()+
				    " getDemografico="+candidatoDto.getDemografico()+
				    " estatus_candidato="+candidatoDto.getIdEstatusCandidato()+
    	    		" getIdEstatusOperativo="+candidatoDto.getIdEstatusOperativo()+
    	    		" getIapBrutoTotal="+candidatoDto.getIapBrutoTotal());
			if(candidatoDto.getLsPerfilDto() != null){
				Iterator<PerfilDto> itPerfilDto=candidatoDto.getLsPerfilDto().iterator();
				while(itPerfilDto.hasNext()){
					PerfilDto perfilDto=itPerfilDto.next();								
					log4j.info(" \tidPerfil="+perfilDto.getIdPerfil()+
							" getIdPersonaPerfil="+perfilDto.getIdPersonaPerfil()+
							" experienciaLaboralBruto="+perfilDto.getExperienciaLaboralBruto()+
							" escolaridadBruto="+perfilDto.getEscolaridadBruto()+
							" getIap="+perfilDto.getIap()+
							" getPonderacion="+perfilDto.getPonderacion()); 
				}
			}
			
		}*/
		log4j.info("\n \n**************************************************** FIN DE PROCESO getIndexesApplicantsPeople	*********************************\n\n\n");
		
		return true;
	}
	
	
	/**
	 * Se obtienen los ngram del perfil correspondiente
	 * @param hmPerfilNgranDto, 
	 * @param idPerfil, id del perfil
	 */
	private void getPerfilNgram(HashMap<String,List<PerfilNgramDto>> hmPerfilNgranDto,long idPerfil ){
		  log4j.debug("<getPerfilNgram> hmPerfilNgranDto: " + hmPerfilNgranDto );
		  log4j.debug("<getPerfilNgram> idPerfil: " + idPerfil);
		  
		List<PerfilNgramDto> lsPerfilTextoNgramDto=perfilTextoNgramDao.getText(idPerfil);
		  log4j.debug(" %&&& lsPerfilTextoNgramDto="+lsPerfilTextoNgramDto);
		  //Se guarda el perfil con sus respectivas funciones de cada area correspondiente
		  if(lsPerfilTextoNgramDto != null)
			  hmPerfilNgranDto.put(String.valueOf(idPerfil),lsPerfilTextoNgramDto);
	}

	
	/**
	 * 
	 * @param perfilPondera
	 * @param lsperfilPondera
	 * @return
	 */
	private PerfilDto getPerfilDto(String perfilPondera,List<PerfilDto> lsperfilPondera){
		  StringTokenizer st=new StringTokenizer(perfilPondera,"_");
		  PerfilDto perfilDto=new PerfilDto();
		  perfilDto.setIdPerfil(Long.parseLong(st.nextToken()));
		  perfilDto.setPonderacion(Double.valueOf(st.nextToken()).doubleValue());
		  //Se verifica si existe el candidato y tiene informacion en la tabla PersonaPerfil
		  if(lsperfilPondera != null && lsperfilPondera.size() > 0 ){
			  Iterator<PerfilDto> itPerfilDto= lsperfilPondera.iterator();
			  while(itPerfilDto.hasNext()){
				  PerfilDto perfilDto1=itPerfilDto.next();
				  log4j.debug("$&% idPerfil1="+perfilDto.getIdPerfil()+" dPerfil2="+perfilDto1.getIdPerfil());
				  //Si ya existe el perfil
				  if(perfilDto.getIdPerfil() == perfilDto1.getIdPerfil()){
					  log4j.debug("$&% se reutiliza perfilTDO");
					  perfilDto.setIdPersonaPerfil(perfilDto1.getIdPersonaPerfil());
					  perfilDto.setIdEmpresaPerfil(perfilDto1.getIdEmpresaPerfil());
					  perfilDto.setFechaCreacionPersonaPerfil(perfilDto1.getFechaCreacionPersonaPerfil());
					  perfilDto.setFechaCreacionEmpresaPerfil(perfilDto1.getFechaCreacionEmpresaPerfil());
					  break;
				  }
			  }
		  }
		  return perfilDto;
	}
	
	
	/**
	 * Analiza las politicas demograficas, pondera a consecuencia o ejecuta KO's.
	 * Tambien guarda en cache parametros para los calculos posteriores
	 * @param candidatoDto a analizar
	 * @param hmPoliticaValor, contiene las politicas de cada perfil correspondiente
	 * @return true si hay un ko
	 * 		   false si no hay ko
	 */
	@SuppressWarnings("unlikely-arg-type")
	private void getDemograficosYKO(CandidatoDto candidatoDto, HashMap<String,List<PoliticaValor>> hmPoliticaValor, 
									boolean huboCambioPosicion) {
		  boolean ko=false;
		  byte estatusRechazo=Constante.ESTATUS_CANDIDATO_RECHAZADO_DEMOGRAFICOS;
		  PerfilDto perfilDto=null;
		  lsPerfilDto=new ArrayList<PerfilDto>();
		  List<AvisoDto> avisos=candidatoDto.getAvisos() != null ? candidatoDto.getAvisos():new ArrayList<AvisoDto>();
		  double sumaConcepto=0;
		  log4j.debug("<getDemograficosYKO> Evaluando idPersona:"+candidatoDto.getIdPersona()+
				  "\n IdEstadoCivil="+candidatoDto.getIdEstadoCivil()+
				  " IdTipoDispViajar="+candidatoDto.getIdTipoDispViajar()+
				  " Sexo(genero)="+candidatoDto.getIdTipoGenero()+
				  " PermisoTrabajo="+candidatoDto.getPermisoTrabajo()+
				  " DisponibilidadHorario="+candidatoDto.getDisponibilidadHorario()+
//				  " Certificaciones="+candidatoDto.getCertificacion()+
				  " Edad="+candidatoDto.getEdad());
		  Iterator<Map.Entry<String,List<PoliticaValor>>>  itKeyPoliticaValor=hmPoliticaValor.entrySet().iterator();
		  while(itKeyPoliticaValor.hasNext()){
			  List<PoliticaValor> lsPoliticaValorEscolar=new ArrayList<PoliticaValor>();
			  Map.Entry<String,List<PoliticaValor>> mePoliticaValor =(Map.Entry<String,List<PoliticaValor>>)itKeyPoliticaValor.next();
			  //Se obtiene el objeto perfilDto
			  perfilDto=getPerfilDto((String)mePoliticaValor.getKey(),candidatoDto.getLsPerfilDto());
			  
			  log4j.debug("## getDemograficosYKO() -> Aplica politicas_valor -> getIdPerfil="+perfilDto.getIdPerfil()+
					  	 " getPonderacion="+perfilDto.getPonderacion()+
					  	 " getIdPersonaPerfil="+perfilDto.getIdPersonaPerfil()+
					  	 " lsPoliticaValor="+((List<PoliticaValor>)mePoliticaValor.getValue()).size());
			  
			  
			 // float ponderacionConcepto=0;
			  //double valorConceptoPolitica=0;
			//  long idConceptoAnt=0;
			  Iterator<PoliticaValor> itPoliticaValor=((List<PoliticaValor>)mePoliticaValor.getValue()).iterator();
			  
			  /* ************************************************************************ */
			  /* ************************************************************************ */
			  /* ************************************************************************ */
			  while(itPoliticaValor.hasNext()){
				  PoliticaValor politicaValor=itPoliticaValor.next();
				  log4j.debug("<getDemograficosYKO> IdPoliticaValor="+politicaValor.getIdPoliticaValor()+ "\n"+
						  " IdPolitica="+politicaValor.getPolitica().getIdPolitica()+
						  " clave_politica="+politicaValor.getPolitica().getClavePolitica()+
						  " TipoValor="+politicaValor.getPolitica().getTipoValor()+
						  " Perfil="+(politicaValor.getPerfil() != null ? politicaValor.getPerfil().getIdPerfil():null)+
						  " Posicion="+(politicaValor.getPosicion() != null ? politicaValor.getPosicion().getIdPosicion():null)+
						  " Seccion="+(politicaValor.getSeccion() != null ? politicaValor.getSeccion().getIdSeccion():null)+
						  " C_IdSeccion="+(politicaValor.getConcepto() != null ? 
											politicaValor.getConcepto().getSeccion().getIdSeccion():null)+
						  " IdConcepto="+(politicaValor.getConcepto() != null ? politicaValor.getConcepto().getIdConcepto():null)+
						  " Concepto="+(politicaValor.getConcepto() != null ? politicaValor.getConcepto().getDescripcion():null)+
						  " Valor="+politicaValor.getValor()+
						  " IdPropositoPolitica="+politicaValor.getPolitica().getPropositoPolitica().getIdPropositoPolitica()
						  );
				  sbTempo.append("IdPoliticaValor="+politicaValor.getIdPoliticaValor()).append("  ")
				  .append(" IdPolitica="+politicaValor.getPolitica().getIdPolitica())
				  .append(" clave_politica="+politicaValor.getPolitica().getClavePolitica())
				  .append(" TipoValor="+politicaValor.getPolitica().getTipoValor())
				  .append(" Perfil="+(politicaValor.getPerfil() != null ? politicaValor.getPerfil().getIdPerfil():null))
				  .append(" Posicion="+(politicaValor.getPosicion() != null ? politicaValor.getPosicion().getIdPosicion():null))
				  .append(" Seccion="+(politicaValor.getSeccion() != null ? politicaValor.getSeccion().getIdSeccion():null))
				  .append(" C_IdSeccion="+(politicaValor.getConcepto() != null ? 
				  											politicaValor.getConcepto().getSeccion().getIdSeccion():null))
				  .append(" IdConcepto="+(politicaValor.getConcepto() != null ? politicaValor.getConcepto().getIdConcepto():null))
				  .append(" Concepto="+(politicaValor.getConcepto() != null ? politicaValor.getConcepto().getDescripcion():null))
				  .append(" Valor="+politicaValor.getValor())
				  .append(" IdPropositoPolitica="+politicaValor.getPolitica().getPropositoPolitica().getIdPropositoPolitica())
				  .append("\n");
				  
				  //Se obtienen las politicas_valor relacionadas al perfil
				  if(politicaValor.getPerfil() != null){
					  log4j.debug("<getDemograficosYKO> Politica-Perfil: IdPoliticaValor="+politicaValor.getIdPoliticaValor()+
							  	" Valor="+politicaValor.getValor());
					   //se guardan las politicas valor de ponderacion en cache
					  if(politicaValor.getPoliticaMValors().size() == 0 ){
						   setElementoPerfilCache(Constante.NM_POLITICA_VALOR_PERFIL,
									  				 politicaValor.getPerfil().getIdPerfil(),
									  				 politicaValor.getPolitica().getClavePolitica(),
									  				 politicaValor.getValor());
					  }
				  //Se obtienen las politicas_valor relacionadas a la posicion
				  }else{
					  if(politicaValor.getPosicion()  != null){  
						     //Se obtienen las politicas_valor relacionadas con concepto
							 if(politicaValor.getConcepto() == null){
								 log4j.debug("<getDemograficosYKO> Politica-Posicion-ValorUnico (Sin Concepto)");
								 if(politicaValor.getPolitica().getTipoValor().equals(Constante.TIPO_VALOR_UNICO) ){
									 //Se guarda la ponderacion para calcular el demografico 
									 setElementoPerfilCache(Constante.NM_POLITICA_VALOR_POSICION,
											  				 politicaValor.getPosicion().getIdPosicion(),
											  				 politicaValor.getPolitica().getClavePolitica(),
											  				 politicaValor.getValor());
								 }								 
								 
								 
								 //Se obtiene la ponderación de la sección 
								/* if(politicaValor.getPolitica().getPropositoPolitica().getIdPropositoPolitica() == Constante.PROPOSITO_VALOR &&
									politicaValor.getPolitica().getTipoValor().equals(Constante.TIPO_VALOR_UNICO)){
									 ponderacionSeccion=Float.parseFloat(politicaValor.getValor());
								 }*/
							 //Es concepto
							 }else {
								 log4j.debug("<getDemograficosYKO> Politica-Posicion-Concepto: "
										 + politicaValor.getConcepto().getDescripcion()
										 + " IdPropositoPolitica="+politicaValor.getPolitica().getPropositoPolitica().getIdPropositoPolitica()
										 + " TipoValor="+politicaValor.getPolitica().getTipoValor()
										 );
								 //Se cambia el valor a nulo si no hay un ko, al final del bucle
								 candidatoDto.setIdPoliticaKo(politicaValor.getPolitica().getIdPolitica());
								 
								  //Tipo valor multi
								  if(politicaValor.getPolitica().getTipoValor().equals(Constante.TIPO_VALOR_MULTI)){
									  log4j.debug("<getDemograficosYKO> Politica-Posicion-Multiple ");
									  
									  if(politicaValor.getPolitica().getPropositoPolitica().getIdPropositoPolitica() 
											  == Constante.PROPOSITO_KO){
										  //Hay politicas multi
										  if(politicaValor.getPoliticaMValors() != null && politicaValor.getPoliticaMValors().size() > 0){
											  log4j.debug("<getDemograficosYKO> VALOR_MULTI-> Se evaluan "+politicaValor.getPoliticaMValors().size()+" PoliticaMValor's");
											  
											  Iterator<PoliticaMValor> itPoliticaMValors= politicaValor.getPoliticaMValors().iterator();
											  /* ** **** Iterar politicas M Valor *** *** */
											  while(itPoliticaMValors.hasNext()){
												  PoliticaMValor politicaMValor=itPoliticaMValors.next();
												  
												  log4j.debug("<getDemograficosYKO> PoliticaMValor.clave="+politicaMValor.getPoliticaValor().getPolitica().getClavePolitica());
												  
												  
												  /* CASO Estado civil */
												  if(politicaMValor.getEstadoCivil() != null ){
													  
													  log4j.debug("<getDemograficosYKO> Tipo Estado civil:  "+
															 "Pos="+politicaMValor.getEstadoCivil().getIdEstadoCivil()+
															 " Cand="+candidatoDto.getIdEstadoCivil());
													  
													  //si hay dato en candidato
													  if(candidatoDto.getIdEstadoCivil() != null){
														  
														  //se borra el aviso
														  aviso(avisos,Constante.AVISO_EMPTY_EDO_CIVIL,true);
														  
														  //se analiza el ko
														  if(politicaMValor.getEstadoCivil().getIdEstadoCivil() != 
															 candidatoDto.getIdEstadoCivil().longValue()){													  
															  log4j.debug("<getDemograficosYKO> KO: Estado civil");
															  ko=true;
															  break;												  
														  }
													  }else{
														  	//se crea aviso de falta de dato en Persona
															aviso(avisos,Constante.AVISO_EMPTY_EDO_CIVIL,false);
													  }	  
												  }
												  
												  /* CASO Disponibilidad a viajar */
												  if(politicaMValor.getTipoDispViajar() != null ){
													  
													  log4j.debug("<getDemograficosYKO> Tipo Disp. Viajar:  "+
																 "Pos="+politicaMValor.getTipoDispViajar().getIdTipoDispViajar()+
																 " Cand="+candidatoDto.getIdTipoDispViajar());
													  
													  if(candidatoDto.getIdTipoDispViajar() != null){
														  
														  //se borra el aviso
														  aviso(avisos,Constante.AVISO_EMPTY_TIPO_JORNADA,true);
														  
														  if(candidatoDto.getIdTipoDispViajar().byteValue() == 
															Constante.DISPONIBILIDAD_VIAJAR_NO &&
															(politicaMValor.getTipoDispViajar().getIdTipoDispViajar() != 
															 candidatoDto.getIdTipoDispViajar().longValue())){
															  log4j.debug("<getDemograficosYKO> KO: Disp a viajar");
															  ko=true;
															  break;
														  }
														  
													  }else{
														  //aviso de no encontrar la prop. en candidato
														  aviso(avisos,Constante.AVISO_EMPTY_TIPO_JORNADA,false);
													  }	 
												  }
												  
												  /* CASO genero */
												  if(politicaMValor.getTipoGenero() != null ){
													  
													  log4j.debug("<getDemograficosYKO> Tipo Genero:  "+
																 "Pos="+politicaMValor.getTipoGenero().getIdTipoGenero()+
																 " Cand="+candidatoDto.getIdTipoGenero());
													  
													  if(candidatoDto.getIdTipoGenero() != null){
														  
														  // falta quitar aviso
														  
														   if(politicaMValor.getTipoGenero().getIdTipoGenero() != 
															 candidatoDto.getIdTipoGenero().longValue()){
															   log4j.debug("<getDemograficosYKO> KO: Genero");
															  ko=true;
															  break;
														  } 
													  }else{
														  // falta dar de alta aviso
													  }											  	 
												  }
												  
												  /* TODO CASO Idioma  */
												  if(politicaValor.getPolitica().getClavePolitica().equals(Constante.CLAVE_POLITICA_IDIOMA)
														  ){
													  log4j.debug("\n\n&& Buscando Idioma: " + politicaMValor.getIdioma().getIdIdioma()+ "-"+ politicaMValor.getIdioma().getDescripcion()
															  + "\n VALIDAR IDIOMA \n\n");
													  if(candidatoDto.getIdioma()==null){
														  estatusRechazo=Constante.ESTATUS_CANDIDATO_RECHAZADO_ACADEMICA;
														  log4j.debug("<getDemograficosYKO> KO: IDIOMA (candidato no tiene idiomas) ");
														  ko=true;
														  break;
													  }else{
														  boolean coincid = false;
														  Iterator<IdiomaDto> itIdiom = candidatoDto.getIdioma().iterator();
														  IdiomaDto idiomaDto;
														  while(itIdiom.hasNext()){
															  idiomaDto = itIdiom.next();
															  if(idiomaDto.getIdIdioma()!=null &&
																	  Long.parseLong(idiomaDto.getIdIdioma())==politicaMValor.getIdioma().getIdIdioma()){
																  log4j.debug("Idioma Coincide, se compara nivel  ");
																  if(idiomaDto.getIdDominio()!=null &&
																		  Long.parseLong(idiomaDto.getIdDominio()) >= politicaMValor.getDominio().getIdDominio()){
																	  log4j.debug("Idioma y nivel Coinciden");
																	  coincid = true;
//																	  break;
																  }
															  }
														  }
														  if(!coincid){
															  log4j.debug("<getDemograficosYKO> KO: IDIOMA (Idioma id y/o nivel No coinciden) ");
															  estatusRechazo=Constante.ESTATUS_CANDIDATO_RECHAZADO_ACADEMICA;
															  ko=true;
															  break;
														  }
													  }
												  }
												  
												  /* TODO CASO certificaciones ??? */
												  if(politicaValor.getPolitica().getClavePolitica().equals(Constante.CLAVE_POLITICA_CERTIFICACION)
														&& politicaMValor.getDescripcion()!=null  ){
													  log4j.debug("\n\n&& Buscando CERTIFICACION: " + politicaMValor.getDescripcion());
													  //* Validacion basica TODO: probar si conviene KO o Ponderación
													  if(candidatoDto.getCertificacion()==null){
														  estatusRechazo=Constante.ESTATUS_CANDIDATO_RECHAZADO_ACADEMICA;
														  log4j.debug("<getDemograficosYKO> KO: CERTIFICACION (candidato no tiene certificaciones) ");
														  ko=true;
														  break;
													  }else{
														  int aprov = UtilsTCE.evalCert(politicaMValor.getDescripcion(), candidatoDto.getCertificacion());
														  /*
														  int aprov = 0, tmp=0;
														  Iterator<CertificacionDto> itCertDto = candidatoDto.getCertificacion().iterator();
														  while(itCertDto.hasNext()){														  
															  tmp = getKOCert(politicaMValor.getDescripcion(), itCertDto.next().getTituloCert() );//6;
															  if(tmp>aprov){
																  aprov = tmp;
															  }
														  }//*/
														  if(aprov<5){ //5- Fail, 6+ Pass
															  ko=true;
															  estatusRechazo=Constante.ESTATUS_CANDIDATO_RECHAZADO_ACADEMICA;
															  log4j.debug("<getDemograficosYKO> KO: CERTIFICACION (Coincidencia) No encontrada");
															  break;
														  }
													  }//*/
												  }
												  
												  /* CASO Formacion academica */
												  if(politicaMValor.getPoliticaValor().getPolitica().getClavePolitica().
															equals(Constante.POLITICA_VALOR_FORM_ACADEMICA_KO_MIN) ||
														politicaMValor.getPoliticaValor().getPolitica().getClavePolitica().
															equals(Constante.POLITICA_VALOR_FORM_ACADEMICA_KO_MAX)){
														
														
														log4j.debug("TIPO_VALOR_MULTI-> pos_GradoAcademicoNivel="+
																(politicaMValor.getGradoAcademico() == null ? 
																		null:politicaMValor.getGradoAcademico().getNivel())+
																" candto_GradoAcademicoNivel="+candidatoDto.getGradoAcademicoNivel());
														log4j.debug("TIPO_VALOR_MULTI-> pos_EstatusEscolarNivel="+
																		(politicaMValor.getEstatusEscolar() == null ? 
																				null:politicaMValor.getEstatusEscolar().getNivel())+
																" candto_EstatusEscolarNivel="+candidatoDto.getEstatusEscolarNivel());
														
														 if(candidatoDto.getGradoAcademicoNivel() != null){
															 
															 // falta dar de alta aviso?
															 
															 //candto_GradoAcademicoNivel < pos_grado_academ_min_nivel ó
															 //candto_GradoAcademicoNivel > pos_grado_academ_max_nivel
															 if((politicaMValor.getPoliticaValor().getPolitica().getClavePolitica().
																 equals(Constante.POLITICA_VALOR_FORM_ACADEMICA_KO_MIN) && 
																 (candidatoDto.getGradoAcademicoNivel().byteValue() <
																 politicaMValor.getGradoAcademico().getNivel().byteValue())) ||
																 (politicaMValor.getPoliticaValor().getPolitica().getClavePolitica().
																 equals(Constante.POLITICA_VALOR_FORM_ACADEMICA_KO_MAX) && 
																 (candidatoDto.getGradoAcademicoNivel().byteValue() >
																 politicaMValor.getGradoAcademico().getNivel().byteValue()))){
																	 
																 	 /* log4j.debug("TIPO_VALOR_MULTI-> KO -->  candto_GradoAcademicoNivel < pos_grado_academ_min_nivel"+
																 				" ó candto_GradoAcademicoNivel > pos_grado_academ_max_nivel");*/
																 	  
																 log4j.debug("<getDemograficosYKO> KO: "+politicaMValor.getPoliticaValor().getPolitica().getClavePolitica());
																 	  
																 	  estatusRechazo=Constante.ESTATUS_CANDIDATO_RECHAZADO_ACADEMICA;
																 	  ko=true;
																	  break;
																	 
																 }
															 
															 //Se analiza el Estatus_Escolar
															 //candto_GradoAcademicoNivel == pos_grado_academ_min_nivel
															 //candto_GradoAcademicoNivel == pos_grado_academ_max_nivel
															 if(candidatoDto.getGradoAcademicoNivel().byteValue() ==
																politicaMValor.getGradoAcademico().getNivel().byteValue()){
																 		
																 		if(politicaMValor.getEstatusEscolar() != null){
																 			
																 			//se crea aviso?
																 			
																 			//FORM_ACADEMICA_KO_MIN
																	 		if(politicaMValor.getPoliticaValor().getPolitica().getClavePolitica().
																			   equals(Constante.POLITICA_VALOR_FORM_ACADEMICA_KO_MIN) &&
																	 		   (candidatoDto.getEstatusEscolarNivel().byteValue() >
																			   politicaMValor.getEstatusEscolar().getNivel())){
																	 			log4j.debug("<getDemograficosYKO> KO: FORM_ACADEMICA_MIN_EDO_ESCOLAR_KO");
																	 				candidatoDto.setIdPoliticaKo(Constante.POLITICA_ID_ESTATUS_ESCOLAR_F_A_MIN.longValue());																	 			
																					estatusRechazo=Constante.ESTATUS_CANDIDATO_RECHAZADO_ACADEMICA;
																		 			ko=true;
																		 			break;
																	 			
																	 		}
																	 		//FORM_ACADEMICA_MAX
																	 		if(politicaMValor.getPoliticaValor().getPolitica().getClavePolitica().
																			   equals(Constante.POLITICA_VALOR_FORM_ACADEMICA_KO_MAX) &&
																	 			(candidatoDto.getEstatusEscolarNivel().byteValue() <
																				 politicaMValor.getEstatusEscolar().getNivel())){
																	 			log4j.debug("<getDemograficosYKO> KO: FORM_ACADEMICA_MAX_EDO_ESCOLAR_KO");
																	 				candidatoDto.setIdPoliticaKo(Constante.POLITICA_ID_ESTATUS_ESCOLAR_F_A_MAX.longValue());
																					estatusRechazo=Constante.ESTATUS_CANDIDATO_RECHAZADO_ACADEMICA;
																		 			ko=true;
																		 			break;
																			 }
																 		}else{
																 			//se crea aviso?
																 		}
																	 }
															 	
															 //Si no hay ko
															 if(!ko && politicaMValor.getPoliticaValor().getPolitica().getClavePolitica().
																equals(Constante.POLITICA_VALOR_FORM_ACADEMICA_KO_MIN)){
																//se guarda en cache el grado_academico de la posicion
																 setElementoPerfilCache(Constante.NM_POLITICA_VALOR_POSICION,
															 				politicaValor.getPosicion().getIdPosicion(),
															 				Constante.FORM_ACADEMICA_KO_GA,
															 				String.valueOf(politicaMValor.getGradoAcademico().
															 								getNivel().byteValue()));
																 //se guarda en cache el estatus_escolar de la posicion
																 setElementoPerfilCache(Constante.NM_POLITICA_VALOR_POSICION,
															 				politicaValor.getPosicion().getIdPosicion(),
															 				Constante.FORM_ACADEMICA_KO_EE,
															 				String.valueOf(politicaMValor.getEstatusEscolar().
															 								getNivel()));
															 }
															 
														 }else{
															
															 // falta dar de alta aviso?
														 }
													}
												    
											  }//FIN de while() de politicaMValor
											  
											//Hay un ko
											if(ko){
													break;
												}
										 }										  
									  }
								  //Se obtienen valores unicos
								  }else if(politicaValor.getPolitica().getTipoValor().equals(Constante.TIPO_VALOR_UNICO) &&
										  UtilsTCE.isPositiveAndCero(politicaValor.getValor())){
									  if(politicaValor.getPolitica().getPropositoPolitica().
										 getIdPropositoPolitica() == Constante.PROPOSITO_KO ){
										  log4j.debug("<getDemograficosYKO> Politica-Posicion-ValorUnico-KO" +
										  		" DiasExperienciaLaboral="+candidatoDto.getDiasExperienciaLaboral()+
										  		" Cambio Domicilio="+candidatoDto.getCambioDomicilio());
										  
										  	//Politica KO para experiencia Laboral
										  	if(politicaValor.getPolitica().getClavePolitica().equals(Constante.POLITICA_VALOR_EXP_LABORAL_KO)){
												 if(candidatoDto.getDiasExperienciaLaboral() == null ||
													(candidatoDto.getDiasExperienciaLaboral().longValue() < 
													 Long.parseLong(politicaValor.getValor()))){
													 log4j.debug("<getDemograficosYKO> KO: DiasExperienciaLaboral ");
													 estatusRechazo=Constante.ESTATUS_CANDIDATO_RECHAZADO_LABORAL;
													 ko=true;
													 break;
												 } 
											}
										  	//Cambio Domicilio
											if(politicaValor.getPolitica().getClavePolitica().equals(Constante.POLITICA_VALOR_CAMBIO_DOM_KO)){	  
												 log4j.debug("&%&  cambio domicilio -> avisos:"+avisos.size());
												 if(candidatoDto.getCambioDomicilio() != null){
													 log4j.debug("&%&  cambio domicilio -> getCambioDomicilio:"+candidatoDto.getCambioDomicilio()+
															 " PoliValor"+politicaValor.getValor());
													 
													//si existe el aviso se marca como borrado
													aviso(avisos,Constante.AVISO_EMPTY_CAMB_DOM,true);
													
													//Solo se aplica en el caso que en la posición sea true
													if((Integer.parseInt(politicaValor.getValor()) == 1 &&
														(candidatoDto.getCambioDomicilio() ? 1:0) != 
														 Integer.parseInt(politicaValor.getValor()))){
														 log4j.debug("<getDemograficosYKO> KO: Cambio Domicilio");
														  //posicionLog.info("KO en Cambio Domicilio -- valor_Posicion="+
														// politicaValor.getValor()+
														//  " valor_Persona="+(candidatoDto.getCambioDomicilio() ? 1:0));
														  ko=true;
														  break;
													 }
												 }else{
													//se crea aviso
													aviso(avisos,Constante.AVISO_EMPTY_CAMB_DOM,false);						
												  }
											 }
										   
									  }
								  /* TIPO RANGO */
								  }else if(politicaValor.getPolitica().getTipoValor().equals(Constante.TIPO_VALOR_RANGO) ){									 
//									  log4j.debug("<getDemograficosYKO> Politica-Posicion-RANGO");
									  
									  /* CASO Edad */
									  if(politicaValor.getPolitica().getClavePolitica().equals(Constante.POLITICA_VALOR_RANGO_EDAD_KO)) {
										  log4j.debug("<getDemograficosYKO> Politica-Posicion-RANGO" +
												  " Edad="+candidatoDto.getEdad()+
												  " ClavePolitica="+politicaValor.getPolitica().getClavePolitica()+
												  " ValorMinRango1="+politicaValor.getValorMinRango1()+
												  " ValorMaxRango1="+politicaValor.getValorMaxRango1());
										  	//Se analiza el rango
								  			if(politicaValor.getValorMinRango1() != null && 
								  			   (Integer.parseInt(politicaValor.getValorMinRango1()) >  
								  			   	candidatoDto.getEdad().intValue())){
								  				log4j.debug("<getDemograficosYKO> KO: Edad min ");
								  				  //posicionLog.info("KO en Edad mínima -- valor_Posicion="+
															// politicaValor.getValorMinRango1()+
															//  " valor_Persona="+candidatoDto.getEdad());
							  					 ko=true;
												 break;
								  			} 
								  			
								  			//Se analiza el rango
								  			if(politicaValor.getValorMaxRango1() != null && 
								  			   (Integer.parseInt(politicaValor.getValorMaxRango1()) <  
								  				candidatoDto.getEdad().intValue())){
								  				log4j.debug("<getDemograficosYKO> KO: Edad max");
								  				  //posicionLog.info("KO en Edad máxima -- valor_Posicion="+
															// politicaValor.getValorMaxRango1()+
															//  " valor_Persona="+candidatoDto.getEdad());
							  					 ko=true;
												 break;
								  			} 
								  		
									  }
								}
							 }
	
						/* if(politicaValor.getPolitica().getClavePolitica().equals(Constante.POLITICA_VALOR_GRADO_ESTATUS_ACADEMICO)){
								log4j.debug(" && ponderacion grado academico --> getPoliticaEscolaridads="+politicaValor.getPoliticaEscolaridads());
								if(politicaValor.getPoliticaEscolaridads() != null){
									Iterator<PoliticaEscolaridad> itPoliticaEscolaridad=politicaValor.getPoliticaEscolaridads().iterator();
									while(itPoliticaEscolaridad.hasNext()){
										PoliticaEscolaridad politicaEscolaridad =itPoliticaEscolaridad.next();
										log4j.debug(" && Escolaridad ko --> PEGradoAcademico="+politicaEscolaridad.getGradoAcademico().getNivel()+
												" PEstatusEscolar="+politicaEscolaridad.getEstatusEscolar().getNivel() +  
												" PgradoAcademico="+persona.getGradoAcademico().getNivel()+
											   " PstatusEscolar="+persona.getEstatusEscolar().getNivel() );
									}
								}
								
							}else{
								 //Se guarda la ponderacion para calcular el demografico 
								 setElementoPerfilCache(Constante.NM_POLITICA_VALOR_POSICION,
					  				 politicaValor.getPosicion().getIdPosicion(),
					  				 politicaValor.getPolitica().getClavePolitica(),
					  				 politicaValor.getValor());
							}	*/
							
					  }else{
						  //diferente de posicion
					  }
				  }//fin Politica-Posicion
				  //Se anexa la politica escolar al nuevo map_perfil
				  if(politicaValor.getPerfil()!= null && 
					// politicaValor.getPoliticaEscolaridads().size() != 0 ){
					politicaValor.getPoliticaMValors() != null &&
					politicaValor.getPoliticaMValors().size() > 0 ){
					  
					  Iterator<PoliticaMValor> itPoliticaMValors= politicaValor.getPoliticaMValors().iterator();
					  while(itPoliticaMValors.hasNext()){
						  PoliticaMValor politicaMValor=itPoliticaMValors.next();
						 if(politicaMValor.getGradoAcademico() != null && 
							politicaMValor.getPoliticaValor().getPolitica().getClavePolitica().
							equals(Constante.POLITICA_VALOR_FORM_ACADEMICA_KO_MIN)){
							  log4j.debug("# se adiciona polica escolar al cache getIdPerfil="+perfilDto.getIdPerfil()+
									  		" politicaValor="+politicaValor);
							  if(!hmPoliticaValorEscolar.containsKey(perfilDto.getIdPerfil())){
								  lsPoliticaValorEscolar.add(politicaValor);
							  }
							  break;
						 }
					  }
					  
				  }
			  }
			  
			  //Hay un KO
			  if(ko) break;
			  else{
				  log4j.debug("# se adiciona  politicas_escolar -> getIdPerfil="+perfilDto.getIdPerfil()+
						  " lsPoliticaValorEscolar="+lsPoliticaValorEscolar.size());
				  //se adicionan las listas de politicas_escolar de cada perfil, al map correspondiente
				  if(lsPoliticaValorEscolar.size() > 0){
					  if(!hmPoliticaValorEscolar.containsKey(perfilDto.getIdPerfil().longValue()))
						  hmPoliticaValorEscolar.put(String.valueOf(perfilDto.getIdPerfil().longValue()),
						  lsPoliticaValorEscolar);
				  }
			  }
			  //Se guarda el perfilDto si es nuevo
			  lsPerfilDto.add(perfilDto);
		  } //FIN DE while(itKeyPoliticaValor.hasNext()){
		  log4j.debug("sale del ciclo while --> sumaConcepto="+sumaConcepto+
				  	" hmPoliticaValorEscolar="+hmPoliticaValorEscolar.size());
		  //se libera memoria
		  hmPoliticaValor=null;
		  
		  //Se especifica que es un cv modificado o nuevo
		  candidatoDto.setNuevoOModificado(true);
		  candidatoDto.setLsPerfilDto(null);
		  //candidatoDto.setModificado(candidatoDto.getIdCandidato() != null ? true:false);
		  	  
		  /* ************** SI EXISTIO UN RECHAZO (KO) SE ANULAN DATOS Y SE ESPECIFICA TIPO ****** */
		  if(ko){
			  //Rechazo por ko 
			  candidatoDto.setIapPuntos((double) -1);
			  candidatoDto.setIas(null);
			  candidatoDto.setIasDistancia(null);
			  candidatoDto.setIpg(null);
			  candidatoDto.setIdEstatusCandidato(estatusRechazo);
			  lsPerfilDto=null;
		  }else{
			  //estatus aceptado
			  candidatoDto.setIdEstatusCandidato(Constante.ESTATUS_CANDIDATO_ACEPTADO );
			  //se guardan los perfiles en el candidato
			  candidatoDto.setLsPerfilDto(lsPerfilDto);
			  //Se calcula el demografico
			  candidatoDto.setDemografico(sumaConcepto);
			  //se aplica el IdEstatusOperativo
			  candidatoDto.setIdEstatusOperativo(candidatoDto.getIdCandidato() != null ? 
					  							candidatoDto.getIdEstatusOperativo():
					  							Constante.ESTATUS_CANDIDATO_OPERATIVO_INDEXADO);
			  candidatoDto.setIdPoliticaKo(null);
			  
			   //si hay avisos
		  	  if(avisos.size() > 0){
		  		log4j.debug("#&% avisos="+avisos.size()); 
		  		candidatoDto.setAvisos(avisos);  		 
		  	  }
		  }
		  candidatoDto.setIpgEnCola(false);
		  candidatoDto.setIasEnCola(false);		  
		  
		  log4j.debug("<getDemograficosYKO> KO="+ko+" estatus="+ candidatoDto.getIdEstatusCandidato()+
				  " IdPersona="+candidatoDto.getIdPersona());
	}
	
	
	
	
	/**
	 * Se analiza el token de habilidad_posicion con respecto a los tokens de habilidad persona
	 * @param tokensHabilidadPer, tokens de cada habilidad persona
	 * @param tokenHabilidadPos, token de la habilidad persona
	 * @return true si es similar
	 * 		   false no hubo similar
	 */
	/*private boolean analizaSimilar(String[] tokensHabilidadPer, String tokenHabilidadPos ){		
		for (int i = 0; i < tokensHabilidadPer.length; i++) {							    			  			
  			//Es similar
  			if(UtilsTCE.similarity(tokenHabilidadPos,tokensHabilidadPer[i].trim()) > 0.0f){
  				return true;
  			}
		}
		return false;
	}*/
	
	/**
	 * si no existe se crea la lista de habiliddaes posicion
	 * @param politicaMHabilidads
	 */
	/*private  void setLsHabilidadesPosicion(Set<PoliticaMHabilidad> politicaMHabilidads,long idPosicion, 
											 boolean huboCambioPosicion){
		 log4j.debug("** setLsHabilidadesPosicion ini --> idPosicion="+idPosicion+ " huboCambioPosicion="+huboCambioPosicion+
				 	" unaVezListaHabilidadPos="+unaVezListaHabilidadPos);
		
		//si la lista no se encuentra en cache o hubo cambio en la posicion
  	  if((huboCambioPosicion || !HabilidadesPosicionCache.containsKey(idPosicion)) &&
  		  unaVezListaHabilidadPos){
	    	  
	    	  //Se crea una lista de habilidades posicion para obtener la ponderacion de cada habiliadad_persona
	    	  Iterator<PoliticaMHabilidad> itPoliHabil=politicaMHabilidads.iterator();
	    	  List<PerfilNgramDto> lsHabilidPosicion=new ArrayList<PerfilNgramDto>();
			  while(itPoliHabil.hasNext()){
					  PoliticaMHabilidad politicaMHabilidad=itPoliHabil.next();
					  log4j.debug("&##& Habilidades-->  descripcion_pos="+politicaMHabilidad.getHabilidadpos().getDescripcion()+
							      " ponderacion_pos="+politicaMHabilidad.getDominio().getPonderacion()+
							      " getIdHabilidadpos="+politicaMHabilidad.getHabilidadpos().getIdHabilidadpos());
					  
					   //Se buscan los similares de la habilides
					  lsHabilidPosicionSimilar=new ArrayList<PerfilNgramDto>();
					  habilidadSimilar(politicaMHabilidad.getHabilidadpos().getIdHabilidadpos(),
							  		   politicaMHabilidad.getDominio().getPonderacion().floatValue());
					  
					  log4j.debug("&##& Habilidades se crea --> lsHabilidPosicionSimilar="+lsHabilidPosicionSimilar.size());
					  lsHabilidPosicion.add(new PerfilNgramDto(UtilsTCE.filterGramaText(politicaMHabilidad.getHabilidadpos().getDescripcion()).trim(),
							  								   politicaMHabilidad.getDominio().getPonderacion(),
							  								   lsHabilidPosicionSimilar));
					 				 
				  }
			  log4j.debug("&##& Habilidades se crea --> lsHabilidPosicion="+lsHabilidPosicion.size());
			  //Si ya existe
			  if(HabilidadesPosicionCache.containsKey(idPosicion)){
				  HabilidadesPosicionCache.replace(idPosicion, lsHabilidPosicion);
			  }else{
				  HabilidadesPosicionCache.put(idPosicion, lsHabilidPosicion);
			  }
			  unaVezListaHabilidadPos=false;	  
  	  } 
	}*/
	
	
	/**
	 * Se adiciona a la lista de habilidadesPos la habilidad similar a la correspondiente
	 * @param idHabilidad, id de la habilidad 
	 * @param ponderacion
	 */
	/*private void habilidadSimilar(long idHabilidadPos,float ponderacion){
		
		List<PerfilNgramDto> lsHabilidadPosSimi= habilidadDao.getTextSimiPos(idHabilidadPos);
		if(lsHabilidadPosSimi != null){
			 //Se crea una lista de habilidades posicion para obtener la ponderacion de cada habiliada_persona
	    	  Iterator<PerfilNgramDto> itHabilidadPosSimi=lsHabilidadPosSimi.iterator();
			  while(itHabilidadPosSimi.hasNext()){
				  PerfilNgramDto habilidadPosSimi=itHabilidadPosSimi.next();
				  habilidadPosSimi.setDescripcion(UtilsTCE.filterGramaText(habilidadPosSimi.getDescripcion()).trim());
				  habilidadPosSimi.setPonderacion(ponderacion);
					  log4j.debug("&##& habilidadSimilar-->  descripcion_pos="+habilidadPosSimi.getDescripcion()+
							      " ponderacion_pos="+ponderacion+
							      " idHabilidadpos="+habilidadPosSimi.getIdHabilidad());
					  //Se guarda en la lista
					  lsHabilidPosicionSimilar.add(habilidadPosSimi);
					  
					  //Se busca habilidades similares
					  habilidadSimilar(habilidadPosSimi.getIdHabilidad(),ponderacion);
				  }
		}
	}*/
	
	
	/**
	 * Se suma un aviso a la lista. 
	 * Estos se muestran a la persona que creo la posicion
	 * @param avisos, lista de objetos Aviso
	 * @param cad, clave del aviso
	 * @param borrar, false no se borra
	 * 				  true  se borra
	 */
	private void aviso(List<AvisoDto> avisos,String cad, boolean borrar){
		boolean existe=false;
		Iterator<AvisoDto> itAvisos =avisos.iterator();
		AvisoDto avisoDto;
		while(itAvisos.hasNext()){
			avisoDto=itAvisos.next();
			
			//Se analiza si existe el aviso con la clave correspondiente
			if(avisoDto.getClaveAviso().equals(cad)){
				existe=true;
				avisoDto.setBorrado(borrar);
				break;
			}
		}
		//si no existe la clave se adiciona a la lista de avisos
		if(!existe && !borrar){
			if(AvisoCache.get(cad) != null){
				avisos.add(AvisoCache.get(cad));
			}else{
				log4j.error("No hay objeto: AvisoDto, en cache, referido a:"+cad);
			}
		}
	}
	
	/**
	 * Se guardan las politicas valor de ponderacion en cache
	 * @param nmPoliticaValor, es el nemonico para especificar si es perfil o posicion o empresa
	 * @param idModo, es el id de  perfil o posicion o empresa
	 * @param clavePolitica
	 * @param valor, de la politica
	 */
	private void setElementoPerfilCache(String nmPoliticaValor, long idModo,String clavePolitica,String valor ){
		  //Se guardan en un map. La llave= clave del modo + "_"+ idModo(idPerfil o idPosicion o idEmpresa) + _ + clavePolitica
		  sbKey.append(nmPoliticaValor).append("_"). append(idModo).append("_").append(clavePolitica);
		  log4j.debug(" %&& setElementoPerfilCache -> sbKey="+sbKey.toString()+" valor="+valor);
		  //Se revisa si esta en el cache el elemento
		  if(!PoliticaValorElementoCache.containsKey(sbKey.toString())){
				//Se adiciona al cache
				PoliticaValorElementoCache.put(sbKey.toString(),Float.parseFloat(valor));
			}
		  //se inicializa
		  sbKey.delete(0, sbKey.toString().length());
	}
	
	/**
	 * Se calcula el indice IAP para las empresas
	 * @param candidatoDto
	 * @param hmPerfilNgranDto
	 */
/*	private void getEnterpriseIAP(CandidatoDto candidatoDto,  HashMap<String,List<PerfilNgramDto>> hmPerfilNgranDto){
		texto=candidatoDto.getTexto();
		log4j.debug("%&/ texto_empresa="+texto);
		Iterator<Map.Entry<String,List<PerfilNgramDto>>>  itPerfilNgramDto=hmPerfilNgranDto.entrySet().iterator();
		//Se analiza por perfil
		while(itPerfilNgramDto.hasNext()){
			Map.Entry<String,List<PerfilNgramDto>> eMap =(Map.Entry<String,List<PerfilNgramDto>>)
														  itPerfilNgramDto.next();
			//Se obtiene la puntuacion con el analisis de Ngrams
			double puntosNgram = getResAnalisisNgrams(eMap.getValue());
			
			log4j.debug("%&/ idPerfil="+eMap.getKey()+" puntosNgram="+puntosNgram+
					" getLsPerfilDto="+candidatoDto.getLsPerfilDto());
			//Se actualiza la propiedad empresa Bruto del candidato, para el perfil correspondiente
			actualizaPerfilPorCandidato(candidatoDto,eMap.getKey(), puntosNgram,3);
		}
	}*/
	
	/**
	 * Analiza las politicas de experiencia laboral
	 * @param candidatoDto a analizar
	 * @param hmPerfilNgranDto, map que contiene los ngram de los perfiles correspondientes
	 * @throws Exception 
	 */
	private void getExperienciaLaboralIAP(CandidatoDto candidatoDto,  
			HashMap<String,List<PerfilNgramDto>> hmPerfilNgranDto) throws Exception{
		//Se obtiene una lista de objetos ExperienciaLaboral mediante criteria
		currFilters  = new HashMap<String, Object>();
		orderby=new ArrayList<String>();
		currFilters.put("persona.idPersona",candidatoDto.getIdPersona());
		orderby.add(0,"idExperienciaLaboral");
		currFilters.put(Constante.SQL_ORDERBY,orderby );
		//se obtiene una lista
		List<ExperienciaLaboral> lsExperienciaLaboral=experienciaLaboralDao.getByFilters(currFilters);
		log4j.debug("%% lsExperienciaLaboral="+lsExperienciaLaboral.size());
		//Se recorren todas las experiencias laborales
		if(lsExperienciaLaboral != null && lsExperienciaLaboral.size() > 0){
			double puntuacion=0, puntosNgram =0;
			Iterator<ExperienciaLaboral> itExperienciaLaboral=lsExperienciaLaboral.iterator();
			while(itExperienciaLaboral.hasNext()){
				Date dateFin=null;
				ExperienciaLaboral experienciaLaboral=itExperienciaLaboral.next();
				log4j.debug("%% getIdPersona="+candidatoDto.getIdPersona()+
						  " getIdExperienciaLaboral="+experienciaLaboral.getIdExperienciaLaboral()+
						  " getTrabajoActual="+experienciaLaboral.getTrabajoActual());
				//Se obtienen las fechas inicial y final
				Date dateIni= DateUtily.setDateDirect(experienciaLaboral.getAnioInicio() ,
							  experienciaLaboral.getMesByMesInicio().getIdMes(),
							  experienciaLaboral.getDiaInicio() == null ? 
							  Constante.SI_DIA_NULO:experienciaLaboral.getDiaInicio(),
							  0,0,0);
				//se verifica si tiene trabajo actual
				if(experienciaLaboral.getTrabajoActual()){
					 dateFin=DateUtily.getTodayZero();
				}else{
					 dateFin= DateUtily.setDateDirect(experienciaLaboral.getAnioFin() ,
													  experienciaLaboral.getMesByMesFin().getIdMes(),
													  experienciaLaboral.getDiaFin() == null ? 
													  Constante.SI_DIA_NULO:experienciaLaboral.getDiaFin(),
													  0,0,0);
				} 
				log4j.debug("%% dateIni="+dateIni+" dateFin="+dateFin);
				int diasTotales=(int) DateUtily.daysBetween(dateIni, dateFin);
				
				//Se obtienen los textos_funciones
				if(experienciaLaboral.getTexto() != null ){
					texto=experienciaLaboral.getTextoFiltrado();
					log4j.debug("% experienciaTexto="+texto);
					Date fechaCaducidad;
					double caducos,caducosCastigo,efectivos,obsoletos,ponderacion;
					String raiz,keyCaducidad,keyDuracionValida,keyCastigo;
					Iterator<Map.Entry<String,List<PerfilNgramDto>>>  itPerfilNgramDto=hmPerfilNgranDto.entrySet().iterator();

					//Se analiza por perfil
					while(itPerfilNgramDto.hasNext()){
						Map.Entry<String,List<PerfilNgramDto>> eMap =(Map.Entry<String,List<PerfilNgramDto>>)
																	  itPerfilNgramDto.next();
						log4j.debug("-------------------------------------- empieza perfil: "+eMap.getKey());
						
						//Se crea las llaves para obtener los elementos del cache
						//PF + "_"+ idModo(idPerfil o idPosicion o idEmpresa) + _ + clavePolitica
						raiz=new StringBuilder(Constante.NM_POLITICA_VALOR_PERFIL).append("_").
									append(eMap.getKey()).append("_").toString();
						keyCaducidad=new StringBuilder(raiz).append(Constante.NM_CADUCIDAD).toString();
						keyDuracionValida=new StringBuilder(raiz).append(Constante.NM_DURACION_VALIDA).toString();
						keyCastigo=new StringBuilder(raiz).append(Constante.NM_CASTIGO).toString();

						//se obtinen los dias totales
						fechaCaducidad=new Date(DateUtily.getTodayZero().getTime() - 
											(((PoliticaValorElementoCache.containsKey(keyCaducidad)  ? 
											  PoliticaValorElementoCache.get(keyCaducidad).longValue():
											  Constante.CADUCIDAD)) 
											* Constante.MULT_MILISEG_A_DIAS));
						//se obtinen los dias caducos
						caducos=((float)(Math.abs(DateUtily.daysBetween(dateIni,fechaCaducidad)) + 
									   (diasTotales - Math.abs(DateUtily.daysBetween(fechaCaducidad,dateFin))))/
									   (2 * (PoliticaValorElementoCache.containsKey(keyDuracionValida)  ? 
											 PoliticaValorElementoCache.get(keyDuracionValida).longValue():
											 Constante.DURACION_VALIDA)));
						caducosCastigo=caducos * (1 - (PoliticaValorElementoCache.containsKey(keyCastigo)  ? 
								  						 PoliticaValorElementoCache.get(keyCastigo).doubleValue():
								  						 Constante.CASTIGO));
						//se obtienen los dias efectivos
						efectivos=((float)(Math.abs(DateUtily.daysBetween(fechaCaducidad,dateFin)) + 
								  (diasTotales - Math.abs(DateUtily.daysBetween(dateIni,fechaCaducidad))))/
								  (2 * (PoliticaValorElementoCache.containsKey(keyDuracionValida)  ? 
										PoliticaValorElementoCache.get(keyDuracionValida).longValue():
										Constante.DURACION_VALIDA)));
						obsoletos=((float)(DateUtily.daysBetween(fechaCaducidad,dateFin) - 
						 		  Math.abs(DateUtily.daysBetween(fechaCaducidad,dateFin)))/
						 		  (2 * (PoliticaValorElementoCache.containsKey(keyDuracionValida)  ? 
										PoliticaValorElementoCache.get(keyDuracionValida).longValue():
						 				Constante.DURACION_VALIDA)))
						 		  * (PoliticaValorElementoCache.containsKey(keyCastigo)  ? 
									  PoliticaValorElementoCache.get(keyCastigo).floatValue():
						 			  Constante.CASTIGO);
						ponderacion=efectivos + caducosCastigo + obsoletos;
						log4j.debug(" idPerfil="+eMap.getKey()+
								" FechaInicial="+DateUtily.date2String(dateIni,"dd/MM/yyyy")+
								" FechaFin="+DateUtily.date2String(dateFin,"dd/MM/yyyy")+
								" fechaCaducidad="+DateUtily.date2String(fechaCaducidad,"dd/MM/yyyy"));
						log4j.debug("%% diasTotales="+diasTotales+
						 			" caducos="+caducos+
						 			" efectivos="+efectivos+
						 			" caducosCastigo="+caducosCastigo+
						 			" obsoletos="+obsoletos+
						 			" ponderacion="+ponderacion);
						//Solo tiene caso seguir si
						if(ponderacion != 0){
							//Se obtiene la puntuacion con el analisis de Ngrams
							puntosNgram = getResAnalisisNgrams(eMap.getValue());
							log4j.debug("#### PuntosNgram="+puntosNgram);
							puntuacion=UtilsTCE.redondear((Math.abs(ponderacion * 
											experienciaLaboral.getTipoJornada().getPonderacion()
											* puntosNgram) + (ponderacion * 
											experienciaLaboral.getTipoJornada().getPonderacion()
											* puntosNgram))/2,1,RoundingMode.CEILING);													
						}
						log4j.debug(" PUNTUACION_final="+puntuacion);
						log4j.debug("-------------------------------- termina perfil: "+eMap.getKey());
						//Se actualiza la propiedad ExperienciaLaboralBruto del candidato, para el perfil correspondiente
						actualizaPerfilPorCandidato(candidatoDto,eMap.getKey(), puntuacion, 1);
					}
				 }
		   }	
		}
	}
	
	/**
	 * Analiza las politicas de escolaridad para cada candidato
	 * @param candidatoDto a analizar
	 * @param hmPerfilNgranDto, map que contiene los ngram de los perfiles correspondientes
	 * @param hmPoliticaValor, map que contiene las politicas referente a escolaridad
	 * @throws Exception 
	 */
	private void getEscolaridadIAP(CandidatoDto candidatoDto, 
					HashMap<String,List<PerfilNgramDto>> hmPerfilNgranDto) throws Exception{
		byte gradoEscolarAsignadoNivel;
		byte estatusEscolarAsignadoNivel;				
		//Se obtiene una lista de objetos ExperienciaLaboral mediante criteria
		currFilters  = new HashMap<String, Object>();
		orderby=new ArrayList<String>();
		currFilters.put("persona.idPersona",candidatoDto.getIdPersona());
		orderby.add(0,"idEscolaridad");
		currFilters.put(Constante.SQL_ORDERBY,orderby );
		//se obtiene una lista
		List<Escolaridad> lsEscolaridad=escolaridadDao.getByFilters(currFilters);
		log4j.debug("getEscolaridadIAP() -> getIdPersona="+candidatoDto.getIdPersona()+" lsEscolaridad="+lsEscolaridad.size());
		//Se recorren todas las escolaridades
		if(lsEscolaridad != null && lsEscolaridad.size() > 0){
			Iterator<Escolaridad> itEscolaridad= lsEscolaridad.iterator();
			while(itEscolaridad.hasNext()){
				Escolaridad escolaridad=itEscolaridad.next();
				log4j.debug("-----------------------------------------");
				log4j.debug("getEscolaridadIAP() -> getIdPersona="+candidatoDto.getIdPersona()+"  idEscolaridad="+escolaridad.getIdEscolaridad());
				//Se efectua el analisis de Ngrams para obtener los puntos
				  //SE COMENTO POR EL CAMBIO DE BD
				if(escolaridad.getTexto() != null ){
					texto=escolaridad.getTextoFiltrado();
					log4j.debug("% texto="+texto);
					log4j.debug("----------- PERFILES  -----------");
					gradoEscolarAsignadoNivel=gradoEscolarNivel;
					estatusEscolarAsignadoNivel=estatusEscolarNivel;
					Iterator<Map.Entry<String,List<PerfilNgramDto>>> itPerfilNgramDto=hmPerfilNgranDto.entrySet().iterator();
					//Se recorren las funciones por perfil
					while(itPerfilNgramDto.hasNext()){
						Map.Entry<String,List<PerfilNgramDto>> eMap =(Map.Entry<String,List<PerfilNgramDto>>)itPerfilNgramDto.next();
						log4j.debug("getEscolaridadIAP() -> idPerfil="+eMap.getKey()+" hmPoliticaValorEscolar="+hmPoliticaValorEscolar.size()+
								" gradoEscolarNivel="+gradoEscolarNivel+" estatusEscolarNivel="+estatusEscolarNivel);
						log4j.debug("getEscolaridadIAP() -> getGradoAcademico_nivel:"+escolaridad.getGradoAcademico().getNivel());
						//Se obtiene la puntuacion con el analisis de Ngrams
						double puntosNgram =getResAnalisisNgrams(eMap.getValue());
						boolean hayCoincidencia=false;						
						
						//solo se aplica cuando la escolaridad no es la maxima
						//y hay politica_valor para perfil
						/*if(escolaridad.getGradoAcademico().getNivel() == null ||
						   escolaridad.getGradoAcademico().getNivel().byteValue() != gradoEscolarNivel ){
							puntosNgram=puntosNgram == 0 ? 1: puntosNgram;
							//Si hay politicas de escolaridad
							if(hmPoliticaValorEscolar.size() > 0){
								//Se obtienen las politicas de la posicion
								Iterator<Map.Entry<String,List<PoliticaValor>>>  itKeyPoliticaValor=hmPoliticaValorEscolar.entrySet().iterator();
								while(itKeyPoliticaValor.hasNext()){
									  Map.Entry<String,List<PoliticaValor>> mePoliticaValor =(Map.Entry<String,List<PoliticaValor>>)itKeyPoliticaValor.next();
									  log4j.debug("getEscolaridadIAP() -> hmPoliticaValor --> key="+mePoliticaValor.getKey()+ 
											  	 " lsPoliticaValor="+((List<PoliticaValor>)mePoliticaValor.getValue()).size());
									  //Se obtienen las politicas correspondientes al perfil
									  if(mePoliticaValor.getKey().equals(eMap.getKey())){
										  Iterator<PoliticaValor> itPoliticaValor= ((List<PoliticaValor>)mePoliticaValor.getValue()).iterator();
										  while(itPoliticaValor.hasNext()){
											  PoliticaValor politicaValor=itPoliticaValor.next();
											  //Nos aseguramos que esta politica sea de escolaridad
											  if(politicaValor.getPerfil() != null &&
												 politicaValor.getPoliticaMValors() != null &&
												 politicaValor.getPoliticaMValors().size() == 0){
												   log4j.debug("getEscolaridadIAP() -> hmPoliticaValor --> getPoliticaMValors="+
														  		politicaValor.getPoliticaMValors().size());
												 Iterator<PoliticaMValor> itPoliticaMValors= politicaValor.getPoliticaMValors().iterator();
												  while(itPoliticaMValors.hasNext()){
													  PoliticaMValor politicaMValor=itPoliticaMValors.next();
													  if(politicaMValor.getGradoAcademico() != null){
														  log4j.debug("getEscolaridadIAP() -> IdGradoAcademico(politica)="+politicaMValor.getGradoAcademico().getIdGradoAcademico()+
																	 " IdGradoAcademico(escolaridad)="+ escolaridad.getGradoAcademico().getIdGradoAcademico());
														  if(politicaMValor.getGradoAcademico().getIdGradoAcademico() ==
															escolaridad.getGradoAcademico().getIdGradoAcademico()){
															  gradoEscolarAsignadoNivel=politicaMValor.getGradoAcademico().getNivel() != null ?
																	  politicaMValor.getGradoAcademico().getNivel().byteValue():0;
																 estatusEscolarAsignadoNivel=politicaMValor.getEstatusEscolar().getNivel();
																 hayCoincidencia=true;
																 break;
														  }
														 
													 }
												 }
												
											  }
											  //si hay coincidencia no tiene caso seguir revisando los demas perfiles
											  if(hayCoincidencia) break;
										  }
										  //si hay coincidencia no tiene caso seguir revisando los demas perfiles
										 if(hayCoincidencia) break; 
									  }
								  }
							}
						}else{*/
							//Cuando la escolaridad es la maxima
							hayCoincidencia=true;
						//}
						 log4j.debug("getEscolaridadIAP() -> hayCoincidencia="+hayCoincidencia+
								 	" puntosNgram="+puntosNgram+
								 	" gradoEscolarAsignadoNivel="+gradoEscolarAsignadoNivel+
								 	" estatusEscolarAsignadoNivel="+estatusEscolarAsignadoNivel);
						
						//Se efectuan los algoritmos de ponderacion
						if(hayCoincidencia && puntosNgram != 0){
							//Se actualiza la propiedad escolaridad del candidato para el perfil correspondiente
							actualizaPerfilPorCandidato(candidatoDto, eMap.getKey(), 
											puntosEscolaridad(escolaridad,puntosNgram,
											gradoEscolarAsignadoNivel,estatusEscolarAsignadoNivel),2);
						}
						//log4j.debug("& hayCoincidencia="+hayCoincidencia+"  puntosNgram="+puntosNgram);
						log4j.debug(" ----------------------------------------------  ");
					}
				}
			}
		}
	}
	
	/**
	 * Obtiene los puntos de la formación academica
	 * @param escolaridadObj, tiene la informaci�n necesaria de la escolaridad
	 * @param puntosNgram, el resultado del analisis de los ngram
	 * @param gradoEscolarAsiganado, el grado  correspondiente a la escolaridad
	 * @param estatusEscolarAsiganado, el estatus escolar correspondiente a la escolaridad
	 * @return el resultado del analisis
	 */
	private float puntosEscolaridad(Escolaridad escolaridadObj,Double puntosNgram,
									byte gradoEscolarAsiganado, byte estatusEscolarAsiganado){
		/**
		 * Ponderacion de caducidad
		 */
		//Se obtienen los valores de ponderacion dependiendo del grado academico
		String keyDegradacion=new StringBuilder(Constante.NM_GRADO_ACADEMICO).append("_").
							  append(String.valueOf(escolaridadObj.getGradoAcademico().getIdGradoAcademico()))
							  .append("_").append(Constante.GA_DEGRADACION).toString();
		String keyDuracion=new StringBuilder(Constante.NM_GRADO_ACADEMICO).append("_").
							append(String.valueOf(escolaridadObj.getGradoAcademico().getIdGradoAcademico()))
						   .append("_").append(Constante.GA_DURACION).toString();
		String keyNivel=new StringBuilder(Constante.NM_GRADO_ACADEMICO).append("_").
						append(String.valueOf(escolaridadObj.getGradoAcademico().getIdGradoAcademico()))
					   .append("_").append(Constante.GA_NIVEL).toString();
		String keyDominio=new StringBuilder(Constante.NM_GRADO_ACADEMICO).append("_").
						append(String.valueOf(escolaridadObj.getGradoAcademico().getIdGradoAcademico()))
					   .append("_").append(Constante.GA_DOMINIO).toString();
		long degradacion=(long)(PoliticaValorElementoCache.containsKey(keyDegradacion) ?
						PoliticaValorElementoCache.get(keyDegradacion):0);	
		float duracionPon=(float)(PoliticaValorElementoCache.containsKey(keyDuracion) ?
				  	 PoliticaValorElementoCache.get(keyDuracion):1);
		int nivel=(int)(PoliticaValorElementoCache.containsKey(keyNivel) ?
					PoliticaValorElementoCache.get(keyNivel):0);	
		int dominio=(int)(PoliticaValorElementoCache.containsKey(keyDominio) ?
					 PoliticaValorElementoCache.get(keyDominio):0);	
		//Se obtienen las fechas inicial y final
		Date dateIni= DateUtily.setDateDirect(escolaridadObj.getAnioInicio() ,escolaridadObj.getMesByMesInicio().getIdMes(),
						escolaridadObj.getDiaInicio() == null ? Constante.SI_DIA_NULO:escolaridadObj.getDiaInicio(),0,0,0);
		
		Date dateFin=DateUtily.getToday();
		
		//Si no es estudiante
		if(escolaridadObj.getEstatusEscolar().getIdEstatusEscolar() != 
		   Long.parseLong(Constante.ESTATUS_ESCOLAR_ESTUDIANTE)){						
			 	dateFin= DateUtily.setDateDirect(escolaridadObj.getAnioFin() ,
				 						  escolaridadObj.getMesByMesFin().getIdMes(),
				 						  escolaridadObj.getDiaFin() == null ? 
		 								  Constante.SI_DIA_NULO:
				 						  escolaridadObj.getDiaFin(),0,0,0);
		 }
		//se obtinen los dias totales
		Date fechaCaducidad=new Date(DateUtily.getTodayZero().getTime() - (degradacion * Constante.MULT_MILISEG_A_DIAS));
		int caducos=(int) DateUtily.daysBetween(fechaCaducidad, dateFin);
		float diasInvertidos=(float) DateUtily.daysBetween(dateIni, dateFin);
		float proporcion=diasInvertidos/duracionPon;
		float totalCaducidad=caducos < 0 ? (1-castigoXcaducidad):1;
		log4j.debug("puntosEscolaridad() -> dateIni="+new SimpleDateFormat("dd/MM/yyyy").format(dateIni)+
				   " dateFin="+new SimpleDateFormat("dd/MM/yyyy").format(dateFin)+
				   " fechaCaducidad="+new SimpleDateFormat("dd/MM/yyyy").format(fechaCaducidad));
		log4j.debug("puntosEscolaridad() ->  puntosEscolaridad() ->  degradacion="+degradacion+" duracion="+duracionPon+
				   " diasInvertidos="+diasInvertidos+" proporcion="+proporcion+
				   " caducos="+caducos+" castigoXcaducidad="+castigoXcaducidad+
				   " totalCaducidad="+totalCaducidad+" nivel="+nivel+
				   " dominio="+dominio);
		/**
		 * Ponderador de grado
		 */
		float estatus=1 - ((((float)Math.abs(escolaridadObj.getEstatusEscolar().getNivel() - estatusEscolarAsiganado) - 
					  (estatusEscolarAsiganado - escolaridadObj.getEstatusEscolar().getNivel()))/2) * estatusIncompletos);
		float duracion=escolaridadObj.getEstatusEscolar().getNivel() == 1 ? 1:
					   (proporcion > 1 ? 1/proporcion:proporcion );
		float escolaridad=(1+dominio*(((float)Math.abs(nivel - gradoEscolarAsiganado)-(gradoEscolarAsiganado - nivel ))/2))+
						  (escolaridadSuperior * dominio);
		float totalGrado=estatus * duracion * escolaridad;
		float ponderacion=totalGrado * totalCaducidad;
		float puntosTotales=(float) (puntosNgram * ponderacion);
		log4j.debug("puntosEscolaridad() ->  gradoEscolarAsiganado="+gradoEscolarAsiganado+" estatusEscolarAsiganado="+estatusEscolarAsiganado+
				   " escolaridadSuperior="+escolaridadSuperior+" estatusIncompletos="+estatusIncompletos+
				   " getNivel(estatus)="+escolaridadObj.getEstatusEscolar().getNivel());
		log4j.debug("puntosEscolaridad() ->  estatus="+estatus+" duracion="+duracion+" escolaridad="+escolaridad+
				   " total="+totalGrado+" ponderacion="+ponderacion+" PUNTOS_TOTALES="+puntosTotales); 
		return puntosTotales;
	}
	
	/**
	 * Actualiza el valor bruto del IAP en las propiedades:experienciaLaboral, escolaridad y empresa, 
	 * de perfil correspondiente al candidato
	 * @param candidatoDto, objeto del candidato correspondiente
	 * @param idPerfil, es el id del perfil correspondiente
	 * @param puntuacion, cantidad de puntos a sumar
	 * @param modo, 1 si es experiencia
	 * 			    2 si es escolaridad
	 * 				3 si es empresa
	 */
	private void actualizaPerfilPorCandidato(CandidatoDto candidatoDto, String idPerfil, double puntuacion, int modo){	
		if(candidatoDto.getLsPerfilDto() != null){
			Iterator<PerfilDto> itPerfilDto=candidatoDto.getLsPerfilDto().iterator();
			while(itPerfilDto.hasNext()){
				PerfilDto perfilDto=itPerfilDto.next();
				if(perfilDto.getIdPerfil().longValue() == Long.parseLong(idPerfil)){
					//experiencia laboral
					if(modo == 1){
						perfilDto.setExperienciaLaboralBruto(perfilDto.getExperienciaLaboralBruto() + puntuacion);
					//escolaridad
					}else if(modo == 2){
						perfilDto.setEscolaridadBruto(perfilDto.getEscolaridadBruto() + puntuacion );
					//empresa
					}else if(modo == 3){
						perfilDto.setEmpresaBruto(perfilDto.getEmpresaBruto() + puntuacion);
					}
					break;
				}
			}
		}
	}
	
	/**
	 * Se obtiene la puntuacion de los Ngrams con respecto a las coincidencias 
	 * de las experiencias de trabajo o escolaridad de candidato
	 * @param lsFuncionPerfil es la lista de los ngram del perfil correspondiente
	 * @return el resultado del analisis de ngram
	 * @throws JepException 
	 */
	private double getResAnalisisNgrams(List<PerfilNgramDto> lsFuncionPerfil){
		log4j.debug("%% getResAnalisisNgrams() --> lsFuncionPerfil="+lsFuncionPerfil.size());
		//Directo
		totalA=sumaPonderacionDirec(lsFuncionPerfil, false);
		totalB=sumaPonderacionIndirec(lsFuncionPerfil, false);
		log4j.debug("%% TOTAL_DIRECTO -> totalA="+totalA+" totalB="+totalB);
		
		//Con token
		lsFuncionParcial=filtroTokens(lsFuncionPerfil,Constante.EXP_REG_ANALISIS_NGRAM_TOKEN);
		log4j.debug("%% CON_TOKENS -> lsFuncionParcial="+lsFuncionParcial);
		totalC=sumaPonderacionDirec(lsFuncionParcial, true);
		totalD=sumaPonderacionIndirec(lsFuncionParcial, true);
		log4j.debug("%% TOTAL_CON_TOKENS -> totalC="+totalC+" totalD="+totalD);
				
		//Con espacios
		lsFuncionParcial=filtroTokens(lsFuncionParcial,Constante.EXP_REG_ANALISIS_NGRAM_ESPACIOS);
		log4j.debug("%% CON_ESPACIOS -> lsFuncionParcial="+lsFuncionParcial);
		totalE=sumaPonderacionDirec(lsFuncionParcial, true);
		totalF=sumaPonderacionIndirecSimilar(lsFuncionParcial, true,Constante.EXP_REG_ANALISIS_NGRAM_ESPACIOS);
		log4j.debug("%% TOTAL_CON_ESPACIOS -> totalE="+totalE+" totalF="+totalF);
		
		//Se utiliza (1)*A+(0.5)*B+(0.8)*C+(0.4)*D+(0.3)E+(0.1)F
		return (totalA + (0.5 * totalB) + (0.8 * totalC) + (0.4 * totalD) + (0.3 * totalE) + (0.1 * totalF));
	}
	
	/**
	 *  Se suma las ponderaciones de las coincidencias directas de los ngrams del perfil con el texto del candidato
	 * @param lsFuncionPerfil, lista de funciones de la posicion
	 * @param eliminaFuncion, 	true si se elimina la funcion 
	 * 							false lo contrario
	 * @return
	 */
	private float  sumaPonderacionDirec(List<PerfilNgramDto> lsFuncionPerfil, boolean eliminaFuncion){
		//Match directo
		float contDirecto=0;
		if(!texto.equals("") && lsFuncionPerfil.size() > 0){
			Iterator<PerfilNgramDto> itFuncionPerfil= lsFuncionPerfil.iterator();
			while(itFuncionPerfil.hasNext()){
				PerfilNgramDto funcionDto=itFuncionPerfil.next();
				//directo
				if(texto.contains(funcionDto.getDescripcion())){
					log4j.debug("sumaPonderacionDirec -> Ponderacion:"+funcionDto.getPonderacion()+
							"\tMatch(directo):"+funcionDto.getDescripcion());
					texto=texto.replaceAll(funcionDto.getDescripcion(), " ");
					contDirecto+=funcionDto.getPonderacion();	
					//Se elimina funcion
					if(eliminaFuncion){
						//log4j.debug("sumaPonderacionDirec -> remove");
						itFuncionPerfil.remove();
					}
				}
			}
		}
		log4j.debug("contDirecto:"+contDirecto);
		return contDirecto;
	}
	
	/**
	 * Se suma las ponderaciones de las coincidencias indirectas de los ngrams del perfil con el texto del candidato
	 * @param lsFuncionPerfil, lista de funciones de la posicion
	 * @param eliminaFuncion, 	true si se elimina la funcion 
	 * 							false lo contrario
	 * @return un valor ponderado decimal
	 */
	private float  sumaPonderacionIndirec(List<PerfilNgramDto> lsFuncionPerfil, boolean eliminaFuncion){
		 float contIndirecto=0;
		 if(!texto.equals("") && lsFuncionPerfil.size() > 0){
			 //Match indirecto
			 Iterator<PerfilNgramDto> itFuncionPerfil= lsFuncionPerfil.iterator();
			 while(itFuncionPerfil.hasNext()){
					PerfilNgramDto funcionDto=itFuncionPerfil.next();
					if(texto.indexOf(funcionDto.getDescripcion().trim()) != -1){
						log4j.debug("Ponderacion:"+funcionDto.getPonderacion()+
						"\tMatch(indirecto):"+funcionDto.getDescripcion());
						texto=texto.replaceAll(funcionDto.getDescripcion().trim(), " ");
						contIndirecto+=funcionDto.getPonderacion();	
						//Se elimina funcion
						if(eliminaFuncion){
							//log4j.debug("sumaPonderacionIndirec -> remove");
							itFuncionPerfil.remove();
						}
					}
				}
		 }
		log4j.debug(" contIndirecto="+contIndirecto);
		return contIndirecto;
	  }
	
	/**
	 * Se suma las ponderaciones de las coincidencias indirectas de los ngrams del perfil con el texto del candidato
	 * @param lsFuncionPerfil, lista de funciones de la posicion
	 * @param eliminaFuncion, 	true si se elimina la funcion 
	 * 							false lo contrario
	 * @return un valor ponderado decimal
	 */
	private float  sumaPonderacionIndirecSimilar(List<PerfilNgramDto> lsFuncionPerfil, 
												boolean eliminaFuncion,String expRegComodin){
		 float contIndirecto=0;
		 log4j.debug(" sumaPonderacionIndirecSimilar -> expRegComodin="+expRegComodin+
				 	" lsFuncionPerfil="+lsFuncionPerfil.size()+
				 	" matches_texto="+!Pattern.matches(Constante.EXP_REG_ANALISIS_NGRAM_TEXTO,texto)+
				 	" texto="+texto);
		 if((!texto.equals("") && !Pattern.matches(Constante.EXP_REG_ANALISIS_NGRAM_TEXTO,texto))
			&& lsFuncionPerfil.size() > 0){
			 log4j.debug(" sumaPonderacionIndirecSimilar -> match="+texto.matches(".*(".concat(expRegComodin).concat(").*")));
			 String[] tokens=texto.split(expRegComodin);
			 log4j.debug(" sumaPonderacionIndirecSimilar -> tokens_length:"+(tokens != null ? tokens.length:null));
			 
			 //Se revisa si hay tokens
			 if(tokens != null && tokens.length > 0){
				 //Match indirecto
				 Iterator<PerfilNgramDto> itFuncionPerfil= lsFuncionPerfil.iterator();
				 float resp;
				 while(itFuncionPerfil.hasNext()){
					PerfilNgramDto funcionDto=itFuncionPerfil.next();
					funcionDto.setDescripcion(funcionDto.getDescripcion().trim());
					for (int i = 0; i < tokens.length; i++) {
						//se analiza que no sea un token basura
						if(!tokens[i].equals("") && 
						   !Pattern.matches(Constante.EXP_REG_ANALISIS_NGRAM_TEXTO,tokens[i].trim()) &&
						   !Pattern.matches(Constante.EXP_REG_ANALISIS_NGRAM_TEXTO,funcionDto.getDescripcion())){
								resp=UtilsTCE.similarity(funcionDto.getDescripcion(),tokens[i].trim());								
								if( resp > 0.0f){
									log4j.debug("sumaPonderacionIndirecSimilar -> Ponderacion:"+funcionDto.getPonderacion()+
									" token_funcion:"+funcionDto.getDescripcion()+
									" token_texto:"+tokens[i].trim()+
									" similarityResp="+resp);
									texto=texto.replaceAll(funcionDto.getDescripcion()," ");
									contIndirecto+=funcionDto.getPonderacion()+resp;	
									
									//Se elimina funcion
									if(eliminaFuncion){
										//log4j.debug("sumaPonderacionIndirecSimilar -> remove");
										itFuncionPerfil.remove();
									}
									break;
								}
						}
					}	
				}
			 }
		 }
		log4j.debug(" sumaPonderacionIndirecSimilar -> contIndirecto="+contIndirecto);
		return contIndirecto;
	  }
	
	/**
	 * Se filtran los textos_ngram, dado una expresion regular.
	 * @param lsFuncionPerfil, lista de funciones_ngram
	 * @param expRegComodin, expresion regular
	 * @return una lista de objetos PerfilNgramDto
	 */
	private List<PerfilNgramDto> filtroTokens(List<PerfilNgramDto> lsFuncionPerfil, String expRegComodin){
		List<PerfilNgramDto> itFuncionResp=new ArrayList<PerfilNgramDto>();
		Iterator<PerfilNgramDto> itFuncionPerfil= lsFuncionPerfil.iterator();
		while(itFuncionPerfil.hasNext()){
			PerfilNgramDto funcionDto=itFuncionPerfil.next();
			//if(funcionDto.getDescripcion().matches(".*(".concat(expRegComodin).concat(").*"))){
				String[] tokens=funcionDto.getDescripcion().trim().split(expRegComodin);
				log4j.debug("Match(Tokens):"+funcionDto.getDescripcion());
				for (int i = 0; i < tokens.length; i++) {
					if(!tokens[i].trim().equals("etc")){
						PerfilNgramDto funcionDtoToken=new PerfilNgramDto();
						funcionDtoToken.setDescripcion(" ".concat(tokens[i]).concat(" "));
						funcionDtoToken.setPonderacion(funcionDto.getPonderacion());
						log4j.debug("\tToken:"+funcionDtoToken.getDescripcion());
						itFuncionResp.add(funcionDtoToken);
					}
				}	
			//}
		}
		return itFuncionResp;
	}

	
	
	/**
	 * Asigna los datos procesados (Calculados) de experiencia Laboral,
	 *  Grado Academico y estatus escolar maximos.
	 * @param persona
	 * @param cvDto
	 */
	private void setPreprocecedData(Persona persona, CurriculumDto cvDto) throws Exception {
		if(cvDto!=null){
			if(cvDto.getDiasExperienciaLaboral()!=null){
				log4j.debug("getDiasExperienciaLaboral: " + cvDto.getDiasExperienciaLaboral() );
				persona.setDiasExperienciaLaboral( Long.parseLong(cvDto.getDiasExperienciaLaboral()) );
			}
			if(cvDto.getIdGradoAcademicoMax()!=null){
				log4j.debug("getIdGradoAcademicoMax: " + cvDto.getIdGradoAcademicoMax() );
				GradoAcademico gradoAcad = new GradoAcademico();
				gradoAcad.setIdGradoAcademico( Long.parseLong(cvDto.getIdGradoAcademicoMax()) );
				persona.setGradoAcademico(gradoAcad);
			}
			if(cvDto.getIdEstatusEscolarMax()!=null){
				log4j.debug("getIdEstatusEscolarMax: " + cvDto.getIdEstatusEscolarMax() );
				EstatusEscolar estatusEscolar = new EstatusEscolar();
				estatusEscolar.setIdEstatusEscolar( Long.parseLong(cvDto.getIdEstatusEscolarMax()) );
				persona.setEstatusEscolar(estatusEscolar);
			}
			if(cvDto.getTituloMax()!=null){
				log4j.debug("getTituloMax: " + cvDto.getTituloMax() );
				persona.setTituloMax(cvDto.getTituloMax());
			}
		}
		else{
			log4j.debug("El Dto es null, no hay nada que procesar");
		}
	}
	
	

	/**
	 * Valida y publica un curriculum
	 * @author Osy Fixed by Evalle 
	 * @param idPersona Identificador de la persona  
	 * @return List<CandidatoDto> Exito : Mensaje Vacío o Error : 
	 * Informacion de la persona y atributos que fallaron cuyo curriculum se publico o se intento publicar 
	 * @throws Exception 
	 */	
	@SuppressWarnings("unchecked")
	public Object setResumePublication(CandidatoDto candidatoDto) throws Exception {
		log4j.debug("<setResumePublication> Inicio...getIdPersona="+candidatoDto.getIdPersona());
		//byte empresssa = Constante.EMPRESA_TCE;
		
		List<CandidatoDto> lsCandidatoDtoOut=new ArrayList<CandidatoDto>();
		long estatusInscripcionOut;

		Persona persona = personaDao.read(candidatoDto.getIdPersona());
		
		log4j.debug("<setResumePublication> persona="+persona);

		//si existe la persona
		if(persona != null){
			CurriculumDto curriculumDto = null; //DTO que contendra los datos para validación
			List<EmpresaParametroDto> lsEmpresaParametroDto = null;
			//Obtiene la lista de atributos requeridos para la publicación
			Object object=empresaParametroService.get(new EmpresaParametroDto(Constante.TIPO_PARAMETRO_ATRIBUTO_REQUERIDO,
																			  candidatoDto.getIdEmpresaConf()),true);
			if(object instanceof EmpresaParametroDto ){
				//No existen valores de Publicación, se envia error 
				log4j.error("No existen criterios en la tabla EMPRESA_PARAMETRO para publicar curriculum");
				return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_FATAL,
						Mensaje.MSG_ERROR));
			}else{
				lsEmpresaParametroDto = (List<EmpresaParametroDto>)object;
				log4j.debug("<setResumePublication> lsEmpresaParametroDto.size :" +  lsEmpresaParametroDto.size());	
				//Se obtiene DTO en base al Pojo para procesamiento y Validación
				curriculumDto = curriculumAssembler.getCvDto(persona);
				
				//FIX para generar AreaPersona en DTO
				if(persona.getAreaPersonas()!=null){
					List<AreaPersonaDto> lsModelo = new ArrayList<AreaPersonaDto>();
					Iterator<AreaPersona> itModelObj = persona.getAreaPersonas().iterator();
					AreaPersona apTmp;
					while(itModelObj.hasNext()){
						apTmp = itModelObj.next();
						AreaPersonaDto nDto = new AreaPersonaDto();
						nDto.setIdAreaPersona(String.valueOf(apTmp.getIdAreaPersona()));
						nDto.setIdArea(String.valueOf(apTmp.getArea().getIdArea()));
						nDto.setIdPersona(String.valueOf(apTmp.getPersona().getIdPersona()));;
						nDto.setPrincipal(apTmp.isPrincipal());//principal
						if(apTmp.isPrincipal()){
							curriculumDto.setIdAreaPrincipal(nDto.getIdArea());
						}
						nDto.setConfirmada(apTmp.isConfirmada());//confirmada
						nDto.setPersonal(apTmp.isPersonal());		//personal
						
						lsModelo.add(nDto);
					}
					curriculumDto.setAreaPersona(lsModelo);
				}
				
				log4j.debug("curriculumDto.titulo_max= " + curriculumDto.getTituloMax() );
				
				//Se itera para hacer preprocesamientos dinamicos con reflection
				Iterator<EmpresaParametroDto> itParametro = lsEmpresaParametroDto.iterator();
				while(itParametro.hasNext()){
					EmpresaParametroDto empParametroDto = itParametro.next();
					if(empParametroDto.getFuncion()!=null && !empParametroDto.getFuncion().trim().equals("")){
						log4j.debug("<Publication calculated attributes>\n valor: " + empParametroDto.getValor() 
								+ "\n funcion: "+empParametroDto.getFuncion());
						Class<?>[] argumentTypes = { CurriculumDto.class};
						Object[] arguments = { curriculumDto };
						
						//Se ejecutan las funciones (tabla EMPRESA_PARAMETRO)) 
						UtilsTCE.executeReflexion(new ParametersTCE(),empParametroDto.getFuncion(),
												  null, argumentTypes,arguments);
					}
				}
				log4j.debug("curriculumDto.diasExperiencia= " +curriculumDto.getDiasExperienciaLaboral() );
				log4j.debug("curriculumDto.gradoMaximo= " + curriculumDto.getIdGradoAcademicoMax() );
				log4j.debug("curriculumDto.estatusEscolarMax= " + curriculumDto.getIdEstatusEscolarMax() );
				log4j.debug("curriculumDto.titulo_max= " + curriculumDto.getTituloMax() );
				log4j.debug("localizacion: " + curriculumDto.getLocalizacion().size() );
				
				//Se envia el Dto para registrar los datos calculados en Persona
				setPreprocecedData(persona, curriculumDto);
				log4j.debug("localizacion: " + curriculumDto.getLocalizacion().size() );
				
				//Se envia al metodo principal de Validacion de Dto-persona 
				lsCandidatoDtoOut = ParametersTCE.mainResumePersonaDtoValidations(curriculumDto, lsEmpresaParametroDto);
				
				
				log4j.debug("localizacion: " + curriculumDto.getLocalizacion().size() );
				
				//una vez validado, se actualiza si cambia estatus a publicado o no publicado
				if(lsCandidatoDtoOut!=null && lsCandidatoDtoOut.size()>0){
					log4j.debug("errores encontrados: "+ lsCandidatoDtoOut.size() );
					if(persona.getEstatusInscripcion().
							getIdEstatusInscripcion() == Constante.ESTATUS_INSCRIPCION_PUBLICADO.byteValue()){
						estatusInscripcionOut = Constante.ESTATUS_INSCRIPCION_ACTIVO.byteValue();
					}else{
						estatusInscripcionOut = persona.getEstatusInscripcion().getIdEstatusInscripcion();					
					}
				}else{
					log4j.debug("lsCandidatoDtoOut es null" + lsCandidatoDtoOut );
					estatusInscripcionOut = Constante.ESTATUS_INSCRIPCION_PUBLICADO.byteValue();
					//Se reinicia los parametros del demonio de "debe publicar"
					persona.setNumeroDebePublicar((short)0);
					persona.setFechaDebePublicar(DateUtily.getToday());
				}
				
				EstatusInscripcion estatusInscripcion = new EstatusInscripcion();
				persona.setEstatusInscripcion(estatusInscripcion);
				persona.getEstatusInscripcion().setIdEstatusInscripcion(estatusInscripcionOut);
				persona.setFechaModificacion(DateUtily.getToday());	
				persona.setClasificado(false);
				
				//Si la persona es actualmente un candidato, se registra el estatus correspondiente, es decir
				// estatus_candidato = 8 (Por calcular) y bandera de modificado a true 
				Candidato candidato = candidatoDao.getApplicantInfo(candidatoDto);
				if(candidato != null){
					log4j.debug("<setResumePublication> candidatoDto.getIdCandidato :" + candidato.getIdCandidato());
					EstatusCandidato estatusCandidato = new EstatusCandidato();
					estatusCandidato.setIdEstatusCandidato(Constante.ESTATUS_CANDIDATO_POR_CALCULAR);
					candidato.setEstatusCandidato(estatusCandidato);		
					candidato.setFechaModificacion(DateUtily.getToday());				
					candidato.setModificado(true);
					applicantDao.update(candidato);
				}else{
					log4j.debug("<setResumePublication> No hay información de candidato para esta persona (No ha clasificado aún) ");				
				}
				
				log4j.debug("<setResumePublication> isSePreClasifica:" + persona.isSePreClasifica() +
						" estatusInscripcionOut="+estatusInscripcionOut);
				
				// Si el estatus es de Publicado y si se puede pre-clasificar, se envia al Hilo para almacenarlo en Solr 
				if(estatusInscripcionOut == Constante.ESTATUS_INSCRIPCION_PUBLICADO.byteValue()
				 && persona.isSePreClasifica()){
					
					log4j.debug("<setResumePublication> Se envía el doc a solr" );
					
					//Escribir/actualizar documento en solr
					solrService.threadwriteInSolr(persona);
					
					log4j.debug("<setResumePublication> ThreadExceptionCache.isEmpty=" +ThreadExceptionCache.isEmpty()
					+ (ThreadExceptionCache.isEmpty()?"No hay ":"Existe ")+"excepcion en Hilos");
					//Se analiza si hubo un error fatal en mandar el doc en un hilo
					//y si lo hay regresar el fatal
					/*if(!ThreadExceptionCache.isEmpty()){ 	
						return UtilsTCE.getJsonMessageGson(null,
								ThreadExceptionCache.elemens().nextElement());
            		}*/
					//se pone la fecha de publicacion
					persona.setFechaPublicacion(DateUtily.getToday());
				}
				else {
					log4j.debug("<setResumePublication> NO SE ENVIA A SOLR");
				}
			}
		}else{//NO existe persona con Id en persistencia
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR));
		}
		
		log4j.debug("<setResumePublication> Fin.");
		return lsCandidatoDtoOut;
	}
	
}	