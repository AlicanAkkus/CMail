package com.caysever.test;

import java.io.File;
import java.io.FileInputStream;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import com.caysever.mail.sender.MailSender;
import com.caysever.model.Ticket;
import com.caysever.utils.XmlUtils;

public class MailTest {
	private static Logger logger = Logger.getLogger(MailTest.class);
	public static void main(String[] args) {
		
		try {
			Document tickets = XmlUtils.loadXmlFromFile("distsrc/simulation/xml/tickets.xml");
			File xslFile = new File("distsrc/simulation/xsl/tickets.xsl");
			
			Ticket ticket = new Ticket();
			ticket.setMailXml(tickets);
			ticket.setMailXsl(xslFile);
			
			String mailContent = XmlUtils.transformXmlXsl(tickets, new FileInputStream(xslFile));
			System.out.println(mailContent);
			
			MailSender mailSender = new MailSender();
			mailSender.sendMail(ticket);
			
		} catch (Exception e) {
			logger.error(e, e);
		}
		
	}
}
