package net.tce.admin.service.impl;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import net.tce.admin.service.JavaMailManagerService;
import net.tce.app.exception.SystemTCEException;
import net.tce.dto.CorreoTceDto;
import net.tce.util.AmazonSESSample;
import net.tce.util.ApplicationContextProvider;
import net.tce.util.UtilsNotification;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
/**
 * Clase (implementacion) diseñada para generar y enviar un objeto de correo
 * con valores de configuración en constantes, y valores de cuerpo obtenidos de un 
 * objeto CorreoTceDto
 * 
 * @author evalle
 *
 */
@Service("javaMailManagerService")
public class JavaMailManagerServiceImpl implements JavaMailManagerService{
	
	Logger log4j = Logger.getLogger( JavaMailManagerServiceImpl.class );
	
	 @Value("${email.username.clave}")
	 private boolean isUsuarioclave;
	 
	 @Value("${email.username}")
	 private String username;
	
//	 @Value("${email.remite}")
	 private String emailReclutamiento = "reclutamientoTalentwise@dothr.net";//Cuenta Reclutamiento [HardCode para evitar hackeo de War]
	 
//	 @Value("${email.remite.failure}")
	 private String emailSoporte = "soporte@dothr.net"; //Cuenta soporte
	 
	 @Value("${email.password.failure}")
	 private String passwordFailure;
	 
	 @Value("${email.password}")
	 private String password;
	 
	 @Value("${mail_path}")
	 private String mail_path;
	 
	JavaMailSenderImpl javaMailSender;
	MimeMessage message;
	MimeMessageHelper helper;

	//@Autowired
	//private SimpleAsyncTaskExecutor asynchTaskExecutor;
	
	@Autowired
    private JavaMailSender mailSender;
	
	private Boolean enviado;
	
	
//	/**
//	 * Crea un hilo por cada llamada a sendMail
//	 * @param correo, objeto con la informacion del correo a amndar
//	 */
	/*public void threadMail(final CorreoTceDto correo) {
		if (this.asynchTaskExecutor != null) {
			this.asynchTaskExecutor.execute(new Runnable() {
				public void run() {
                	try {
                		sendMail( correo);                		
                	} catch (Exception e) {
						log4j.error("Error al mandar correo, numHilo:"+
									Thread.currentThread().getId()+"--> error:"+e.toString());
						e.printStackTrace();
					}
				}
			});
		}
	}*/
	
	
	
	/**
	 * metodo que genera un correo electronico por medio de MimeMessage con
	 * información obtenida de un objeto CorreoTceDto
	 * @param correo
	 * @throws SystemTCEException 
	 */
	public void sendMail(CorreoTceDto correo) throws SystemTCEException {
		if(correo!=null){
			
			//Prueba 2
			//System.out.println("Adjuntando archivo..");
			//FileSystemResource file = new FileSystemResource(correo.getAdjunto());
			//helper.addAttachment("Adjunto.jpg", file);
			
			//Prueba 3: agregar imagen in-Line
			// let's include the infamous windows Sample file (this time copied to c:/)
			//FileSystemResource res = new FileSystemResource(new java.io.File("C:/Server/images/rss.png"));
			//helper.addInline("identifier1234", res);
			log4j.debug("sendMail() ->  emailReclutamiento:"+emailReclutamiento+
					" emailSoporte="+emailSoporte+
					" passwordFailure="+passwordFailure+
					" getDestinatario="+correo.getDestinatario()+
					" isUsuarioclave="+isUsuarioclave);
			
	        try {
	        	message = mailSender.createMimeMessage();
	        	helper = new MimeMessageHelper(message, true);
	        	//Para adjuntar archivos
			 	//helper.setFrom( new InternetAddress(MAIL_SERVER_USERNAME) );
	        	
	        	//se obtiene el bean javaMailSender
        		javaMailSender=(JavaMailSenderImpl)ApplicationContextProvider.
													getBean("javaMailService");
        		
        		log4j.debug("sendMail() ->  javaMailSender="+javaMailSender+
    					" claveEvento="+correo.getClaveEvento());
	        	
	        	//si hay un evento fatal 
	        	if(correo.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_ERROR_FATAL) ) {	        		 
	        			        		 
	        		//se pone el usuario y pass del remitente
	        		javaMailSender.setUsername(isUsuarioclave ? username:emailSoporte);
	        		javaMailSender.setPassword(passwordFailure);
	        		
	        		//si se ocupa la opcion de usuario_clave
		        	if(isUsuarioclave){
		        		helper.setFrom(new InternetAddress(emailSoporte));
		        	}
	        	}else {
	        		
	        		//se pone el usuario y pass del remitente
	        		 javaMailSender.setUsername(isUsuarioclave ? username:emailReclutamiento);
	        	     javaMailSender.setPassword(password);
	        		
	        		//si se ocupa la opcion de usuario_clave
		        	if(isUsuarioclave){
		        		helper.setFrom(new InternetAddress(emailReclutamiento));
		        	}
	        	}
	        	
				helper.setTo(new InternetAddress(correo.getDestinatario()));
				helper.setSubject(correo.getAsunto());
				message.setText(correo.getCuerpo(),
						 		UtilsNotification.MAIL_MESSTEXT_CHARSET,
						 		UtilsNotification.MAIL_MESSTEXT_SUBTYPE);
				//log4j.debug("Enviando E-mail -> getCuerpo: "+correo.getCuerpo());
	        	log4j.debug("sendMail() -> Enviando E-mail: \"".concat(correo.getAsunto()).concat("\" a ").
	        				concat(correo.getDestinatario()) );
	        	mailSender.send(message);
	        }
	        catch (Exception ex) {
	        	log4j.fatal("Error al mandar correo al destinatario:"+correo.getDestinatario()+" ",ex); 
	        	throw new SystemTCEException("Error al mandar correo al destinatario:"+correo.getDestinatario()+" ",ex);
	        }
		 }
	}


	@Override
	public void sendMailBySES(CorreoTceDto correo) throws SystemTCEException {
		log4j.debug("<sendMailBySES>");
		enviado = false;
		if(correo!=null){
					log4j.debug("<sendMailBySES> " //+ " emailSoporte="+emailSoporte
							+" destinatario="+correo.getDestinatario()
							+" remitente="+correo.getRemitente()
							);
					correo.setRemitente(emailReclutamiento);  //Por defecto envia desde reclutamiento
//					correo.setDestinatario("ernesto.valle@dothr.net"); //DesComentar unicamente para prueba
					if(correo.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_ERROR_FATAL) ) {
						correo.setRemitente(emailSoporte); //SI hay error de sistema, se envia desde cuenta soporte/failure
					}
					try {						
						enviado = AmazonSESSample.sendEmail(correo);
						if(enviado) {
							log4j.debug("<sendMailBySES> \n ENVIO DE CORREO ELECTRONICO a " + correo.getDestinatario() + " realizado sin Errores");
						}
						else {
							log4j.debug("<sendMailBySES> \n ENVIO DE CORREO ELECTRONICO a " + correo.getDestinatario() + " No realizado");
							throw new SystemTCEException("Error al mandar correo al destinatario: "+correo.getDestinatario());
						}
					} catch (Exception ex) {
						log4j.fatal("<sendMailBySES> Error al mandar correo al destinatario: "+correo.getDestinatario(),ex);  
			        	throw new SystemTCEException("Error al mandar correo al destinatario: "+correo.getDestinatario(), ex);
					}
		}
		
	}
}