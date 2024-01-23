package pe.gob.sunarp.app.solicitud.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

@Component
public class Email {	

	private static final Logger LOG = LoggerFactory.getLogger(Email.class);
    @Autowired
    private ApplicationContext context;
    
	private final String user = "";
	private final String clave = "";
	
	public int sendEmail(String[] para, String asunto, String mensaje, String url, 
			boolean autenticar, boolean attachFile, String bs, String[] cco, String[] reply, String appName) {
		
		String host = context.getEnvironment().getProperty("correo.servidor");
		String puerto = context.getEnvironment().getProperty("correo.puerto");
		LOG.info("host = " + host);
		LOG.info("puerto = " + puerto);
		
		try {
			Properties mailProps = new Properties();

			mailProps.put("mail.smtp.host", host);
			mailProps.put("mail.smtp.port", puerto );

			// Get session
			Session mailSession;

			if(autenticar){
				mailProps.put("mail.smtp.auth", true);
				mailSession = Session.getDefaultInstance(mailProps, new Authenticator() {
		            @Override
		            protected PasswordAuthentication getPasswordAuthentication() {
		                return new PasswordAuthentication(user, clave);
		            }
		        });
			} else {
				mailSession = Session.getDefaultInstance(mailProps);
			}
						
			// Define message
			MimeMessage message = new MimeMessage(mailSession);
			
			//De :
			InternetAddress from = new InternetAddress((String)context.getEnvironment().getProperty("correo.from"), appName);
			message.setFrom(from);
			
			/*correo destinatario*/
			String[] emails = para;	        
			InternetAddress dests[] = new InternetAddress[emails.length];
	        for (int i = 0; i < emails.length; i++) {
	            dests[i] = new InternetAddress(emails[i].trim().toLowerCase());
	        }
	        message.setRecipients(Message.RecipientType.TO, dests);
	        	        
	        /*correo copia oculta*/
	        if (cco!=null){
		    	InternetAddress bcc[] = new InternetAddress[cco.length];
		        for (int i = 0; i < bcc.length; i++) {
		        	bcc[i] = new InternetAddress(cco[i].toLowerCase());
		        }	        
		        message.setRecipients(Message.RecipientType.BCC, bcc);	
	        }
	                
	        /*correo reply*/
	        if(reply!=null){
		    	InternetAddress replys[] = new InternetAddress[reply.length];
		        for (int i = 0; i < reply.length; i++) {
		        	replys[i] = new InternetAddress(reply[i].toLowerCase());
		        }	        
		        message.setReplyTo(replys);
	        }    
	        	        
	        message.setSubject(asunto, "UTF-8");	      		
			
			//Fecha actual		
			SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
			Date currentDate = new Date();
			String hoy = fecha.format(currentDate);
			
			String htmlText ="<font color='#000000' size='2' face='Verdana, Arial, Helvetica, sans-serif'>"+mensaje+"</font>"+
					"<br><br><strong><font  size='1' face='Verdana, Arial, Helvetica, sans-serif'>Fecha : "+hoy+" </font></strong>"+
					"<br><font  size='1' face='Verdana, Arial, Helvetica, sans-serif'>";
			if(url != null && !url.toString().equals("")){
				htmlText = htmlText.concat("Puede acceder haciendo click ... <a href='"+url+"'> aqu&iacute; </a></font>");
			}
			
			htmlText = htmlText.concat("<br><br><p><img src=\"https://www.sunarp.gob.pe/Images/logo_Sunarp_news.png\" width=\"182\" height=\"101\" />");
			
			Multipart mp = new MimeMultipart();
	        MimeBodyPart mbp = new MimeBodyPart();
	        mbp.setContent(htmlText, "text/html;charset=utf-8");
	        mp.addBodyPart(mbp);
	        
	        if (attachFile){		        	
	        	if (bs!=null && !bs.trim().equals("")){
		        	// byte[] imageByte;
		            // BASE64Decoder decoder = new BASE64Decoder();
		            // imageByte = decoder.decodeBuffer(bs);	
		            
		            byte[] imageByte = Base64.getDecoder().decode(bs);
		        	
					DataSource dataSource = new ByteArrayDataSource(imageByte, "image/png");
					MimeBodyPart fileBodyPart = new MimeBodyPart();
					fileBodyPart.setDataHandler(new DataHandler(dataSource));
					fileBodyPart.setFileName("screenshot.png");
					mp.addBodyPart(fileBodyPart);
	        	}
	        }
	        	    
	        message.setContent(mp);
	        message.setSentDate(currentDate);
	        
			// Send message
			Transport.send(message);
			return 1;

		} catch (SendFailedException s){
			s.printStackTrace();			
			return 9;
		} catch (AddressException a){
			//a.printStackTrace();
			return 8;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}