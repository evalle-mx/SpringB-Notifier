package net.tce.admin.adapter.rest;

import java.text.ParseException;
import javax.inject.Inject;
import net.tce.admin.service.AdminService;
import net.tce.app.exception.SystemTCEException;
import net.tce.dto.MensajeDto;
import net.tce.exception.FileException;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


/**
 * Clase que gestiona excepciones y maneja mensajes 
 * @author Goyo
 *
 */
public class ErrorMessageAdapterRest {
	Logger log4j = Logger.getLogger( this.getClass());
	
	@Autowired
	private AdminService adminService;
	
	@Inject
	Gson gson;
	

	final MensajeDto mensajeDto=new MensajeDto(null,null,
								Mensaje.SERVICE_CODE_006,
								Mensaje.SERVICE_TYPE_FATAL,
								null);
	
	
	
	/**
	 * Maneja excepciones de tipo NoSuchFieldException
	 * @param e, objeto de la excepcion correspondiente
	 * @return un mensaje JSON
	 */
	@ExceptionHandler(NoSuchFieldException.class)
	public @ResponseBody String handleNoSuchFieldException(final NoSuchFieldException e)
	{
		log4j.error("&---& NoSuchFieldException ERROR: "+e.toString(), e);
		e.printStackTrace();
	    return mensajeError(e); 
	}
	
	/**
	 * Maneja excepciones de tipo ParseException
	 * @param e, objeto de la excepcion correspondiente
	 * @return un mensaje JSON
	 */
	@ExceptionHandler(ParseException.class)
	public @ResponseBody String handleParseException(final ParseException e)
	{
		log4j.error("&---& ParseException ERROR: "+e.toString(), e);
		e.printStackTrace();
	    return mensajeError(e); 
	}
	
	/**
	 * Maneja excepciones de tipo SystemTCEException
	 * @param e, objeto de la excepcion correspondiente
	 * @return un mensaje JSON
	 */
	@ExceptionHandler(SystemTCEException.class)
	public @ResponseBody String handleSystemTCEException(final SystemTCEException e)
	{
		log4j.error("&---& SystemTCEException ERROR: "+e.toString(), e);
		e.printStackTrace();
	    return mensajeError(e); 
	}
	
	
	/**
	 * Maneja excepciones de tipo GenericJDBCException
	 * @param e, objeto de la excepcion correspondiente
	 * @return un mensaje JSON
	 */
	@ExceptionHandler(PropertyValueException.class)
	public @ResponseBody String handlePropertyValueException(final PropertyValueException e)
	{
		log4j.error("&---&  handlePropertyValueException ERROR: "+e.toString(), e);
		e.printStackTrace();
	    return mensajeError(e); 
	}
		
	/**
	 * Maneja excepciones de tipo UncategorizedSQLException
	 * @param e, objeto de la excepcion correspondiente
	 * @return un mensaje JSON
	 */
	@ExceptionHandler(UncategorizedSQLException.class)
	public @ResponseBody String handleUncategorizedSQLException(final UncategorizedSQLException e)
	{
		log4j.error("&---&  UncategorizedSQLException ERROR: "+e.getSQLException().getMessage()+" --(bueno) "+
				e.getSQLException().getErrorCode(), e);
		e.printStackTrace();
	    return mensajeError(e); 
	}
	
	/**
	 * Maneja excepciones de tipo DataIntegrityViolationException
	 * @param e, objeto de la excepcion correspondiente
	 * @return un mensaje JSON
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	public @ResponseBody String handleDataIntegrityViolationException(final DataIntegrityViolationException e)
	{
		log4j.error("&---&  DataIntegrityViolationException ERROR: "+e.getMostSpecificCause().getMessage(), e);
		e.printStackTrace();
	    return mensajeError(e); 
	}
	
	/**
	 * Maneja excepciones de tipo ConstraintViolationException
	 * @param e, objeto de la excepcion correspondiente
	 * @return un mensaje JSON
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public @ResponseBody String handleConstraintViolationException(final ConstraintViolationException e)
	{
		log4j.error("&---& ConstraintViolationException ERROR:"+e.getSQLState()+" -- "+e.getSQLException().getErrorCode()+
				" --"+e.getSQLException().getMessage(), e);
		e.printStackTrace();
	    return mensajeError(e); 
	}
	
	/**
	 * Maneja excepciones de tipo JsonSyntaxException
	 * @param e, objeto de la excepcion correspondiente
	 * @return un mensaje JSON
	 */
	@ExceptionHandler(JsonSyntaxException.class)
	public @ResponseBody String handleJsonSyntaxException(final JsonSyntaxException e)
	{
		log4j.error("&---& JsonSyntaxException ERROR: "+e.toString(), e);
		e.printStackTrace();
	    return mensajeError(e); 
	}
	
	/**
	 * Maneja excepciones de tipo ClassNotFoundException
	 * @param e, objeto de la excepcion correspondiente
	 * @return un mensaje JSON
	 */
	@ExceptionHandler(ClassNotFoundException.class)
	public @ResponseBody String handleClassNotFoundException(final ClassNotFoundException e)
	{
		log4j.error("&---& ClassNotFoundException ERROR: "+e.toString(), e);
		e.printStackTrace();
	    return mensajeError(e); 
	}
	
	/**
	 * Maneja excepciones de tipo CannotCreateTransactionException
	 * @param e, objeto de la excepcion correspondiente
	 * @return un mensaje JSON
	 */
	@ExceptionHandler(CannotCreateTransactionException.class)
	public @ResponseBody String handleCannotCreateTransactionException(final CannotCreateTransactionException e)
	{
		log4j.error("&---& CannotCreateTransactionException ERROR: "+e.toString(), e);
		e.printStackTrace();
	    return mensajeError(e); 
	}
	
	/**
	 * Maneja excepciones de tipo CannotCreateTransactionException
	 * @param e, objeto de la excepcion correspondiente
	 * @return un mensaje JSON
	 */
	@ExceptionHandler(FileException.class)
	public @ResponseBody String handleFileException(final FileException e)
	{
		log4j.error("&---& FileException ERROR: "+e.toString(), e);
		e.printStackTrace();
	    return mensajeError(e); 
	}
	
	
	/**
	 * Maneja cualquier otra excepcion
	 * @param e, objeto de la excepcion correspondiente
	 * @return un mensaje JSON
	 */
	@ExceptionHandler(Exception.class)
	public @ResponseBody String Exception(final Exception e)
	{
	    log4j.error("&---& Exception ERROR: "+e.toString(), e);
		e.printStackTrace();
	    return mensajeError(e); 
	}
	
	/**
	 * Crea un mensaje de error en general, para las politicas de negocio
	 * @return mensaje de error en JSON
	 */
	private String mensajeError(Exception e){
		 log4j.error("mensajeError() -> mensaje: "+e.getMessage());
			
			//divisor tipo HTTP 
			if(e.getMessage().contains(Constante.DIVISOR_HTTP)){
				String[] arMensaje= e.getMessage().split(Constante.DIVISOR_HTTP);
				log4j.debug("mensajeError() -> arMensaje[0]: "+arMensaje[0]+
						" arMensaje[1]: "+arMensaje[1]);
				
				//es numerico
				if(UtilsTCE.isNumeric(arMensaje[0])){ 
					  //HTTP 4xx
					 if (Constante.HTTP_ERROR_CLIENT_4xx_INI <= Integer.parseInt(arMensaje[0]) &&
					    Integer.parseInt(arMensaje[0]) < Constante.HTTP_ERROR_CLIENT_4xx_FIN ){
					    	mensajeDto.setCode(Mensaje.SERVICE_CODE_200);
					    	mensajeDto.setMessage(Mensaje.MSG_FATAL_OPERATIONAL_NO_STRUCTURED);
					   }
				}
			}else {
				mensajeDto.setMessage(ExceptionUtils.getStackTrace(e).substring(0,
										Constante.MESSAGE_FATAL_LENGHT).concat(" ..."));	
			}
		
		
		//se envía correo a los admin
		return adminService.notificacionFatal(mensajeDto);
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion ping para verificar conectividad con Aplicacion 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 */
	@RequestMapping(value=net.tce.util.Constante.URI_PING, method=RequestMethod.POST,headers = net.tce.util.Constante.ACEPT_REST_JSON)
	public @ResponseBody String ping(@RequestBody String json) {
		log4j.debug("<PING> enviando respuesta positiva de Ping: -ping OK-");
		return UtilsTCE.getJsonMessageGson(null, new MensajeDto("app","AppNotifierStructured",
				Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_INFORMATION,
				Mensaje.MSG_WARNING + " -ping OK-"));
	  } 
}
