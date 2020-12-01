package net.tce.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.tce.app.exception.SystemTCEException;
import net.tce.dto.CertificacionDto;
import net.tce.dto.DataConfDto;
import net.tce.dto.MensajeDto;
import org.apache.log4j.Logger;
import com.google.gson.Gson;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import uk.ac.shef.wit.simmetrics.similaritymetrics.JaroWinkler;
import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class UtilsTCE {
	static Logger log4j = Logger.getLogger("UtilsTCE".getClass());


	/**
	 * Regresa una cadena aleatoria 
	 * @param longitud de la cadena
	 * @return
	 */
	public static String getCadenaAlfanumAleatoria (int longitud){
		String cadenaAleatoria = "";
		int i = 0;
		while ( i < longitud){
			char c = (char)new Random().nextInt(255);
			if ( (c >= '0' && c <='9') || (c >='A' && c <='Z') ){
				cadenaAleatoria += c;
				i ++;
			}
		}
		return cadenaAleatoria;
	}
	

	/**
	 * Este m�todo se encarga de cambiar la configuración de log4j en runtime
	 * @param nivel  String que indica el nivel de debug
	 */
	 /*public static void cambiarNivelLog4j(String nivel, String path){
	      try {	    	
	    	  Properties props = new Properties();
	          InputStream configStream = UtilsTCE.class.getResourceAsStream("/log4j.properties");
	          props.load(configStream);
	          configStream.close();  	          
	 	      props.setProperty("log4j.appender.FILE.Threshold",nivel);
	 	      props.setProperty("log4j.appender.FILE.File",path);
	 	      LogManager.resetConfiguration();
	 	      PropertyConfigurator.configure(props);	 	    
	      } catch(Exception e) {
	    	 log4j.error("ERROR AL CAMBIAR EL NIVEL DE VISTA EN LOG4J");
	         e.printStackTrace();
	      }	    	     
	   }*/
	 
	 /**
	  * Borra archivos en forma recursiva, de una carpeta dada
	  * @param directorio correspondiente
	  */
	  public static void borrarDirectorio (File directorio){
          File[] ficheros = directorio.listFiles();
          for (int x=0;x<ficheros.length;x++){
                  if (ficheros[x].isDirectory()) {
                          borrarDirectorio(ficheros[x]);
                  }
                  ficheros[x].delete();
          }              
      }
	  
	 

	 
	 
	 /**
	 * Obtiene una cadena con formato Json en base a los parametros ingresados
	 * @param code
	 * @param type
	 * @param eMessage
	 * @return
	 */
	public static String getJsonMessageGson(String errores,Object object ) {
		StringBuilder sb=new StringBuilder("[");
		return (errores == null || errores.equals("")) ? 
			 		sb.append(new Gson().toJson(object)).append("]").toString():
			 		sb.append(new Gson().toJson(object)).append(",").
			 		append(errores.substring(1, errores.length())).toString();
	}
	
	
	 /**
	  * Regresa un json-dataconf de anio, con las claves minLength y maxLength
	  * @param dataconfAnio, json data-conf
	  * @param minMax, cadena que contiene los criterios min y max
	  * @return json
	  */
	 public static String getDataConfAnio(String dataconfAnio,String minMax){
		Gson gson=new Gson();
		DataConfDto dataConfDto=gson.fromJson(dataconfAnio, DataConfDto.class);
		StringTokenizer st=new StringTokenizer(minMax, ",");
		int anio =DateUtily.getCalendar().get(Calendar.YEAR);
		//importa el orden 
		dataConfDto.setMaxLength(new Long(anio - Integer.valueOf(st.nextToken()).intValue()));
		dataConfDto.setMinLength(new Long(anio - Integer.valueOf(st.nextToken()).intValue()));
		return gson.toJson(dataConfDto);
	 }
	 
	/**
	 * Returns altern element if first one is null, like NVL pl/sql function
	 * @param a,b (parameterized types)
	 * @return Not Null a||b
	 */
	public static <T> T nvl(T a, T b) {
	 return (a == null)?b:a;
	}	 
	 
	/**
	 * Asigna el anio base
	 * @param anioDataConf, la cadena dataconf
	 * @param inicio, true si se quiere MinLength o false si se quiere MaxLength
	 * @param nacimiento, si el calculo es para anio de nacimiento
	 * @param anioBase, si no es anio de nacimiento, este dato tiene un anio default
	 * @return el anio
	 */
	public static int getAnioBaseToDataConf(String anioDataConf, boolean inicio, boolean nacimiento,Integer anioBase){
		int resp;
		DataConfDto dataConfDto=new Gson().fromJson(anioDataConf, DataConfDto.class);
		if(inicio){
			if(dataConfDto.getMinLength() != null)
				resp= dataConfDto.getMinLength().intValue();
			else{
				if(nacimiento)
					resp= Constante.ANIO_NACIMIENTO_BASE_MIN;
				else
					resp= anioBase;
			}
		}else{
			if(dataConfDto.getMaxLength() != null)
				resp= dataConfDto.getMaxLength().intValue();
			else{
				if(nacimiento)
					resp= Constante.ANIO_NACIMIENTO_BASE_MAX;
				else
					resp= anioBase;
			}
		}
		return resp;
	}
	
	/**
	 * Returns absolute distance between two cartesian pointsS
	 * @param x1,y1,x2,y2
	 * @return double
	 */
	public static double distPlain(double x1, double y1, double x2, double y2) {
        return Math.sqrt(
            (x1 - x2) *  (x1 - x2) + 
            (y1 - y2) *  (y1 - y2)
        );
    }	

	/**
	 * Returns absolute distance between two geographical points latitude and longitude
	 * @param lat1,lng1,lat2,lng2
	 * @return double
	 */
	public static double distGeo(double lat1, double lng1, double lat2, double lng2) {
		//ver http://www.movable-type.co.uk/scripts/latlong.html
	    double a = Math.pow(Math.sin((double)(Math.toRadians(lat2-lat1) / 2)), 2) + 
	    		   (Math.pow(Math.sin((double)(Math.toRadians(lng2-lng1) / 2)), 2)
	            * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)));
	    return (double)(Constante.EARTH_RADIUS * (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a))));
	   }	
    
	/**
	 * Returns distribution range classification according to provided method
	 * @param <T>
	 * @param List<T> completeObjectList,String valueAttribute, String rangeAttribute,  int statMethod
	 * @return <T> List<T>
	 */
    public static <T> List<T> getRankedDistribution(List<T> completeObjectList,String valueAttribute, 
    												String rangeAttribute,  int statMethod, boolean reverse)
    				throws Exception{
	    log4j.debug("<getRankedDistribution> Start...");
	    log4j.debug("<getRankedDistribution> Method/valueAttribute/rangeAttribute :" + statMethod + "/" + valueAttribute + "/" + rangeAttribute);
	    log4j.debug("<getRankedDistribution> population.size :" + completeObjectList.size());

	    log4j.debug("<getRankedDistribution> Declaring objects");
	    Object object ;
		Field field,  fieldStatus;
		Object value, valueStatus;
		Short range = 1;

	    log4j.debug("<getRankedDistribution> Declaring stats objects");
		/* Stats */
		DescriptiveStatistics stats = new DescriptiveStatistics();
		
	    log4j.debug("<getRankedDistribution> Getting attributes sublist");
		/* IMPROVEMENT : Best way to get a sublist from a list of attributes of one object list */
	    Iterator<T> itListValues = completeObjectList.iterator();
	    while(itListValues.hasNext()){
	    	object = itListValues.next();
			field =Class.forName(object.getClass().getName()).getDeclaredField(valueAttribute);		
			field.setAccessible(true);
			value = field.get(object);
			
			fieldStatus=Class.forName(object.getClass().getName()).getDeclaredField(Constante.FILTRO_STATUS);
			fieldStatus.setAccessible(true);
			valueStatus=fieldStatus.get(object);
			
		    log4j.debug("<getRankedDistribution> value :" + value+" valueStatus="+valueStatus);
		    log4j.debug("<getRankedDistribution> object.getClass() :" + object.getClass());
			if(value != null && ((Byte)valueStatus).byteValue() == Constante.ESTATUS_CANDIDATO_ACEPTADO){
			    log4j.debug("<getRankedDistribution> value_type :" + value.getClass());
				if(value instanceof Double){
					stats.addValue((Double) value);	  
				}else if(value instanceof Integer){
					 log4j.debug("<getRankedDistribution> value_integer_1 :" + ((Integer) value).doubleValue());
					stats.addValue( ((Integer) value).doubleValue());	
				}else
					if(value instanceof BigDecimal){
						stats.addValue( ((BigDecimal) value).doubleValue() );	    							
					}
			}
	    }
	    
	    log4j.debug("<getRankedDistribution> Continuing process with sublist...size :" + stats.getN());
		Double currentValue = null;
		double maxRange1 = 0;
		double minRange2 = 0, maxRange2 = 0;
		double minRange3 = 0, maxRange3 = 0;
		double minRange4 = 0;

	    log4j.debug("<getRankedDistribution> mean/std/popVariance :" + stats.getMean() + "/" + stats.getStandardDeviation() + "/" + stats.getPopulationVariance());
	    log4j.debug("<getRankedDistribution> getPercentile 25/50/75 :" + stats.getPercentile(25) + "/" + stats.getPercentile(50) + "/" + stats.getPercentile(75));

	    /* For both methods RANGE: 1 = Lower values ... 4 = Greatest values */
	    if(statMethod == Constante.STAT_SD_METHOD){
		    /* Method 1 Standard Deviation for Normal distributions 
		     RANGE 4 = [AritmeticMean + (1)StandardDeviation, MAX-LIMIT]
		     RANGE 3 = [AritmeticMean, AritmeticMean + (1)StandardDeviation
		     RANGE 2 = [AritmeticMean - (1)StandardDeviation, AritmeticMean)
		     RANGE 1 = [MIN-LIMIT, AritmeticMean - (1)StandardDeviation) 
		    */
		    minRange4 = stats.getMean() + stats.getStandardDeviation();
		    minRange3 = stats.getMean();
		    maxRange3 = stats.getMean() + stats.getStandardDeviation();
		    minRange2 = stats.getMean() - stats.getStandardDeviation();
		    maxRange2 = stats.getMean();
		    maxRange1 = stats.getMean() - stats.getStandardDeviation();
	    }
	    
	    if(statMethod == Constante.STAT_QUARTILE_METHOD){
		    /* Method 2 Percentiles (Quartiles) for non-Normal distributions
		     RANGE 4 = [Q3, MAX-LIMIT]
		     RANGE 3 = [Q2, Q3)
		     RANGE 2 = [Q1, Q2)
		     RANGE 1 = [MIN-LIMIT, Q1) 
		    */
		    minRange4 = stats.getPercentile(75);
		    minRange3 = stats.getPercentile(50);
		    maxRange3 = stats.getPercentile(75);
		    minRange2 = stats.getPercentile(25);
		    maxRange2 = stats.getPercentile(50);
		    maxRange1 = stats.getPercentile(25);
	    }	    
		
		
	    log4j.debug("<getRankedDistribution> Assigning stat rank to every value");
	    Iterator<T> itList = completeObjectList.iterator();
	    while(itList.hasNext()){
	    	object = itList.next();

			field =Class.forName(object.getClass().getName()).getDeclaredField(valueAttribute);
			field.setAccessible(true);
			value = field.get(object);
			
			fieldStatus=Class.forName(object.getClass().getName()).getDeclaredField(Constante.FILTRO_STATUS);
			fieldStatus.setAccessible(true);
			valueStatus=fieldStatus.get(object);
			
			if(value != null && ((Byte)valueStatus).byteValue() == Constante.ESTATUS_CANDIDATO_ACEPTADO){
				if(value instanceof Double){
				    currentValue = (Double) value;	
				}else if(value instanceof Integer){
					 log4j.debug("<getRankedDistribution> value_integer_2 :" + ((Integer) value).doubleValue());
					 currentValue= ((Integer) value).doubleValue();	
				}else
					if(value instanceof BigDecimal){
						currentValue =  ((BigDecimal) value).doubleValue();	    							
					}

				if(currentValue >= minRange4){
			    	range = 4;
			    }else
			    if(currentValue >= minRange3 && currentValue < maxRange3){
			    	range = 3;
			    }else
			    if(currentValue >= minRange2 && currentValue < maxRange2){
			    	range = 2;
			    }else
			    if(currentValue < maxRange1){
			    	range = 1;
			    }
				log4j.debug("<getRankedDistribution> currentValue/range :" + currentValue + "/" + range);
				
				//se aplica la reversa de los rangos
				if(reverse){
					switch ( range ) {
				      case 1:
				    	  	range = 4;
				           break;
				      case 2:
				    	  	range = 3;
				           break;
				      case 3:
				    	   range = 2;
				           break;
				      case 4:
				    	   range = 1;
				           break;
				      }
				}
				
				field =Class.forName(object.getClass().getName()).getDeclaredField(rangeAttribute);
				field.setAccessible(true);
				field.set(object, range);
			}	    	
	    }
	    
		log4j.debug("<getRankedDistribution> End...");
        return completeObjectList;
	}	
   
	
	/**
	 * Redondea un numero decimal de tipo double
	 * @param numero donde se aplica el redondeo
	 * @param digitos, numero de digitos a redondear
	 * @param roundingMode, modo de redondeo
	 * @return un numero redondeado
	 */
	public static double redondear(double numero, int digitos, RoundingMode roundingMode) {
		 	return new BigDecimal(String.valueOf(numero)).
						setScale(digitos,roundingMode).doubleValue();
	}
	
	/**
	 * Redondea un numero decimal de tipo BigDecimal
	 * @param numero donde se aplica el redondeo
	 * @param digitos, numero de digitos a redondear
	 * @param roundingMode, modo de redondeo
	 * @return un numero redondeado
	 */
	public static BigDecimal redondear(BigDecimal numero, int digitos, RoundingMode roundingMode) {
		 	return numero.setScale(digitos,roundingMode);
	}
	
	
	/**
	 * Filtra un texto en contexto gramatical 
	 * @param texto el cual se va a filtrar
	 * @return
	 */
	public static String filterGramaText(String texto){
		return removeStopWords(replaceEspecialChars(" ".concat(texto.toLowerCase()).concat(" ")));
	}
	
	/**
	 * Se reemplazan caracteres específicos del token
	 * @param token
	 * @return el token modificado
	 */
	public static String replaceEspecialChars(String token){
		return token.replaceAll(Constante.EXP_REG_A,"a").
					replaceAll(Constante.EXP_REG_E,"e").
					replaceAll(Constante.EXP_REG_I,"i").
					replaceAll(Constante.EXP_REG_O,"o").
					replaceAll(Constante.EXP_REG_U,"u");
	}
	
	/**
	 * Se eliminan palabras específicas del token
	 * @param token
	 * @return el token modificado
	 */
	public static String removeStopWords(String token){
		return token.replaceAll(Constante.EXP_REG_CARACTERES," ").
					replaceAll(",", " , ").
					replaceAll(";", " ; ").
					replaceAll(Constante.EXP_REG_CONJUNCION," ").
					replaceAll(Constante.EXP_REG_ADVERBIO," ").
					replaceAll(Constante.EXP_REG_ARTICULOS," ").
					replaceAll(Constante.EXP_REG_PREPOSICIONES," ").
					replaceAll(" por "," ").
					replaceAll(Constante.EXP_REG_PRONOMBRES," ").
					replaceAll(Constante.EXP_REG_ESPACIO," ");
	}
	
	/**
	 * 
	 * @param numero
	 * @param base
	 * @return
	 */
	public static double obtenerModulo(double numero,int base){
		double resp=numero/base;
		if(resp < 0){
			return resp;
		}else{
			return obtenerModulo(resp,base);
		}
	}
	
	/**
	 * Dado un numero se obtiene la base dependiendo del divisor
	 * @param numero, el cual se obtiene la  base
	 * @param divisor, se aplica al numero para obtener la base
	 * @return p.e.
	 * 			numero=256
	 * 			divisor=10 --> base=100		
	 */
	public static double obtenerBase(double numero,int divisor){
		int cont=0;
		 do {
			 numero=numero/divisor;
			 cont++;
			 } while (numero > 1);
		return  Math.pow(divisor,cont-1);
	}
	
	/**
	 * Regresa una cadena aleatoria dado max y min tamaño de esta
	 * @param longitud de la cadena
	 * @return
	 */
	public static String getCadenaAlfanumAleatoria (int longMin, int longMax){
		String cadenaAleatoria = "";
		int i = 0;
		int numRandom=0;
		//Encontrar un numero entre longMin y longMax para definir el tamaño de la cadena
		do{
			numRandom=new Random().nextInt(longMax);
		}while( longMin > numRandom );
		//Despues de obtener el tamaño de la cadena, se consigue la correspondiente
		while ( i < numRandom){
			char c = (char)new Random().nextInt(255);
			if ( (c >= '0' && c <='9') || (c >='A' && c <='Z') ){
				cadenaAleatoria += c;
				i ++;
			}
		}
		return cadenaAleatoria;
	}
	
	/**
	 * Fuerza un retardo
	 * @param ms Duración en segundos
	 * @author Osy
	 * @throws InterruptedException 
	 */
	public static void forceDelay(int ms) throws Exception{
		Thread.sleep(ms);
	}
	
	/**
	 * 
	 * @param compareInicio
	 * @param compareFin
	 * @return
	 */
	public static String mensajeFechas(int compareInicio, int compareFin,  Object object) throws Exception{
		String salida=null;
		int mensajes=0;
		Field field =null;
		
		//Fecha fin es incorrecta				
		if(compareInicio == 0 && compareFin != 0){
			 mensajes=1;
		//Fecha inicio es incorrecta	
		}else if(compareInicio != 0 && compareFin == 0){
			 mensajes=2;
		}
		
		if(mensajes == 0 || mensajes == 2){	 	
			field = Class.forName(object.getClass().getName()).getDeclaredField("diaInicio");
			field.setAccessible(true);
			salida=UtilsTCE.getJsonMessageGson(salida, 
				  	new MensajeDto("diaInicio",(String)field.get(object),
				  	Mensaje.SERVICE_CODE_006_6,Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.FILTER_DATACONF_DATAVALIDATION_COMPARE_F_INI)); 
			field = Class.forName(object.getClass().getName()).getDeclaredField("mesInicio");
			field.setAccessible(true);
			salida=UtilsTCE.getJsonMessageGson(salida, 
				  	new MensajeDto("mesInicio",(String)field.get(object),
				  	Mensaje.SERVICE_CODE_006_6,Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.FILTER_DATACONF_DATAVALIDATION_COMPARE_F_INI));
			field = Class.forName(object.getClass().getName()).getDeclaredField("anioInicio");
			field.setAccessible(true);
			salida=UtilsTCE.getJsonMessageGson(salida, 
				  	new MensajeDto("anioInicio",(String)field.get(object),
				  	Mensaje.SERVICE_CODE_006_6,Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.FILTER_DATACONF_DATAVALIDATION_COMPARE_F_INI));
		} 
		if(mensajes == 1){	 
			field = Class.forName(object.getClass().getName()).getDeclaredField("diaFin");
			field.setAccessible(true);
			salida=UtilsTCE.getJsonMessageGson(salida, 
				  	new MensajeDto("diaFin",(String)field.get(object),
				  	Mensaje.SERVICE_CODE_006_6,Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.FILTER_DATACONF_DATAVALIDATION_COMPARE_F_FIN)); 
		 
			field = Class.forName(object.getClass().getName()).getDeclaredField("mesFin");
			field.setAccessible(true);
			salida=UtilsTCE.getJsonMessageGson(salida, 
				  	new MensajeDto("mesFin",(String)field.get(object),
				  	Mensaje.SERVICE_CODE_006_6,Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.FILTER_DATACONF_DATAVALIDATION_COMPARE_F_FIN));
			field = Class.forName(object.getClass().getName()).getDeclaredField("anioFin");
			field.setAccessible(true);
			salida=UtilsTCE.getJsonMessageGson(salida, 
				  	new MensajeDto("anioFin",(String)field.get(object),
				  	Mensaje.SERVICE_CODE_006_6,Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.FILTER_DATACONF_DATAVALIDATION_COMPARE_F_FIN));
		} 
		return salida;
	}


	
	/**
	 * Valida que el parametro sea un tipo entero positivo (ID) valido, por medio de expresion regular
	 * @param stId
	 * @return true si es valido (numerico)
	 */
	public static boolean isValidId(String stId){
		if(stId != null){
			Pattern pattern = Pattern.compile(".*[^0-9].*"); //original
			return !pattern.matcher(stId).matches();
		}
		return false;
	}
	/**
	 * Valida si la cadena parametro recibida es un RFC valido, determinando si es persona o Empresa en tipoRfc
	 * @param rfc Cadena-Rfc a validar
	 * @param tipoRfc 1 es Empresa, cualquier otro caso es persona
	 * @return
	 */
	public static boolean isValidRfc(String rfc, int tipoRfc){
		if(rfc==null || rfc.trim().equals("")){
			return false;
		}
		
		if(tipoRfc==1){
			return Pattern.matches(Constante.EXPRESION_REGULAR_RFC_EMPRESA, rfc.toUpperCase());
		}else{
			return Pattern.matches(Constante.EXPRESION_REGULAR_RFC_PERSONA, rfc.toUpperCase());
		}
	}
	
	/**
	 * Valida por medio de apache Commons si la cadena es numerica
	 * @param stNumber
	 * @return
	 */
	public static boolean isNumeric(String stNumber){
		return NumberUtils.isNumber(stNumber);
	}
	
	/**
	 * Valida por medio de apache Commons si la cadena es un numero positivo
	 * true si es Long Positivo
	 * @param stNumber
	 * @return
	 */
	public static boolean isPositiveLong(String stNumber){
		boolean res = false;
		
		if(isNumeric(stNumber)){
			Long longeaded = new Long(stNumber);
			if(longeaded>0){
				res = true;
			}
		}
		return res;
	}
	
	/**
	 * Valida por medio de apache Commons si la cadena es un numero positivo
	 * true si es Long Positivo
	 * @param stNumber
	 * @return
	 */
	public static boolean isPositiveAndCero(String stNumber){
		boolean res = false;
		
		if(isNumeric(stNumber)){
			Long longeaded = new Long(stNumber);
			if(longeaded >= 0){
				res = true;
			}
		}
		return res;
	}
	
	/**
	 * Dado una lista de tokens se crea una cadena con separadores
	 * @param lsString, lista de tokens
	 * @param separator, 
	 * @param numero, true es numero
	 * 				  false no es numero
	 * @return la cadena
	 */
	public static String getStringByList(List<String> lsString, char separator, boolean numero){
		StringBuilder res=null;
		if(lsString != null && lsString.size() > 0){
			res=new StringBuilder();
			Iterator<String> itString= lsString.iterator();
			while(itString.hasNext()){
				if(numero)
					res.append(itString.next()).append(separator);
				else
					res.append("'").append(itString.next()).append("'").append(separator);
			}
		}
		return (res != null ? res.substring(0,(res.length() - 1)).toString():null);
	}
	
//	/**
//	 * Procesa datos de entrada para generar el Id usado en Solr para el Documento respectivo
//	 * @param idEntidad
//	 * @param tipoEntidad
//	 * @return
//	 */
//	public static String obtieneIdSolr(String idEntidad, String tipoEntidad){
//		if(idEntidad!=null && tipoEntidad!=null){
//			return tipoEntidad.concat("-").concat(idEntidad);
//		}
//		return null;
//	}
	
	/**
	 * Obtiene el primer elemento  de la expresión proporcionada
	 * @param String str Expresion
	 * @return String str firstToken
	 * @author Osy
	 */
	public static String firstExpElement(String str){
		log4j.debug("<firstExpElement> Inicio...str :" + str);
		String element = str;
		boolean findPattern;
		
		Pattern p = Pattern.compile(".+(?=<)|.+(?=\\={2})|.+(?=>)|.+(?=\\!)");
		Matcher m = p.matcher(str);
		findPattern = m.find();
		log4j.debug("<firstExpElement> m.find() :" + findPattern);
		if(findPattern){
			log4j.debug("<firstExpElement> Patrón encontrado :" + m.group().toString());
			element = m.group().toString();										
		}

		element = element.trim();
		element = element.replace(".size()", "");		
		element = element.replace("(", "");		
		element = element.replace(")", "");		
		log4j.debug("<firstExpElement> End...element :" + element);
		return element;
	}
	
	
	/**
	 * Elimina los caracteres reservados, para evitar inyeccion de comandos y 
	 * acronimos comunes para mejorar la busqueda de empresas
	 * @param texto
	 * @return
	 */
	public static String cleanFreeText(String texto){
		if(texto!=null){
			final String[] INVTOKENS = {"%","&","#","\"", "$","!","?","¿","|", "=", "S.A.", "C.V.","S.C.", ";", "."};//punto siempre debe ir al final
			for(String token : INVTOKENS){
				texto = texto.replace(token, "");
	    	}
	    	return texto;
		}
		return null;
	}
	
	/**
	 * Pondera la similitud de dos cadenas
	 * @param searchName, string1
	 * @param candidateName,  string2
	 * @return un valor ponderado, si es 0 no fue mayor al maximo de similitud
	 */
	public static float similarity(String searchName, String candidateName) {
	  float score= new JaroWinkler().getSimilarity(searchName, candidateName);	  
	  if(score >= Constante.MAX_SCORE_SIMILARITY){
		  float score2= (new Levenshtein().getSimilarity(searchName, candidateName) +score)/2;
		  //Se verifica si el promedio entre Levenshtein y JaroWinkler es mayor al valor establecido
		  if((score2) < Constante.MAX_SCORE_SIMILARITY){
			  score= 0.0f;
		  }		  
	  }else{
		  score= 0.0f;
	  }
	 // log4j.debug("similarity() -> searchName:"+searchName+" candidateName="+candidateName+" score="+score);
	  return score;
	}
	
	/**
	 * Ejecuta el paradigma de reflexion dado un objeto y el nombre de la propiedad o metodo, a aplicar
	 * @param object, objeto que se aplica reflexion
	 * @param name, nombre de la propiedad o metodo
	 * @param setProperty, el objeto que se va a inyectar solo a la propiedad
	 * @param argumentTypes,(solo para metodos)  se define un array de tipos de datos de los argumentos del metodo correspondiente
	 * @param arguments, (solo para metodos)  se define un array de los argumentos del metodo correspondiente
	 * @return object o null(si hubo un error)
	 * @throws Exception 
	 */
	public static Object executeReflexion(Object object, String name,Object setProperty, 
							Class<?>[] argumentTypes,Object[] arguments) throws Exception {
		Object objectResp=null;
		log4j.debug("#$% executeReflexion --> object="+object.getClass().getName()+
					", name="+name+
					", argumentTypes="+argumentTypes+
					", arguments="+arguments);
		try {
			//para una propiedad
			if(argumentTypes == null && arguments == null){
				//Field field = object.getClass().getDeclaredField(name);
				Field field = findUnderlying(object.getClass(),name);
				field.setAccessible(true);
				//se aplica el get
				if(setProperty == null){
					objectResp = field.get(object);
				//Se aplica el set
				}else{
					field.set(object,setProperty);
					objectResp=new Object();
				}
				
			//para un metodo	
			}else if(argumentTypes != null && arguments != null){
				// Class<?> clz =object.getClass();
				 Method method=object.getClass().getMethod(name, argumentTypes);
				 method.setAccessible(true);
				 objectResp =method.invoke(object.getClass().newInstance(), arguments);
			}
		} catch (Exception e) {
			log4j.error("Error al aplicar reflexion: object="+object.getClass().getName()+
						", name="+name+
						", argumentTypes="+argumentTypes+
						", arguments="+arguments);
			log4j.error("Error al aplicar reflexion:", e);
			e.printStackTrace();
			 throw new SystemTCEException("Error al aplicar reflexion: object="+object.getClass().getName()+
						", name="+name+
						", argumentTypes="+argumentTypes+
						", arguments="+arguments+ 
						"error:"+e);
		}
		//log4j.debug("#$% executeReflexion --> objectResp2:"+objectResp);
		return objectResp;
	}
	
	/**
	 * Busca la propiedad en la clase o superclase
	 * @param clazz
	 * @param fieldName
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	public static Field findUnderlying(Class<?> clazz, String fieldName) {
	    Class<?> current = clazz;
	    do {
	       try {
	           return current.getDeclaredField(fieldName);
	       } catch(Exception e) {}
	    } while((current = current.getSuperclass()) != null);
	    return null;
	}
	
	/**
	 * se aplica formato de moneda al monto dado
	 * @param monto, cantidad a aplicar formato
	 * @return
	 */
	public static String formatoMoneda(double monto){
		return NumberFormat.getCurrencyInstance(Constante.LOCALE).format(monto);
	}
	
	/**
	 * Realiza una evaluación de Coincidencia (Valor Arbitrario de 0-10)
	 * de elementos de una lista de certificaciones vs una Certificacion Buscada
	 * @param certPosicion
	 * @param jsCerts
	 * @return
	 * @throws Exception
	 */
	public static int evalCert(String certPosicion, List<CertificacionDto> lsCerts){
		//JSONObject jsonCert;
		String certPersona;
		int res =0;
		try{
//			System.out.println("Se evaluan "+ lsCerts.size() +" certificaciones-Persona\n\n" );
			for(int x=0; x<lsCerts.size(); x++){
//				jsonCert = jsCerts.getJSONObject(x);
				//certPersona=jsonCert.getString("tituloCert");
				certPersona = lsCerts.get(x).getTituloCert();
				if(certPersona!=null && certPersona.length()>3){

					//System.out.println("certPersona: "+ certPersona);
					/*  1. Si es exactamente igual  */
					if(procTitulo(certPosicion).equals(procTitulo(certPersona))){
						//System.out.println("Coincidencia Exacta");
						res=10;
					}
					else{
						/* 2. Se buscan Acrónimos y se calcula incidencia en certPosicion */ //FIXME con solo mayusculas capturadas todos son Acronimos
						List<String> lsTokens = Arrays.asList(certPersona.split("\\s* \\s*"));
						//System.out.println("lsTokens.size(): "+lsTokens.size());
						Iterator<String> itTok = lsTokens.iterator();
						String token;
						Matcher mat;
						while(itTok.hasNext()){
							token = itTok.next().replace("(", "").replace(")", "");
							System.out.println("token: -" + token+"-");
							mat = Pattern.compile("[A-Z]{3,}"). matcher(token);
							if(mat.matches()){ /*Es Acrónimo */
								System.out.println("Se busca el acronimo -"+token + "- en: "+ procTitulo(certPosicion));
								if(procTitulo(certPosicion).indexOf(token)!=-1){
									System.out.println("Se encontro el acrónimo "+token + " en certPosicion");
									res=8;
								}
							}
						}
						
						if(res==0){//Si sigue sin coincidir, se busca coincidencia de multiples tokens
							itTok = Arrays.asList(procTitulo(certPersona).split("\\s* \\s*")).iterator();
							int iToksPos = Arrays.asList(certPosicion.split("\\s* \\s*")).size();
							int coinc = 0, estad=0;
							while(itTok.hasNext()){
								token = itTok.next();
								if(token.replace(".", "").length()>2){//Para eliminar el Solo Mayusculas
									System.out.println("Se buscan coincidencia del token "+token +" \t en "+ procTitulo(certPosicion));
									if(procTitulo(certPosicion).indexOf(token)!=-1){
									 coinc++;
									}
								}
							}
							estad = (iToksPos/2); //Al menos la mitad de las palabras estan contenidas
							System.out.println( "coinc: "+coinc + ", toksPos/2="+estad +" => "+(coinc>=estad)) ;
							if(coinc>=estad){
								res=6;
							}
						}	
					}
					//System.out.println("*****");
				}
			}
			
		}catch (Exception e){
			log4j.fatal("<evalCert> Error al evaluar las certificaciones: ", e);
		}
		
		return res;
	}
	
	/**
	 * Hace un reemplazo (Precario/basico) de caracteres, palabras y acentos para
	 * normalizar una comparación en Titulos simples
	 * @param titulo
	 * @return
	 */
	private static String procTitulo(String titulo){
		if(titulo!=null){
			titulo = titulo.toUpperCase();
			titulo = titulo.replace("(", "").replace(")", "");
			titulo = titulo.replace(" DE ", " ").replace(" PARA ", " ").replace(" CON ", " ");
			titulo = titulo.replace("Á", "A").replace("É", "E").replace("Í", "I").replace("Ó", "O").replace("Ú", "U");
			
			titulo = titulo.replace("  ", " ");
		}
		return titulo;
	}
	
	/**
	 * Crea un string con la informacion de un archivo
	 * @param path, la ubicacion del archivo
	 * @param encoding, el charset
	 * @return 
	 * @throws IOException
	 */
	public static String readFile(String path, Charset encoding)  throws IOException {
		  return new String(Files.readAllBytes(Paths.get(path)), encoding);
		}
	
	/**
	 * Obtiene un numero aleatorio absoluto en base al rango definido
	 * @param min (Min debe ser menor q max)
	 * @param max
	 * @return
	 */
	public static int getRandomNumberInRange(int min, int max){
	    
	    if(min>max){
	    	max=min;
	    }
	    return (new Random().nextInt((max - min) + 1) + min);
	}
	
//	public static void main(String[] args) {
		
		//System.out.println("similarity:" +similarity("office", "ofice")); 
		//System.out.println("similarity:" +similarity("word", "word")); 
		///////////////////////////////////////////////
		//System.out.println("nvl: " +nvl(null, "ddd"));
		//para una propiedad
		/*Object resp=executeReflexion(new CurriculumDto("4636",null),"idPersona",null, null,null);
		log4j.debug("#$% respField="+resp);
		log4j.debug("//////////////////");*/
		//para un metodo
		/*AreaTextoDto areaTextoDto=new AreaTextoDto();
		areaTextoDto.setPonderacion("98878");
		Class<?>[] argumentTypes = { int.class, AreaTextoDto.class};
		Object[] arguments = { new Integer(23), areaTextoDto};
		Object resp=executeReflexion(new CurriculumDto(),"prueba", argumentTypes,arguments);
		log4j.debug("#$% respMethod="+resp);
		log4j.debug("#$% getPonderacion="+((AreaTextoDto)resp).getPonderacion());
		log4j.debug("********************************************************************--------------------");*/
				//Class<?>[] argumentTypes,Object[] arguments
		/*String mensaje = "<b>Su cuenta esta creada,<prueba> pero es necesario confirmar de lo contrario esta se eliminara del sistema. </b><br/>Para dar de alta su cuenta, por favor haga click en el siguiente enlace <br/> <a href=\" <linkConfirmacion> \" target=\"_blank\" >Tce login</a>";
		HashMap<String, String> hmEtiquetas=new HashMap<String, String>();
		hmEtiquetas.put("linkConfirmacion", "HOOOOOOOOOOOOOOOOOLLLLLLLLLLLLLLLAAAAAAAAAAA");
		hmEtiquetas.put("prueba", "TURURUUUUUUU");
		System.out.println("mensaje=" +reemplazarEtiquetas(hmEtiquetas , mensaje));
		
		System.out.println("es numero positivo " + cadena + "?");
		System.out.println(isPositiveLong(cadena));*/
		//double num=-0.2525;
		
		//System.out.println("round1:"+UtilsTCE.redondear(num,1,RoundingMode.HALF_UP));
		
		//System.out.println("round2:"+new BigDecimal(String.valueOf(num)).setScale(1,RoundingMode.CEILING).toPlainString() );
		/*String cad="Licenciatura de Economía";
		System.out.println(filtroGramaTexto(cad));
	
		String boleano = "1"; //true | 1 | algo
		try{
			System.out.println("evaluando boleano: " + boleano );
			if(boleano!=null && Boolean.parseBoolean(boleano)){
				System.out.println("se calculan datos");
			}else{
				System.out.println("no se calculan");
			}
		}catch (Exception e){
			e.printStackTrace();
		}*/
		
		//System.out.println("distGeo: " +distGeo(50.06639, -5.71472, 58.64389, -3.07000));
//		System.out.println(isNumeric("125774"));
//	}
}
