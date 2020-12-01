package net.tce.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

import net.tce.dto.CorreoTceDto; 

public class AmazonSESSample {
	
//	static final String FROM = "reclutamiento@dothr.net";
	static final String DEF_TO = "ernesto.valle@dothr.net"; //gabriel.suarez@pirh.com.mx  | ernesto.valle@dothr.net
	static final String EMAIL_SUPPORT = "soporte@dothr.net";
	
//	static final String SUBJECT = "DEMO amazon SES test";
//	static final String HTMLBODY = "<h1>Test SES</h1><p>Email was sent with SES</p>";	
//	static final String TEXTBODY = "This 2nd email was sent through SES by java";
	
//	@Value("${mail_path}")
	 private static String correosEnviados = "/home/netto/logs/";
	
	static Logger log4j = Logger.getLogger( AmazonSESSample.class );
	
//	public static void main(String[] args) {
//		try {
//			AmazonSimpleEmailService client = 
//					AmazonSimpleEmailServiceClientBuilder.standard()
//					.withRegion(Regions.US_EAST_1).build();
//			SendEmailRequest request = new SendEmailRequest()
//					.withDestination( 
//							new Destination().withToAddresses(TO) )
//					.withMessage( new Message()
//							.withBody( new Body()
//									.withHtml( new Content()
//											.withCharset("UTF-8").withData(HTMLBODY)
//											)
//									.withText( new Content().withCharset("UTF-8").withData(TEXTBODY) )
//									)
//							.withSubject( new Content()
//									.withCharset("UTF-8").withData(SUBJECT)
//									)
//							)
//					.withSource(FROM);
//			client.sendEmail(request);
//			System.out.println("email to " + TO +" was Send!");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
	/**
	 * Envia correo con la configuracion ABIERTA de SES (En prueba)
	 * @param correo
	 * @throws Exception
	 */
	public static boolean sendEmail(CorreoTceDto correo) throws Exception {
		log4j.debug("<sendEmail> correoDto: \n"+
				correo.getDestinatario() + "\n" + correo.getAsunto());
		escribeListaEnvio(correo);
		return sendEmailSES(
				correo.getDestinatario(),  //Direccion electronica del Candidato/Usuario //https://youtu.be/c-Uk1Qytiiw?t=285s
				correo.getRemitente(),  //Usualmente Reclutamiento o cuenta Robot
				correo.getAsunto(),  //MOtivo del correo (Confirmacion, Publicacion, etc)
				correo.getCuerpo() //Plantilla DotHR
				);
//		return true; //DESCOMENTAR PARA SIMULAR RESULTADO DE ENVIO
	}
	
//	/**
//	 * Envia el correo con la configuracion cerrada a subscripcion SES
//	 * @param subject
//	 * @param html
//	 * @param body
//	 * @throws Exception
//	 */
//	public static boolean sendEmail(String subject, String html, String body) throws Exception {
//		log4j.debug("<sendEmail> Sending email by demand");	
//		return sendEmail(TO, FROM, subject, body);
//	}
	
	public static void sendFailureEmail(String soporte, String description)  {
		log4j.debug("<sendEmail> Sending email To support/failure");
		try {
			sendEmailSES(EMAIL_SUPPORT, soporte==null?DEF_TO:soporte, "Application Error/Exception", "<h1>La applicacion Arrojo un error</h1><p> <b>Descripcion</b> "+description +"</p>");
		} catch (Exception e) {
			e.printStackTrace();
			log4j.fatal("<sendEmail> ENVIO A SOPORTE FALLO!!!!!  ", e);
		}
	}
	
	/**
	 * Private Method with business logic
	 * @param toAddress
	 * @param fromAddress
	 * @param subject
	 * @param body
	 * @throws Exception
	 */
	private static boolean sendEmailSES(String toAddress, String fromAddress, String subject, String body) throws Exception {
		log4j.debug("<sendEmail> ");
		try {
			AmazonSimpleEmailService client = 
					AmazonSimpleEmailServiceClientBuilder.standard()
					.withRegion(Regions.US_EAST_1).build();
			SendEmailRequest request = new SendEmailRequest()
					.withDestination( 
							new Destination().withToAddresses(toAddress) )
					.withMessage( new Message()
							.withBody( new Body()
									.withHtml( new Content()
											.withCharset("UTF-8").withData(body)
											)
//									.withText( new Content().withCharset("UTF-8").withData(text) )
									)
							.withSubject( new Content()
									.withCharset("UTF-8").withData(subject)
									)
							)
					.withSource(fromAddress);
			client.sendEmail(request);
			return true;			
		}
		catch (Exception e) {
			e.printStackTrace();
			log4j.fatal("<sendEmail> ENVIO A SOPORTE FALLO!!!!!  ", e);
			
		}
		return false;
	}

	/* Consola SES en AWS https://console.aws.amazon.com/ses/home 
	  https://stackoverflow.com/questions/46245538/what-is-the-required-configuration-steps-to-have-a-spring-boot-application-send
	  https://gist.github.com/ShigeoTejima/f2997f57405010c3caca
    */
	
	private static void escribeListaEnvio(CorreoTceDto correo) {
		correosEnviados = System.getProperty("user.home")+"/logs/";
		log4j.debug("<sendEmail> agrega a lista de correos enviados en "+correosEnviados);
		java.io.BufferedWriter bufferedWriter;
		String nuevoMsg = "To:"+correo.getDestinatario() + ". Subject:"+correo.getAsunto()+". Date:"+ (new java.util.Date()) +"\n";
		//System.out.println(nuevoMsg);
		try {
			//bufferedWriter = new BufferedWriter(new FileWriter(filePath, append));
			bufferedWriter = new java.io.BufferedWriter (new java.io.OutputStreamWriter(new java.io.FileOutputStream(correosEnviados +"correosEnviados.txt", true),"UTF-8"));
			bufferedWriter.write(nuevoMsg);
			bufferedWriter.close();
			//System.out.println("escrito en " + mail_path);
		} catch (java.io.IOException e) {
			e.printStackTrace();
			log4j.fatal(e);
//			throw e;
		}
	}
}
