package Javamail;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class MailUtil {
	//配置信息
	public static String myEmailAccount = "test2014123@126.com";
	public static String myEmailPassword = "123456abcd";
	public static String myEmailSMTPHost = "smtp.126.com";
	public static String receiveMailAccount = "2734828072@qq.com";
	public static String mailtheme = "这是一个测试邮件";
	public static Date   data = new Date();
	
	//
	public static Session session = null;
	public static void sendmail(MimeMessage message) throws Exception {
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.host", myEmailSMTPHost);
		props.setProperty("mail.smtp.auth", "true");
		session = Session.getInstance(props);
		session.setDebug(true);
		Transport transport = session.getTransport();
		transport.connect(myEmailAccount, myEmailPassword);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}
	
	public static MimeBodyPart createBodyTxt(String content) throws Exception {
		MimeBodyPart text = new MimeBodyPart();
		text.setContent(content+"<br/>", "text/html;charset=UTF-8");
		return text;
	}
	
	public static MimeBodyPart createBodyLink(String content,String link_url) throws Exception {
		MimeBodyPart link = new MimeBodyPart();
		link.setContent("<html lang='zh-CN'><head ><meta charset='utf-8'>"
				+ "</head><body><a href="+link_url+">"+content+"</a></body></html>",
				"text/html;charset=utf-8");
		return link;
	}
	
	public static MimeBodyPart createBodyPic(String describe,String local_pic) throws Exception {
		MimeBodyPart image = new MimeBodyPart();
		DataHandler dh = new DataHandler(new FileDataSource(local_pic));
		image.setDataHandler(dh);
		image.setContentID("image_page_one"); 
		MimeBodyPart text = new MimeBodyPart();
		text.setContent(describe+"<br/><img src='cid:image_page_one'/><br/>", "text/html;charset=UTF-8");
		MimeMultipart mm_text_image = new MimeMultipart();
		mm_text_image.addBodyPart(text);
		mm_text_image.addBodyPart(image);
		mm_text_image.setSubType("related");
		MimeBodyPart text_image = new MimeBodyPart();
		text_image.setContent(mm_text_image);
		return text_image;
	}

	public static MimeBodyPart createBodyAttach(String local_att) throws Exception {
		MimeBodyPart attachment = new MimeBodyPart();
		DataHandler dh2 = new DataHandler(new FileDataSource(local_att));
		attachment.setDataHandler(dh2);
		attachment.setFileName(MimeUtility.encodeText(dh2.getName()));
		return attachment;
	}
	
	public static MimeMessage mailDetails(String mailtheme,MimeBodyPart... param) throws Exception {
		MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myEmailAccount,"UTF-8"));
        message.addRecipient(RecipientType.TO, new InternetAddress(receiveMailAccount, "UTF-8"));
        message.setSubject(mailtheme, "UTF-8");
        int length = param.length;
        MimeMultipart mm = new MimeMultipart();
        for (int i = 0;i<length;i++) {
        	mm.addBodyPart(param[i]);
        }
        message.setContent(mm);
        message.setSentDate(new Date());  // 12. 设置发件时间
        message.saveChanges();
        return message;
	}
	
	public static MimeMessage mailDetails(String mailtheme,Date data,MimeBodyPart... param) throws Exception {
		MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myEmailAccount,"UTF-8"));
        message.addRecipient(RecipientType.TO, new InternetAddress(receiveMailAccount, "UTF-8"));
        message.setSubject(mailtheme, "UTF-8");
        int length = param.length;
        MimeMultipart mm = new MimeMultipart();
        for (int i = 0;i<length;i++) {
        	mm.addBodyPart(param[i]);
        }
        message.setContent(mm);
        message.setSentDate(data); 
        message.saveChanges();
        return message;
	}
	
	public static MimeMessage mailDetails(MimeBodyPart... param) throws Exception {
		MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myEmailAccount,"UTF-8"));
        message.addRecipient(RecipientType.TO, new InternetAddress(receiveMailAccount, "UTF-8"));
        message.setSubject(mailtheme, "UTF-8");
        int length = param.length;
        MimeMultipart mm = new MimeMultipart();
        for (int i = 0;i<length;i++) {
        	mm.addBodyPart(param[i]);
        }
        message.setContent(mm);
        message.setSentDate(data);
        message.saveChanges();
        return message;
	}
}