package com.caysever.mail.sender;

import java.io.File;
import java.io.FileInputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;

import com.caysever.annotation.Config;
import com.caysever.annotation.XML;
import com.caysever.annotation.XSL;
import com.caysever.model.Ticket;
import com.caysever.utils.XmlUtils;

public class MailSender {

	public void sendMail(Object mailObject) throws Exception {

		try {
			Properties props = new Properties();
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();
			MimeMessage msg = null;
			Authenticator auth = null;
			Session session = null;

			Document xmlDocument = null;
			File xslFile = null;
			String charset = null;
			String contentType = null;

			Class<?> mailObjectClass = mailObject.getClass();

			Field fields[] = mailObjectClass.getDeclaredFields();

			for (Field field : fields) {
				Field declaredField = mailObjectClass.getDeclaredField(field
						.getName());
				declaredField.setAccessible(true);// for private variable

				Annotation[] fieldAnnotations = declaredField.getAnnotations();
				
				for(Annotation annotation : fieldAnnotations) {
					if(annotation.annotationType() == XML.class) {
						xmlDocument = (Document) declaredField.get(mailObject);
					}
					if(annotation.annotationType() == XSL.class) {
						xslFile = (File) declaredField.get(mailObject);
					}
					if(annotation.annotationType() == Config.class) {
						Config mailConfig = (Config) annotation;
						props.put("mail.smtp.host", mailConfig.smtpHost());

						if (mailConfig.isAuth()) {
							props.put("mail.smtp.auth", "true");
							auth = new Authenticator() {
								@Override
								protected PasswordAuthentication getPasswordAuthentication() {
									return new PasswordAuthentication(
											mailConfig.smtpUsername(),
											mailConfig.smtpPassword());
								}
							};
						}

						session = Session.getInstance(props, auth);
						if (mailConfig.isDebug()) {
							session.setDebug(true);
						}

						msg = new MimeMessage(session);

						if (StringUtils.isNotBlank(mailConfig.subject())) {
							msg.setSubject(mailConfig.subject(),
									mailConfig.charset());
						}

						msg.setRecipients(Message.RecipientType.TO,
								Arrays.toString(mailConfig.to()));
						msg.setRecipients(Message.RecipientType.CC,
								Arrays.toString(mailConfig.cc()));
						
						contentType = mailConfig.contentType();
						charset = mailConfig.charset();
					}
				}
				
			}

			if (xmlDocument != null && xslFile != null) {
				String mailContent = XmlUtils.transformXmlXsl(xmlDocument,
						new FileInputStream(xslFile));
				
				messageBodyPart.setContent(mailContent, contentType);
				messageBodyPart.addHeader("Content-Type", contentType + "; " +"charset= " + charset);
				multipart.addBodyPart(messageBodyPart);
				
				msg.setContent(multipart);
				msg.setRecipients(Message.RecipientType.CC, "alican.akkus@32bit.com.tr");
				Transport.send(msg);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void sendMail(List<Object> mailObjects) throws Exception {

		for (Object mailObject : mailObjects) {
			sendMail(mailObject);
		}

	}

	public void sendMail(List<Object> mailObjects, int delay) throws Exception {

		for (Object mailObject : mailObjects) {
			sendMail(mailObject);
			Thread.sleep(delay);
		}

	}
	
	public static void main(String[] args) {
		try {
			Document tickets = XmlUtils.loadXmlFromFile("distsrc/simulation/xml/tickets.xml");
			File xslFile = new File("distsrc/simulation/xsl/tickets.xsl");
			
			Ticket ticket = new Ticket();
			ticket.setMailXml(tickets);
			ticket.setMailXsl(xslFile);
			
			String mailContent = XmlUtils.transformXmlXsl(tickets, new FileInputStream(xslFile));
			
			new MailSender().sendMail(ticket);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
