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
	
	public Long getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(Long ticketNo) {
		this.ticketNo = ticketNo;
	}
	public BigDecimal getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(BigDecimal ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public Document getMailXml() {
		return mailXml;
	}
	public void setMailXml(Document mailXml) {
		this.mailXml = mailXml;
	}
	public File getMailXsl() {
		return mailXsl;
	}
	public void setMailXsl(File mailXsl) {
		this.mailXsl = mailXsl;
	}
	@Override
	public String toString() {
		return "Ticket [ticketNo=" + ticketNo + ", ticketPrice=" + ticketPrice
				+ "]";
	}
	
	
}
