package net.tce.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.tce.dto.CandidatoDto;
import net.tce.dto.EmpresaParametroDto;
import net.tce.dto.VacancyDto;

import org.apache.commons.jexl2.JexlContext;
import org.apache.log4j.Logger;

/**
 * Se crea nuevo Proceso validador pues el incluido en ParametersTCE no contempla elementos anidados a un 2o Nivel
 * @author tce
 *
 */
public class ValidadorPublicacion {

	static Logger log4j = Logger.getLogger( ValidadorPublicacion.class );
	
	/**
	 * 
	 * @param personaVDto
	 * @param lsEmpresaParametroDto
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<CandidatoDto> mainResumePersonaDtoValidations(Object personaVDto,
			List<EmpresaParametroDto> lsEmpresaParametroDto) throws Exception{
		List<CandidatoDto> lsErrorDetail = new ArrayList<CandidatoDto>();
		log4j.debug("\n<mainResumePersonaDtoValidations>personaVDto a evaluar: \n" + personaVDto );
		//TODO validar respuesta cuando lsEmpresaParametroDto == null
		
		//try{
			List<EmpresaParametroDto> lsEmpresaPmsIndexados = 
					getLsEmpresaParametroRequerido(lsEmpresaParametroDto, personaVDto);
			log4j.debug("<mainResumePosicionDtoValidations> \n >>>>>>>>>>>>>>>>>>>>>>>>>>>>> 2) Se valida el Objeto con los valores indexados uno a uno, con la libreria JexlContent:");
			log4j.debug("<mainResumePosicionDtoValidations> lsEmpresaPmsIndexados="+lsEmpresaPmsIndexados.size());
			lsErrorDetail = (List<CandidatoDto>)evaluateObjectByEmpParam(lsEmpresaPmsIndexados, personaVDto);
			log4j.debug("<mainResumePosicionDtoValidations> \n" + (lsErrorDetail.isEmpty()?" Es vacio, es publicable ":"Existen condiciones no cumplidas para la publicación")  );
		/*}catch (Exception e){
			log4j.fatal("<mainResumePersonaDtoValidations> Error al validar el CV:" + e.getMessage(), e);
		}*/
		return lsErrorDetail;
	}
	
	/**
	 * Proceso condensador de subProcesos para la validación de condiciones de Publicación para <strong>Posición<strong>
	 * @param posicionVDto
	 * @param lsEmpresaParametroDto
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<VacancyDto> mainResumePosicionDtoValidations(
			Object posicionVDto,
			List<EmpresaParametroDto> lsEmpresaParametroDto) throws Exception {
		List<VacancyDto> lsErrorDetail = new ArrayList<VacancyDto>();
		log4j.debug("\n<mainResumePosicionDtoValidations>posicionVDto a evaluar: \n" + posicionVDto );
		
		//TODO validar respuesta cuando lsEmpresaParametroDto == null
		
		try{
			log4j.debug("\n<mainResumePosicionDtoValidations> \n >>>>>>>>>>>>>>>>>>>>>>>>>>>>> 1) Se procesan los valores de EmpParam para indexarlos con respecto al objeto...");
			
			List<EmpresaParametroDto> lsEmpresaPmsIndexados = getLsEmpresaParametroRequerido(lsEmpresaParametroDto, posicionVDto);
			log4j.debug("<mainResumePosicionDtoValidations> \n >>>>>>>>>>>>>>>>>>>>>>>>>>>>> 2) Se valida el Objeto con los valores indexados uno a uno, con la libreria JexlContent:");
//			log4j.debug("lsEmpresaPmsIndexados.size() " + lsEmpresaPmsIndexados.size() ); 
			lsErrorDetail = (List<VacancyDto>) evaluateObjectByEmpParam(lsEmpresaPmsIndexados, posicionVDto);
			log4j.debug("<mainResumePosicionDtoValidations> \n" + (lsErrorDetail.isEmpty()?" Es vacio, es publicable ":"Existen condiciones no cumplidas para la publicación")  );
		}catch (Exception e){
			log4j.fatal("<mainResumePosicionDtoValidations> Error al validar la posición:" + e.getMessage(), e);
		}
		
		return lsErrorDetail;
	}
	
	/* **************************************************************************************************** */
	/*  ********************** PROCESO 1: convertir los EmpresaParametro originales, en Indexados ********* */
	/**
	 * Obtiene la lista de Parametros requeridos Indexados (iterados) que se ejecutaran en el Jexl. i.e.: <br>
	 * Formato entrada:<br> perfilesInternos[?].textos[?].texto != null <br>Formato Salida: <br>perfilesInternos[0].textos[4].texto != null
	 * @param lsDB
	 * @param objetoAnalisis
	 * @return
	 */
	private static List<EmpresaParametroDto> getLsEmpresaParametroRequerido(List<EmpresaParametroDto> lsDB, Object objetoAnalisis) {
		List<EmpresaParametroDto> lsEmpresaParam = new ArrayList<EmpresaParametroDto>();
		if(lsDB!=null && !lsDB.isEmpty()){
			Iterator<EmpresaParametroDto> itEmpresaParam = lsDB.iterator();
			while(itEmpresaParam.hasNext()){
				EmpresaParametroDto empresaDto = itEmpresaParam.next();
				String valor = empresaDto.getValor();
				String condicionExcluyente = empresaDto.getCondicion();
				String descripcionError = empresaDto.getDescripcion();
				
				if(valor.indexOf("[?]")>0 || valor.indexOf("[0]")>0){//TODO sustituir por Matcher m
					/* Es arreglo, se itera y valida */
					String subPropiedad = valor.substring(0, valor.indexOf("."));
					String psPropiedadSub = valor.substring(valor.indexOf(".")+1,valor.length());
					log4j.debug("\t::::::::::  llamando a iterateLista :::::::::: con \n\n "
							+" valor: " + valor + "\n subPropiedad: " + subPropiedad + "\n " + psPropiedadSub );
					log4j.debug("::::::::::::::::::::::::::::::::::::::::::::::::::\n ");
					List<EmpresaParametroDto> lsIteradoDto = iterateListaDto(objetoAnalisis, subPropiedad, psPropiedadSub, condicionExcluyente);
					Iterator<EmpresaParametroDto> itLsIterado = lsIteradoDto.iterator();
					while(itLsIterado.hasNext()){
						EmpresaParametroDto dto = itLsIterado.next(); 
						/* Se agrega descripcion, pues la lista iterada es de nuevos dto's */
						dto.setDescripcion(descripcionError); //TODO crear un assembler para duplicar info dtoFinal = merge(dtoNuevo, dtoOrig)
						lsEmpresaParam.add(dto);
					}
				}else{
					log4j.debug("\t::::::::::  no llama a iterateLista :::::::::: con \n\n "
							+" valor: " + valor + "\n condicion: " + condicionExcluyente );
					log4j.debug("::::::::::::::::::::::::::::::::::::::::::::::::::\n ");
					/*indica evaluar propiedad, se Agrega dto Original*/ //TODO verificar con condicion de excepcion
					lsEmpresaParam.add(empresaDto);
				}
			}
		}
		log4j.debug("<getLsEmpresaParametroRequerido> Regresando lsEMpresaParam" );
		return lsEmpresaParam;
	}
	/**
	 * Itera los elementos en forma de Listas para procesar propiedades y posPropiedades
	 * para obtener listas Indexadas de la siguiente forma:<br>  propiedad[i].subpropiedad[j]....subpropiedad[?]
	 * @param objetoAnalisis
	 * @param propiedad ie: <i>perfilesInternos[?]</i>
	 * @param posPropiedad  i.e.:<i>textos[?].texto != null</i>
	 * @param condicionExcepcion  i.e.:<i>perfilesExternos.size() > 0</i>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<EmpresaParametroDto> iterateListaDto(Object objetoAnalisis, String propiedad, String posPropiedad, String condicionExcepcion){
		log4j.debug("<iterateListaDto> Inicio___ ");
		List<EmpresaParametroDto> lsParamsDto = new ArrayList<EmpresaParametroDto>();
		if(condicionExcepcion==null){condicionExcepcion="";}
		try{
			String propiedadPura = propiedad.replace("[?]", "").replace("[0]", "");
			log4j.debug("<iterateListaDto> propiedad a buscar: " + propiedadPura );
			log4j.debug("<iterateListaDto> posPropiedad: " + posPropiedad );
			Object fieldContent = null;
			Field field =UtilsTCE.findUnderlying(objetoAnalisis.getClass(), propiedadPura );
			if(field==null){ /* propiedad no existe en el objeto */
				log4j.error("<iterateListaDto> ERROR, " + propiedadPura +" no existe en el objeto");
				String nwpropiedad = propiedad+"."+posPropiedad;
				condicionExcepcion = condicionExcepcion.replace(propiedad, nwpropiedad);
				EmpresaParametroDto dtoNuevo = new EmpresaParametroDto();
				dtoNuevo.setValor(nwpropiedad.replace("[?]", "["+0+"]"));
				dtoNuevo.setCondicion(condicionExcepcion);
				lsParamsDto.add(dtoNuevo);
			}else{
				log4j.debug("<iterateListaDto> Se encontro: " + field.getName() );
				field.setAccessible(true);
				fieldContent = field.get(objetoAnalisis);
				long counter = 0;				
				if(fieldContent!=null && (fieldContent instanceof List)){
					List<Object> lsContent = (List<Object>)fieldContent;
					String nwpropiedad = "";
					
					if(lsContent.isEmpty()){
						log4j.debug("<iterateListaDto> La lista de elementos en el campo es vacia ");
						nwpropiedad = propiedad+"."+posPropiedad;
						condicionExcepcion = condicionExcepcion.replace(propiedad, nwpropiedad);
						EmpresaParametroDto dtoNuevo = new EmpresaParametroDto();
						dtoNuevo.setValor(nwpropiedad.replace("[?]", "["+counter+"]").replace("[0]", "["+counter+"]"));
						dtoNuevo.setCondicion(condicionExcepcion);
						lsParamsDto.add(dtoNuevo);
					}else{
//						log4j.debug("<iterateListaDto> La lista no es vacia, se itera para verificar contenido");
						Iterator<Object> itContent = lsContent.iterator();						
						
						while(itContent.hasNext()){
							EmpresaParametroDto dtoNuevo = new EmpresaParametroDto();
							nwpropiedad = propiedad.replace("[?]", "["+counter+"]").replace("[0]", "["+counter+"]"); 
							log4j.debug("Modificando ccondicionExcepcion: " + condicionExcepcion + ", nwpropiedad: " + nwpropiedad );
							String nwCondicionExcepcion = condicionExcepcion.replace(propiedad, nwpropiedad);
//							condicionExcepcion = condicionExcepcion.replace(propiedad, nwpropiedad);
							
							log4j.debug("<iterateListaDto> nwpropiedad: " + nwpropiedad + ",  nwCondicionExcepcion: " + nwCondicionExcepcion);
							if(posPropiedad.indexOf("[?]")!=-1 || posPropiedad.indexOf("[0]")!=-1){
								String subPropiedad = posPropiedad.substring(0, posPropiedad.indexOf("."));
								String psPropiedadSub = posPropiedad.substring(posPropiedad.indexOf(".")+1,posPropiedad.length());
								log4j.debug("<iterateListaDto> subPropiedad: " + subPropiedad +", psPropiedadSub : " + psPropiedadSub);
								
								List<EmpresaParametroDto> lsSubList = iterateListaDto(itContent.next(), subPropiedad, psPropiedadSub, condicionExcepcion);
								
								if(lsSubList!=null && !lsSubList.isEmpty()){
									Iterator<EmpresaParametroDto> itSubList = lsSubList.iterator();
									while(itSubList.hasNext()){
										EmpresaParametroDto dtoTmp = itSubList.next();
										String addParam = nwpropiedad+"."+dtoTmp.getValor();
										dtoNuevo = new EmpresaParametroDto();										
										log4j.debug("agregando " + addParam );
										dtoNuevo.setValor(addParam);
										dtoNuevo.setCondicion(condicionExcepcion);
										lsParamsDto.add(dtoNuevo);
									}
								}
								else{
									log4j.debug("<iterateListaDto> NO hay elementos en " + nwpropiedad );//TODO evaluar respuesta
								}
							}else{//Fin del arreglo, se crea validador para la propiedad
								String addParam = nwpropiedad+"."+posPropiedad;
								dtoNuevo = new EmpresaParametroDto();
								dtoNuevo.setValor(addParam);
								dtoNuevo.setCondicion(nwCondicionExcepcion);
								lsParamsDto.add(dtoNuevo);
//								log4j.debug("lsParamsDto.add("+ addParam + ");" );//ByPass
								itContent.next();
							}
							counter++;
						}
					}
				}else {
					log4j.debug("<iterateListaDto> El campo " + field.getName() + " No es Lista, esta vacio o no contiene elementos");
					log4j.debug("fieldContent: " + fieldContent );
					log4j.debug("fieldContent!=null " + (fieldContent!=null)  + " (fieldContent instanceof List): " + (fieldContent instanceof List));
					
					//se crea validador con indices 0
					String nwpropiedad = propiedad+"."+posPropiedad;
							//propiedad.replace("[?]", "["+counter+"]"); 
					condicionExcepcion = condicionExcepcion.replace(propiedad, nwpropiedad);
					EmpresaParametroDto dtoNuevo = new EmpresaParametroDto();
					dtoNuevo.setValor(nwpropiedad.replace("[?]", "["+counter+"]").replace("[0]", "["+counter+"]"));
					dtoNuevo.setCondicion(condicionExcepcion);
					lsParamsDto.add(dtoNuevo);
				}
			}
		}catch (Exception e){
			log4j.error("<iterateListaDto> Error al procesar lista: ", e);
			e.printStackTrace();
		}
		return lsParamsDto;
	}
	
	/* ******************************************************************************************************************** */
	/*  ********************** PROCESO 2: Evaluar los EmpresaParametro Indexados vs el Objeto usando JexlContext  ********* */
	
	/**
	 * Evalua un objeto, en base a una lista de Condiciones (empresaParametro), ya con indices en caso
	 * de arreglos [i]
	 * @param lsCondicionales
	 * @param objectToCheck
	 * @return
	 * @throws Exception 
	 */
	private static List<?> evaluateObjectByEmpParam(List<EmpresaParametroDto> lsCondicionales, 
			Object objectToCheck ) throws Exception{
		List<CandidatoDto> lsErrorDetail = new ArrayList<CandidatoDto>();
		if(lsCondicionales!=null && !lsCondicionales.isEmpty()){
			JexlContext jc = Validador.prepareSet(objectToCheck);
			
			Iterator<EmpresaParametroDto> itCondiciones = lsCondicionales.iterator();
			String condicion = "", condicionExcluyente="";
			boolean excluyente = false; //False=se ejecuta condicion normal, TRUE= se excluye de validacion
			while(itCondiciones.hasNext()){
				EmpresaParametroDto condicionDto = itCondiciones.next();
				condicion = condicionDto.getValor();
				condicionExcluyente = condicionDto.getCondicion();
				log4j.debug("<evaluateObjectByEmpParam> condicion="+condicion+" condicionExcluyente="+condicionExcluyente);
				if(condicionExcluyente!=null && !condicionExcluyente.trim().equals("")){
					/*se cumple excluyente? */
					excluyente = Validador.checkCondition(condicionExcluyente,objectToCheck, jc);					
				}
				
				if(!excluyente){ /* */
					log4j.debug("<evaluateObjectByEmpParam> Validando [" + condicionDto.getIdEmpresaParametro() +"]: " + condicionDto.getValor() );
					if (!Validador.checkCondition(condicion,objectToCheck, jc)){//Si no se cumple
						log4j.debug("<evaluateObjectByEmpParam> Condición >>NO CUMPLIDA<< (" + condicion +"), registrando en arreglo de salida");
						lsErrorDetail.add(new CandidatoDto(UtilsTCE.firstExpElement(condicion),
														   condicionDto.getDescripcion() ));
					}
				}else{	log4j.debug("<evaluateObjectByEmpParam> La condicion Excluyente se cumple, no se verifica condicion, continuando la validación"); }
			}			
		}
		return lsErrorDetail;
	}
			
}
