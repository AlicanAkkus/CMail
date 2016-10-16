# CMail
CMail is a mail sender application. You can easily mark the mail details in java bean and then send mail.
Easily to create content from xml to xsl with internal translation.

JavaBean class;
``` java
package com.caysever.model;

import java.io.File;
import java.math.BigDecimal;

import org.w3c.dom.Document;

import com.caysever.annotation.Config;
import com.caysever.annotation.XML;
import com.caysever.annotation.XSL;

public class Ticket {
	
	Long ticketNo;
	BigDecimal ticketPrice;
	
	@XML(tags = "ticketInfo", createdBy="caysever")
	Document mailXml;
	
	@XSL(tags="ticketXsl" ,description = "Translation xml to mail content")
	File mailXsl;
	
	@Config(subject="Ticket Notification" ,to = { "alican.akkus@32bit.com.tr" }, isAuth=false, smtpHost="your.smtp.host.name", smtpPort="25", charset="UTF-8", contentType="text/html", cc = { "" })
	MailConfig config;
	
	....
  gettes and setters
	
}
```

Send Mail;
``` java
Document tickets = XmlUtils.loadXmlFromFile("distsrc/simulation/xml/tickets.xml");
File xslFile = new File("distsrc/simulation/xsl/tickets.xsl");
			
Ticket ticket = new Ticket();
ticket.setMailXml(tickets);
ticket.setMailXsl(xslFile);
			
String mailContent = XmlUtils.transformXmlXsl(tickets, new FileInputStream(xslFile));
System.out.println(mailContent);
			
MailSender mailSender = new MailSender();
mailSender.sendMail(ticket);
```

