package net.tce.util;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.tce.cache.EscolaridadCache;
import net.tce.dto.AcademicBackgroundDto;
import net.tce.dto.CandidatoDto;
import net.tce.dto.CurriculumDto;
import net.tce.dto.CurriculumEmpresaDto;
import net.tce.dto.EmpresaParametroDto;
import net.tce.dto.VacancyDto;
import net.tce.dto.WorkExperienceDto;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.jexl2.JexlContext;
import org.apache.log4j.Logger;

public class ParametersTCE {
	static String dateFormatDefault = "MM/dd/yyyy";
	static Logger log4j = Logger.getLogger("ParametersTCE".getClass());
	boolean cumpleFiltrosTracking;

	/**
	 * Realiza las validaciones en base a la lista de empresa-Parametro recibida, 
	 * utilizando reflection en otra clase de Validador.java
	 * @param personaVDto
	 * @param lsEmpresaParametroDto
	 * @return
	 * @throws Exception
	 */
	public static List<CandidatoDto> mainResumePersonaDtoValidations(Object personaVDto,
									List<EmpresaParametroDto> lsEmpresaParametroDto) throws Exception{
		List<CandidatoDto> lsCandidatoDtoOut=new ArrayList<CandidatoDto>();
		
		log4j.debug("\n >>>>>>>>>>>>>>>>>>>>>>>>>>>>>  validacion en mapRequiredAttributes...");
		HashMap<String,String>  mapRequiredAttributes = ParametersTCE.getRequiredAttributes(personaVDto, lsEmpresaParametroDto);
		log4j.debug("\n fin de validacion en mapRequiredAttributes  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		log4j.debug(">+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+ Validador.checkExpressions");		
		HashMap<String,String> mapFailedAttributes = Validador.checkExpressions(mapRequiredAttributes, personaVDto);			
		log4j.debug("\n Fin de Validador.checkExpressions  <+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+");
		
		if(mapFailedAttributes.size() > 0){
			log4j.debug("Creando lista de errores");			
			Iterator<String> itFailedAttributes = mapFailedAttributes.keySet().iterator();
			while(itFailedAttributes.hasNext()){
				String failedAttribute = itFailedAttributes.next();
				String description = mapFailedAttributes.get(failedAttribute);
				CandidatoDto candidatoDtoOut = new CandidatoDto(UtilsTCE.firstExpElement(failedAttribute),description);
				
				lsCandidatoDtoOut.add(candidatoDtoOut);
			}
		}
		
		return lsCandidatoDtoOut;
	}
	
	/**
	 * 
	 * @param empresaVDto
	 * @param lsEmpresaParametroDto
	 * @return
	 * @throws Exception
	 */
	public static List<CurriculumEmpresaDto> mainResumeEmpresaDtoValidations(
			Object empresaVDto, List<EmpresaParametroDto> lsEmpresaParametroDto)
			throws Exception {
		HashMap<String, String> mapRequiredAttributes = null, mapFailedAttributes = null;
		List<CurriculumEmpresaDto> lsEmpresaDtoOut = new ArrayList<CurriculumEmpresaDto>();

		log4j.debug("\n >>>>>>>>>>>>>>>>>>>>>>>>>>>>>  validacion en mapRequiredAttributes...");
		mapRequiredAttributes = ParametersTCE.getRequiredAttributes(
				empresaVDto, lsEmpresaParametroDto);
		log4j.debug("\n fin de validacion en mapRequiredAttributes  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		log4j.debug(">+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+ Validador.checkExpressions");
		mapFailedAttributes = Validador.checkExpressions(mapRequiredAttributes,
				empresaVDto);
		log4j.debug("\n Fin de Validador.checkExpressions  <+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+");

		if (mapFailedAttributes.size() > 0) {
			log4j.debug("Creando lista de errores");
			Iterator<String> itFailedAttributes = mapFailedAttributes.keySet()
					.iterator();
			while (itFailedAttributes.hasNext()) {
				String failedAttribute = itFailedAttributes.next();
				String description = mapFailedAttributes.get(failedAttribute);
				CurriculumEmpresaDto empresaDtoOut = new CurriculumEmpresaDto(
						UtilsTCE.firstExpElement(failedAttribute), description);

				lsEmpresaDtoOut.add(empresaDtoOut);
			}
		}

		return lsEmpresaDtoOut;
	}
	/**
	 * DEPRECADO
	 * TODO eliminar en siguiente revisión
	 * Realiza las validaciones en base a la lista de empresa-Parametro recibida, 
	 * utilizando reflection en otra clase de Validador.java
	 * @param posicionValidacionDto
	 * @param lsEmpresaParametroDto
	 * @return
	 * @throws Exception
	 */
	protected static List<VacancyDto> mainResumePosicionDtoValidations(
			Object posicionVDto,List<EmpresaParametroDto> lsEmpresaParametroDto) throws Exception {
		HashMap<String, String> mapRequiredAttributes = null, mapFailedAttributes = null;
		List<VacancyDto> lsPositionsDtoOut = new ArrayList<VacancyDto>();

		log4j.debug("\n >>>>>>>>>>>>>>>>>>>>>>>>>>>>>  validacion en mapRequiredAttributes...");
		mapRequiredAttributes = ParametersTCE.getRequiredAttributes(posicionVDto, lsEmpresaParametroDto);
		log4j.debug("\n fin de validacion en mapRequiredAttributes  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		log4j.debug(">+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+ Validador.checkExpressions");
		mapFailedAttributes = Validador.checkExpressions(mapRequiredAttributes,posicionVDto);
		log4j.debug("\n Fin de Validador.checkExpressions  <+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+");

		if (mapFailedAttributes.size() > 0) {
			log4j.debug("Creando lista de errores");
			Iterator<String> itFailedAttributes = mapFailedAttributes.keySet()
					.iterator();
			while (itFailedAttributes.hasNext()) {
				String failedAttribute = itFailedAttributes.next();
//				String description = mapFailedAttributes.get(failedAttribute);
				VacancyDto posicionDtoOut = new VacancyDto(new Long(1),
						UtilsTCE.firstExpElement(failedAttribute),
						mapFailedAttributes.get(failedAttribute));

				lsPositionsDtoOut.add(posicionDtoOut);
			}
		}
		return lsPositionsDtoOut;
	}
	
	
	
	/**
	 * Calcula dias en experiencia laborales a partir de una lista de ExperienciasLaborales	[WorkExperienceDto]
	 * @param lsWorkExps
	 * @return
	 * @throws ParseException 
	 */
	public static long getDiasExperienciaLaboral(List<WorkExperienceDto> lsWorkExps) throws ParseException{
		log4j.debug("<getDiasExperienciaLaboral> Inicio");
		long diasExperienciaTotal = 0;
		Calendar calendar = Calendar.getInstance(); 
		String currentYear;
		String  anioFin,mesFin,  diaFin;
		if(lsWorkExps!=null && lsWorkExps.size()>0){
			Iterator<WorkExperienceDto> itWorkExp = lsWorkExps.iterator();
			while(itWorkExp.hasNext()){
				WorkExperienceDto workExpDto = itWorkExp.next();
				currentYear = String.valueOf(calendar.get(Calendar.YEAR));
				log4j.debug(" TrabajoActual="+workExpDto.getTrabajoActual()+
						" TrabajoActual_boolean="+workExpDto.getTrabajoActual());
				if(workExpDto.getTrabajoActual()!=null && 
				   Boolean.getBoolean(workExpDto.getTrabajoActual())){
					mesFin = String.valueOf(calendar.get(Calendar.MONTH) + 1);
					diaFin = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
					anioFin = currentYear;
				}else{
					mesFin = UtilsTCE.nvl(workExpDto.getMesFin(), "1");
					diaFin = UtilsTCE.nvl(workExpDto.getDiaFin(), "1");
					anioFin = UtilsTCE.nvl(workExpDto.getAnioFin(), currentYear);
				}
				log4j.debug("dtFIn: "+mesFin+"/"+diaFin+"/"+anioFin);						
				diasExperienciaTotal+=  DateUtily.daysBetween(
							DateUtily.string2Date(UtilsTCE.nvl(workExpDto.getMesInicio(), "1")+"/"+
												  UtilsTCE.nvl(workExpDto.getDiaInicio(), "1")+"/"+
												  UtilsTCE.nvl(workExpDto.getAnioInicio(), currentYear ),
												  dateFormatDefault), 
							DateUtily.string2Date(mesFin+"/"+diaFin+"/"+anioFin, dateFormatDefault));
			}
		}
		
		return diasExperienciaTotal;
	}

	
	
	/**
	 * Obtiene la lista de atributos requeridos global (empresa TCE) para poder publicar 
	 * NOTA : Considera la condicion para poder evaluar la propiedad
	 * @param Object Objeto que contiene los atributos a evaluar
	 * @param List<EmpresaParametroDto> Lista de atributos requeridos junto con su condicion y contexto
	 * @return Lista de atributos requeridos para publicar el curriculum, los atributos de esta lista resultado 
	 * ya pasaron por las condiciones para su validacion. La existencia de valor de estos atributos se valida posteriormente
	 */			
	@SuppressWarnings({ "unchecked" })
	public static HashMap<String,String> getRequiredAttributes(Object objectDto, 
									List<EmpresaParametroDto> lsEmpresaParametroDto) throws Exception{
		log4j.debug("<getRequiredAttributes> Inicio...");
		HashMap<String,String> mapRequiredAttributes = new HashMap<String,String>();
		//EmpresaParametroDto empresaParametroDto=new EmpresaParametroDto();
		String conditionForList;
		String attributeForList;
		String listName;
		String listElementName;
		int indexReference = 0;
		
		JexlContext jc = Validador.prepareSet(objectDto);
		
		Iterator<EmpresaParametroDto> itParametro = lsEmpresaParametroDto.iterator();
		while(itParametro.hasNext()){
			
			EmpresaParametroDto empresaParametroDtoResult = itParametro.next();
			log4j.debug("<getRequiredAttributes> +++++++++++++++++++++++++"+
					"\n Orden: " + empresaParametroDtoResult.getOrden() + " Atributo: " + empresaParametroDtoResult.getValor() +  
					"\n (pre)Condicion: "+ empresaParametroDtoResult.getCondicion() + 
					"\n Descripcion (Msg-error): "+ empresaParametroDtoResult.getDescripcion()
					);
			
			if(empresaParametroDtoResult.getContexto() != null && empresaParametroDtoResult.getContexto().equals("ALL")){
				log4j.debug("<getRequiredAttributes> Evaluando Contexto, todos los elementos de la lista...");

				Pattern p = Pattern.compile("\\w+(?=\\[)");
				Matcher m = p.matcher(empresaParametroDtoResult.getValor());
				log4j.debug("<getRequiredAttributes> m.find() :" + m.find());
				listName = m.group();	//.toString();
				log4j.debug("<getRequiredAttributes> listName :" + listName);
				List<Object> objectList= (List<Object>) PropertyUtils.getNestedProperty(objectDto, listName);
				
				log4j.debug("<getRequiredAttributes> objectList.:" + objectList);
				if(objectList != null){
					for(indexReference=0; indexReference < objectList.size(); indexReference++){
						listElementName = listName + "[" + indexReference + "]";
						log4j.debug("<getRequiredAttributes> listElementName :" + listElementName);
											
						attributeForList = empresaParametroDtoResult.getValor().replaceAll(listName + "\\[\\w+\\]", listElementName);
						log4j.debug("<getRequiredAttributes> attributeForList: " + attributeForList );
						conditionForList = null;
						if(empresaParametroDtoResult.getCondicion() != null){
							conditionForList = empresaParametroDtoResult.getCondicion().replaceAll(listName + "\\[\\w+\\]", listElementName);
							log4j.debug("<getRequiredAttributes> attributeForList/conditionForList :" + attributeForList + "/" + conditionForList);
						}
						if (Validador.checkCondition(conditionForList,objectDto, jc)){
							log4j.debug("<getRequiredAttributes> Condicion CUMPLIDA, Se agrega al mapa de Requeridos para Publicación");			
						}else{
							log4j.debug("<getRequiredAttributes> Condicion NO CUMPLIDA, Este atributo no se validara en Publicación."); 
							continue;
						}
						mapRequiredAttributes.put(attributeForList, empresaParametroDtoResult.getDescripcion());
					}
				}
			}else{			
				log4j.debug("<getRequiredAttributes> Evaluando Contexto vacío, atributo individual...");
				if (Validador.checkCondition(empresaParametroDtoResult.getCondicion(),objectDto, jc)){
					log4j.debug("<getRequiredAttributes> Condicion CUMPLIDA, Se agrega al mapa de Requeridos para Publicación");
				}else{
					log4j.debug("<getRequiredAttributes> Condicion NO CUMPLIDA, Este atributo no se validara en Publicación."); 
					continue;
				}
				mapRequiredAttributes.put(empresaParametroDtoResult.getValor(), empresaParametroDtoResult.getDescripcion());
			}
		}
		log4j.debug("<getRequiredAttributes> Número de Atributos requeridos para Publicar: [mapRequiredAttributes] :" +  mapRequiredAttributes.size() + ".\n +++++++ FIN. ");
		return mapRequiredAttributes;
	}	

	/**
	 * Metodo usado en Reflection para calcular dias de experiencia laboral
	 * @param curriculumDto,  objeto donde se obtiene las propiedades necesarias
	 * @return
	 * @throws ParseException 
	 */
	public CurriculumDto setDiasExperiencia(CurriculumDto curriculumDto) throws ParseException{
//		log4j.debug("<setDiasExperiencia> Calculando dias de experiencia laboral, invocado por reflexion ");
		if(curriculumDto!=null && (curriculumDto.getExperienciaLaboral()!=null)){
//			log4j.debug("<setDiasExperiencia> curriculumDto detectado, se calcula en getDiasExperienciaLaboral");
			curriculumDto.setDiasExperienciaLaboral(String.valueOf(
					getDiasExperienciaLaboral(curriculumDto.getExperienciaLaboral())));
//			log4j.debug("Se obtuvieron " + curriculumDto.getDiasExperienciaLaboral() +" dias ");
		}
		return curriculumDto;
	}
	
	/**
	 * Metodo usado en Reflection que valida si almenos existe una escolaridad y/o experiencia. 
	 * @param curriculumDto, objeto donde se obtiene las propiedades necesarias
	 * @return
	 */
	public CurriculumDto validaAlMenosUnaEscOExp(CurriculumDto curriculumDto){
		//Si usuario tiene una experiencia o una Escolaridad al menos
		if((curriculumDto.getExperienciaLaboral() != null && curriculumDto.getExperienciaLaboral().size() > 0) ||
		   (curriculumDto.getEscolaridad() != null && curriculumDto.getEscolaridad().size() > 0)	){
			curriculumDto.setAlMenosUnaEscOExp(true);
		}else{
			curriculumDto.setAlMenosUnaEscOExp(false);
		}		
		return curriculumDto;
	}
	
	/**
	 * Metodo usado en Reflection para Asignar Grado y estatus maximos
	 * @param curriculumDto
	 * @return
	 */
	public CurriculumDto setGradoEstatusMaximos(CurriculumDto curriculumDto){
		if(curriculumDto!=null && (curriculumDto.getEscolaridad()!=null)){
			byte nivelGradoEscolarMax=0, nivelEstatusEscolarMax=0;
			AcademicBackgroundDto maximoDto=null;
			log4j.debug("<setGradoEstatusMaximos> Comparando Grados y estatus ["+curriculumDto.getEscolaridad().size()
					+"] para obtener el mayor, idPersona="+curriculumDto.getIdPersona());
			if(curriculumDto.getEscolaridad().size() > 0){
					Iterator<AcademicBackgroundDto> itEscolaridad=curriculumDto.getEscolaridad().iterator();
					while(itEscolaridad.hasNext()){
						AcademicBackgroundDto escolaridadDto=itEscolaridad.next();
						log4j.debug("<setGradoEstatusMaximos> IdGradoAcademico="+escolaridadDto.getIdGradoAcademico()+
								" IdEstatusEscolar="+escolaridadDto.getIdEstatusEscolar()+								
								" Titulo="+escolaridadDto.getTitulo() +
								" Texto="+escolaridadDto.getTexto());
						log4j.debug("<setGradoEstatusMaximos> containsKeyNivelGradoAcademico="+
								(escolaridadDto.getIdGradoAcademico() == null ? null:
								EscolaridadCache.containsKeyNivelGradoAcademico( Long.valueOf(escolaridadDto.getIdGradoAcademico()))));
						
						//Si no hay NivelGradoAcademico se obtiene de cache (esto se aplica solo para una carga masiva)
						if(escolaridadDto.getNivelGradoAcademico() == null &&
						   escolaridadDto.getIdGradoAcademico() != null){
								escolaridadDto.setNivelGradoAcademico(EscolaridadCache.getNivelGradoAcademico(
																Long.valueOf(escolaridadDto.getIdGradoAcademico())));
						}
						
						//Si no hay NivelEstatusAcademico se obtiene de cache(esto se aplica solo para una carga masiva)
						if(escolaridadDto.getNivelEstatusAcademico() == null &&
						   escolaridadDto.getIdEstatusEscolar() != null){
								escolaridadDto.setNivelEstatusAcademico(EscolaridadCache.getNivelEstatusEscolar(
																Long.valueOf(escolaridadDto.getIdEstatusEscolar())));
						}	
						
						//si no hay titulo, tomarlo de texto(esto se aplica solo para una carga masiva) TODO distinguir masivo de Normal, causa error
//						if(escolaridadDto.getTitulo() == null){
//							escolaridadDto.setTitulo(escolaridadDto.getTexto()); //TITULO en Pojo es de 250 caracteres
//						}
						log4j.debug("<setGradoEstatusMaximos> nivelGradoAcademico="+escolaridadDto.getNivelGradoAcademico()+
									 " nivelEstatusAcademico="+escolaridadDto.getNivelEstatusAcademico()+
									 " titulo= " + escolaridadDto.getTitulo()+
									 " AnioInicio="+escolaridadDto.getAnioInicio()+
									 " AnioFin="+escolaridadDto.getAnioFin());
						
						//Se analiza el grado y estatus academico para la obtencion del max
						if(escolaridadDto.getNivelGradoAcademico() != null &&
						   escolaridadDto.getNivelEstatusAcademico() != null){
							
							if(escolaridadDto.getNivelGradoAcademico().byteValue() > nivelGradoEscolarMax){
								nivelGradoEscolarMax=escolaridadDto.getNivelGradoAcademico().byteValue();
								nivelEstatusEscolarMax=escolaridadDto.getNivelEstatusAcademico().byteValue();
								maximoDto=escolaridadDto;
							}else if(escolaridadDto.getNivelGradoAcademico().byteValue() == nivelGradoEscolarMax){				
								if(escolaridadDto.getNivelEstatusAcademico() < nivelEstatusEscolarMax){
									nivelEstatusEscolarMax=escolaridadDto.getNivelEstatusAcademico().byteValue();
									maximoDto=escolaridadDto;
								}
							}
						}
					}		
					if(maximoDto != null){
						log4j.debug("<setGradoEstatusMaximos> maximoDto: IdGradoAcademicoMax="+maximoDto.getIdGradoAcademico()+
							" IdEstatusEscolar="+maximoDto.getIdEstatusEscolar()+
							" Titulo="+maximoDto.getTitulo());
						curriculumDto.setIdGradoAcademicoMax(maximoDto.getIdGradoAcademico());
						curriculumDto.setIdEstatusEscolarMax(maximoDto.getIdEstatusEscolar());
						curriculumDto.setTituloMax(maximoDto.getTitulo());
					}	
			}
		}
		return curriculumDto;
	}
	
	//TODO crear metodo derivado de setGradoEstatusMaximos para obtener (ultimo) PUESTO para tabla persona [curriculumDto]
	
}
