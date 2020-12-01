package net.tce.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import net.tce.dto.DataConfDto;
import net.tce.dto.MensajeDto;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.log4j.Logger;
import com.google.gson.Gson;


/**
 * Clase con metodos estaticos que proporcionan utilidades para hacer validaciones  
 * @author 
 *
 */
public class Validador {
	public static final String ALFA= "a-zA-ZáéíóúÁÉÍÓÚñÑüÜ@&_.,;:()\\-\\s";
	public static final String ALFANUMPATT = "^[0-9".concat(ALFA).concat("]+$");
	public static final String ALFAPATT = "^[".concat(ALFA).concat("]+$");
	public static final String NUMERICDECPATT = "^(0|([1-9]\\d*))(\\.\\d+)?$"; //^[0-9]+(\\.\\d{1,15})?$ soporta 00045
	public static final String NUMERICDECNEGPATT = "^(0|([1-9]\\d*)|-([1-9]\\d*))(\\.\\d+)?$";
	public static final String MAILPATT4 = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
	public static final String PASSWORD = "^.{8,}$";

	//public static final String CURPPATT = "[a-zA-Z]{4,4}[0-9]{6,6}[a-zA-Z0-9]{8,8}$";
	//public static final String NAMEPATT = "[a-zA-Z\u00D1 ]+$";
	//public static final String RFCPATT = "^[A-Z]{4}[0-9]{6}[A-Z0-9]{3}$";
	//public static String urlValid = "/^(ht|f)tps?:\/\/\w+([\.\-\w]+)?\.([a-z]{2,3}|info|mobi|aero|asia|name)(:\d{2,5})?(\/)?((\/).+)?$/i"
	//Permite: .@jkjkj.com.mx.kl , se descarta
	//public static final String MAILPATT = "[a-zA-Z0-9_.-]+[@]{1,1}[a-zA-Z0-9_-]+[.]+[a-zA-Z0-9_.-]+$";
	//Permite: goyo.kkk@506.325.652.321.yuhj
	//public static final String MAILPATT2 = "^((\"[\\w-\\s]+\")|([\\w-]+(?:\\.[\\w-]+)*)|(\"[\\w-\\s]+\")([\\w-]+(?:\\.[\\w-]+)*))(@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$)|(@\\[?((25[0-5]\\.|2[0-4][0-9]\\.|1[0-9]{2}\\.|[0-9]{1,2}\\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\]?$)/i";
	// no permite: lll.kkk@jkjkj.com.mx.kl , se descarta
	//public static final String MAILPATT3 = "^[a-zA-Z0-9_-]{2,}@[a-zA-Z0-9_-]{2,}\\.[a-zA-Z]{2,4}(\\.[a-zA-Z]{2,4})?$";
	//public static final String ONECHARPATT = "[a-zA-Z]{1,1}$";
	//public static final String ONLYNUMERICPATT = "[0-9]+$";
	//public static final String YESNOPATT = "[NSYnsy]{1,1}";
	//public static final String UNOCEROPATT = "[0-1]{1,1}";

	static Logger log4j = Logger.getLogger("net.tce.util.Validador");
		
	
	

	/**
	 * Dada la lista de expresiones (booleanas) de propiedades, se analiza el objeto correspondiente y se asegura que se cumplan las expresiones
	 * @param lsFiltros, lista de expresiones a evaluar
	 * @param objetoFiltrar, objeto a analizar
	 * @return, cuando el objeto cumplió las expresiones regresa un mapa nulo,  de lo contrario un mapa de condiciones no cumplidas
	 * @throws Exception
	 */
	public static HashMap<String,String> checkExpressions(HashMap<String,String> mapFiltros, Object objectToCheck) 
						throws Exception{
		log4j.debug("<checkExpressions> Inicio ... ");
		HashMap<String,String> mapStringOut = new HashMap<String,String>();
		
		JexlContext jc = prepareSet(objectToCheck); 	//Validador.prepareSet(objectToCheck);

		if(mapFiltros.size() > 0){
			boolean todoCUmplido = true;
			String requiredExpression = null;
			String description = null;
			Iterator<String> itKeyFiltros= mapFiltros.keySet().iterator();
			StringBuilder sbBuilder = new StringBuilder();
			log4j.debug("<checkExpressions> evaluando " + mapFiltros.size() + " condiciones " );
			while(itKeyFiltros.hasNext()){
				requiredExpression = itKeyFiltros.next();
				description = mapFiltros.get(requiredExpression);
				sbBuilder.append("\n requiredExpression: ").append(requiredExpression)
							.append("\n description: ").append(description);
				
				
				if (Validador.checkCondition(requiredExpression,objectToCheck, jc)){
					//log4j.debug("<getRequiredAttributes> Expresion CUMPLIDA, continuando la validación"); 		
					sbBuilder.append("\n Res: ").append("cumplida").append("\n");
					continue;
				}else{
				//	log4j.debug("<getRequiredAttributes> Expresion NO CUMPLIDA, registrando en arreglo de salida"); 
					sbBuilder.append("\n Res: ").append("NO CUMPLIDA").append("\n");
					todoCUmplido = false;
				}
				mapStringOut.put(requiredExpression, description);
				
				
			}
			if(!todoCUmplido){
				sbBuilder.append("\n checkExpressions: ").append("HAY CONDICIONES NO CUMPLIDAS").append("\n");
				log4j.debug("<checkExpressions> Resultado Validador: \n " + sbBuilder.toString());
			}
			//log4j.debug("<getRequiredAttributes> Resultado Validador: \n " + sbBuilder.toString()); /* Descomentar para mostrar siempre*/
		}
		log4j.debug("<checkExpressions> Fin ... ");
		return mapStringOut;
	}

	/**
	 * Crea el mapa de descriptores de las propiedades que posteriormente seran utilizados en las expresiones  
	 * @param objectToCheck, objeto a analizar
	 * @return JexlContext
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public static JexlContext prepareSet(Object objectToCheck) throws Exception{		
		log4j.debug("<prepareSet> Inicio ... ");
		/* Crea el contexto y agrega datos */
        JexlContext jc = new MapContext();
    	PropertyDescriptor[] propertyDescriptor = PropertyUtils.getPropertyDescriptors(objectToCheck);    	
    	//log4j.debug("<prepareSet> propertyDescriptor.size :" + propertyDescriptor.length) ;
    	for (int i=0;i<propertyDescriptor.length;i++) {
    		//log4j.debug("<prepareSet> propertyDescriptor[i].getName()/getPropertyType :"+ propertyDescriptor[i].getName() + "/" + propertyDescriptor[i].getPropertyType());  
    		
    		//log4j.debug("%&$ val0r de la propiedad:"+ PropertyUtils.getNestedProperty(objectToCheck, propertyDescriptor[i].getName()));
			jc.set(propertyDescriptor[i].getName(), PropertyUtils.getNestedProperty(objectToCheck, propertyDescriptor[i].getName()));
			
    	}
		log4j.debug("<prepareSet> Fin ... ");
		return jc;
	}	

	/**
	 * Evalua una condicion en base a objetos y propiedades 
	 * @param condicion, Texto de la condicion a evaluar
	 * @param objetoFiltrar, objeto a analizar
	 * @param jc, Contexto Jexl con el mapa de propiedades del objeto
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean checkCondition(String condition, Object objectToCheck, JexlContext jc){		
		boolean result = true;
		if(condition != null){
			Expression exp = new JexlEngine().createExpression(condition);
			Object obEval = null;
			/*log4j.debug("<checkCondition> Antes de evaluar..."); */
			try{
				obEval = exp.evaluate(jc);
				/* log4j.debug("<checkCondition> Despues de evaluar..."); */
			}catch (Exception e){
				log4j.error("<checkCondition> Error al evaluar parametro: " + condition, e);
				log4j.error("<checkCondition> Parametros: objectToCheck: " + objectToCheck);
				log4j.error("<checkCondition> Parametros: JexlContext: " + jc);
			}
			
			if(obEval == null){
				log4j.debug("<checkCondition> Expresión nula");
				result = false;
			}else{
				result = new Boolean(obEval.toString());
			}
			//log4j.debug("<checkCondition> (Resultado de evaluar la expresion) result :" + result);
		}
		return result;
	}

	
	/**
	 * Filtra las propiedades de un objeto-dto basado criterios DataConf
	 * @param objectDataconf, objeto DataConf con politicas de filtrado
	 * @param objetoAFiltrar, objeto-dto que va a hacer filtrado con las politicas que contiene el objeto DataConf
	 * @return object
	 * @throws Exception
	 */
	public static Object filtrosDataConf(Object objectDataconf, Object objetoAFiltrarDto) throws Exception  {
		Field field =null;
		Object valueObject=null;
		String propiedad=null;
		boolean tuvoError=false;
		Object valorDataConf;
		try {
			log4j.debug("&&&& NUEVO ANALISIS PARA :"+objetoAFiltrarDto.getClass().getName());
			//Se inicializa la propiedad retorno con la propiedad messages
			field = UtilsTCE.findUnderlying(objetoAFiltrarDto.getClass(),"messages");
			field.setAccessible(true);
			valueObject=field.get(objetoAFiltrarDto);
			String retorno=(valueObject == null ? "":(String)valueObject);
			
			//Se analizan las propiedades del dataconf
			Field[] arrFieldDataConf = objectDataconf.getClass().getDeclaredFields();
			for (Field f : arrFieldDataConf) {
				f.setAccessible(true);
				//Se obtiene el nombre y el valor correspondiente(filtros Dataconf).  
				propiedad=f.getName();
				valorDataConf=f.get(objectDataconf);
				if(valorDataConf != null){
					//Se obtiene la propiedad del objeto a filtrar
					field = objetoAFiltrarDto.getClass().getDeclaredField(propiedad);
			  	  	field.setAccessible(true);
			  	  	valueObject = field.get(objetoAFiltrarDto);
			  	    if(valueObject != null){
						if(valueObject instanceof String ){
					  	    log4j.debug("%%% Filtro para getPropiedad="+propiedad+
					  	    			" valorDataConf="+valorDataConf+" valueObject="+valueObject);
							//El valor es un string
							if(!valueObject.equals("")){
								//Se obtiene la cadena data-conf
						  	    DataConfDto dataInfoDto= new Gson().fromJson((String)valorDataConf, DataConfDto.class);
								boolean cumpleRestriction=true;
								String restriccion=null; 
								//Si restriccion es nulo. Puede tomar cualquier valor
								if(dataInfoDto.getRestriction() == null){
									dataInfoDto.setRestriction(Constante.SIN_RESTRICTION_);
								}
								//se aplica restriction
								if(dataInfoDto.getRestriction().equals(Constante.RESTRICTION_ALFANUM) &&
								   !Pattern.matches(ALFANUMPATT,(String)valueObject)){
									cumpleRestriction=false;
									restriccion=Mensaje.FILTER_DATACONF_RESTRICTION_ALFANUM;
								}else if(dataInfoDto.getRestriction().equals(Constante.RESTRICTION_ALFA) &&
										 !Pattern.matches(ALFAPATT,(String)valueObject)){
									cumpleRestriction=false;
									restriccion=Mensaje.FILTER_DATACONF_RESTRICTION_ALFA;
								}else if(dataInfoDto.getRestriction().equals(Constante.RESTRICTION_NUMBERS) &&
										 !Pattern.matches(NUMERICDECPATT,(String)valueObject)){
									cumpleRestriction=false;
									restriccion=Mensaje.FILTER_DATACONF_RESTRICTION_NUM;
								}else if(dataInfoDto.getRestriction().equals(Constante.RESTRICTION_NEGATIVES) &&
										 !Pattern.matches(NUMERICDECNEGPATT,(String)valueObject)){
									cumpleRestriction=false;
									restriccion=Mensaje.FILTER_DATACONF_RESTRICTION_NUM;
								}
								
								//no cumple la restriccion
								if(!cumpleRestriction){
									retorno=UtilsTCE.getJsonMessageGson(retorno, new MensajeDto(propiedad,
											   							(String)valueObject,Mensaje.SERVICE_CODE_006_5,
																		Mensaje.SERVICE_TYPE_ERROR,
																		restriccion));
									tuvoError=true;
								}
								//Para numeros
								if(!tuvoError  && (dataInfoDto.getRestriction().equals(Constante.RESTRICTION_NUMBERS) ||
								   dataInfoDto.getRestriction().equals(Constante.RESTRICTION_NEGATIVES))){
									StringTokenizer value;
									//Se aplica precisionScale
									if(dataInfoDto.getPrecisionScale() != null){
										StringTokenizer stParam=new StringTokenizer(dataInfoDto.getPrecisionScale(),":");
										Long precision=Long.parseLong(stParam.nextToken());
										Long decimal=Long.parseLong(stParam.nextToken());
										boolean correcto=true;
										if(((String)valueObject).startsWith("-")){
											valueObject=((String)valueObject).substring(1, ((String)valueObject).length());
										}
										//es solo un entero
										if(((String)valueObject).indexOf(".") == -1){
											if(((String)valueObject).length() > precision.longValue()){
												correcto=false;
											}	
										}else{
											value=new StringTokenizer((String)valueObject,".");
											String valueEntero=value.nextToken();
											String valuedecimal=value.nextToken();
											if(valueEntero.length() > (precision.longValue() - decimal.longValue())){
												correcto=false;
											}else if(valuedecimal.length() > decimal.longValue()){
												correcto=false;
											}
										}
										//cumple precision
										if(!correcto){
											retorno=UtilsTCE.getJsonMessageGson(retorno, new MensajeDto(propiedad,
													   (String)valueObject,Mensaje.SERVICE_CODE_006_2,
														Mensaje.SERVICE_TYPE_ERROR,
														new StringBuilder(Mensaje.FILTER_DATACONF_PRECISIONSCALE).
														append(dataInfoDto.getPrecisionScale()).append(")").toString()));
											tuvoError=true;
										}
									}
									if(!tuvoError){
										//se aplica maxValue y minValue
										if(dataInfoDto.getMinValue() != null && dataInfoDto.getMaxValue() == null){
											if((Long.valueOf((String)valueObject)).longValue() < dataInfoDto.getMinValue()){
												retorno=UtilsTCE.getJsonMessageGson(retorno, new MensajeDto(propiedad,
							   							(String)valueObject,Mensaje.SERVICE_CODE_006_3,
														Mensaje.SERVICE_TYPE_ERROR,
														new StringBuilder(Mensaje.FILTER_DATACONF_RANGE_MIN).
														append(dataInfoDto.getMinValue().toString()).toString()));
												tuvoError=true;
											}
										}else if(dataInfoDto.getMinValue() == null && dataInfoDto.getMaxValue() != null){
											if((Long.valueOf((String)valueObject)).longValue() > dataInfoDto.getMaxValue()){
												retorno=UtilsTCE.getJsonMessageGson(retorno, new MensajeDto(propiedad,
							   							(String)valueObject,Mensaje.SERVICE_CODE_006_3,
														Mensaje.SERVICE_TYPE_ERROR,
														new StringBuilder(Mensaje.FILTER_DATACONF_RANGE_MAX).
														append(dataInfoDto.getMaxValue().toString()).toString()));
												tuvoError=true;
											}
										}else if(dataInfoDto.getMinValue() != null && dataInfoDto.getMaxValue() != null){
											if((Long.valueOf((String)valueObject)).longValue() > dataInfoDto.getMaxValue() ||
											   (Long.valueOf((String)valueObject)).longValue() < dataInfoDto.getMinValue()	){
												retorno=UtilsTCE.getJsonMessageGson(retorno, new MensajeDto(propiedad,
							   							(String)valueObject,Mensaje.SERVICE_CODE_006_3,
														Mensaje.SERVICE_TYPE_ERROR,
														new StringBuilder(Mensaje.FILTER_DATACONF_RANGE_MIN).
														append(dataInfoDto.getMinValue().toString()).
														append(" y menor o igual a ").
														append(dataInfoDto.getMaxValue().toString()).toString()));
												tuvoError=true;
											}
										}
								    }
									//se aplica valueSet
									if(!tuvoError && dataInfoDto.getValueSet() != null){
										boolean seEncontro=false;
										value=new StringTokenizer(dataInfoDto.getValueSet(),",");
										while(value.hasMoreTokens()){
											if(((String)valueObject).equals(value.nextToken())){
												seEncontro=true;
												break;
											}
										}
										//esta en la lista
										if(!seEncontro){
											retorno=UtilsTCE.getJsonMessageGson(retorno, new MensajeDto(propiedad,
						   							(String)valueObject,Mensaje.SERVICE_CODE_006_4,
													Mensaje.SERVICE_TYPE_ERROR,
													new StringBuilder(Mensaje.FILTER_DATACONF_VALUESET).
													append(dataInfoDto.getValueSet()).toString()));
											tuvoError=true;
										}
									}
								}else{
									if(!tuvoError){
										//alfanum o alfa
										//se aplica maxLength y minLength
										if(dataInfoDto.getMinLength() != null && dataInfoDto.getMaxLength() == null){
											if(((String)valueObject).length() < dataInfoDto.getMinLength().longValue()){
												retorno=UtilsTCE.getJsonMessageGson(retorno, new MensajeDto(propiedad,
							   							(String)valueObject,Mensaje.SERVICE_CODE_006_3,
														Mensaje.SERVICE_TYPE_ERROR,
														new StringBuilder(Mensaje.FILTER_DATACONF_RANGE_MIN).
														append(dataInfoDto.getMinLength().toString()).toString()));
												tuvoError=true;
											}
										}else if(dataInfoDto.getMinLength() == null && dataInfoDto.getMaxLength() != null){
											if(((String)valueObject).length() > dataInfoDto.getMaxLength().longValue()){
												retorno=UtilsTCE.getJsonMessageGson(retorno, new MensajeDto(propiedad,
							   							(String)valueObject,Mensaje.SERVICE_CODE_006_3,
														Mensaje.SERVICE_TYPE_ERROR,
														new StringBuilder(Mensaje.FILTER_DATACONF_RANGE_MAX).
														append(dataInfoDto.getMaxLength().toString()).toString()));
												tuvoError=true;
											}
										}else if(dataInfoDto.getMinLength() != null && dataInfoDto.getMaxLength() != null){
											if(((String)valueObject).length() > dataInfoDto.getMaxLength().longValue() ||
											   ((String)valueObject).length() < dataInfoDto.getMinLength().longValue()	){
												retorno=UtilsTCE.getJsonMessageGson(retorno, new MensajeDto(propiedad,
							   							(String)valueObject,Mensaje.SERVICE_CODE_006_3,
														Mensaje.SERVICE_TYPE_ERROR,
														new StringBuilder(Mensaje.FILTER_DATACONF_RANGE_MIN).
														append(dataInfoDto.getMinLength().toString()).
														append(" y menor o igual a ").
														append(dataInfoDto.getMaxLength().toString()).toString()));
												tuvoError=true;
											}
										}
									}
									//se aplica pattern
									if(!tuvoError && dataInfoDto.getPattern() != null){
										//Para email
										if(dataInfoDto.getPattern().equals(Constante.PATERRN_EMAIL)){
											if(!Pattern.matches(MAILPATT4,(String)valueObject)){
												retorno=UtilsTCE.getJsonMessageGson(retorno, new MensajeDto(propiedad,
																				   (String)valueObject,Mensaje.SERVICE_CODE_006_5,
																					Mensaje.SERVICE_TYPE_ERROR,
																					Mensaje.FILTER_DATACONF_PATTERN_EMAIL));
												tuvoError=true;
											 }
										}
										//Para password
										if(dataInfoDto.getPattern().equals(Constante.PATERRN_PASSWORD)){
											if(!Pattern.matches(PASSWORD,(String)valueObject)){
												retorno=UtilsTCE.getJsonMessageGson(retorno, new MensajeDto(propiedad,
																				   (String)valueObject,Mensaje.SERVICE_CODE_006_5,
																					Mensaje.SERVICE_TYPE_ERROR,
																					Mensaje.FILTER_DATACONF_PATTERN_PWD));
												tuvoError=true;
											}
										}
									}
								}
							}
							//si tuvo un error el valor de la propiedad, se asigna un nulo
							if(tuvoError){
							   field.set(objetoAFiltrarDto, null);
							   tuvoError=false;
							}
					   }
				   }
				}
			}
			log4j.debug("%%% retorno="+retorno);
  			//Se asigna el valor del retorno a la propiedad: errores
  			if(!retorno.equals("")){
  				field=Class.forName(objetoAFiltrarDto.getClass().getSuperclass().getName()).
  									getDeclaredField(propiedad="messages");
  				field.setAccessible(true);
  				field.set(objetoAFiltrarDto, retorno);
  			}
		} catch (NoSuchFieldException e) {
			throw new NoSuchFieldException(new StringBuilder("La propiedad data-conf: ").append(propiedad).
					append(" , no se encuentra en el objeto: ").
					append(objetoAFiltrarDto.getClass().getName()).toString());
		}
		return objetoAFiltrarDto;
	}
	
	
	/**
	 * Dada la lista de propiedades requeridas, se analiza una lista de objetos  y se asegura que no tengan nulos
	 * @param contextoList, es el nombre del objeto padre
	 * @param lsListaFiltros , lista de propiedades requeridas
	 * @param itObject, lista a iterar de los objetos a analizar
	 * @return, cuando el objeto no tiene nulos regresa un null de lo contrario un message
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws Exception
	 */
	//public static String listaFiltros(String contextoList, List<String> lsListaFiltros, Iterator<Object> itObject)
	/*public static String listaFiltros(List<String> lsListaFiltros, Iterator<Object> itObject)
			throws SecurityException, IllegalArgumentException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
		String retorno=null;	
		while(itObject.hasNext()){
				//retorno=filtros(contextoList.substring(2, 3).toLowerCase().concat(contextoList.substring(3)),
			retorno=filtros(
						lsListaFiltros,itObject.next());
				//si hay un error
 				 if(retorno != null) {
 					 break;
 				 }
			}
		return retorno;
	}*/
	
 /**
  * 
  * @param bigDecimal
  * @return
  */
   static int getNumberOfDecimalPlaces(BigDecimal bigDecimal) {
        String string = bigDecimal.stripTrailingZeros().toPlainString();
        int index = string.indexOf(".");
        return index < 0 ? 0 : string.length() - index - 1;
    }

   /**
    * Se analiza si la estructura del pasword cumple con la expresion regular correspondiente
    * @param pwd, es el password
    * @return
    * 		true cumple
    * 		false no cumple
    */
   public static boolean passwordValido(String pwd){
	   return !Pattern.matches(PASSWORD,pwd);
   }
  
}
