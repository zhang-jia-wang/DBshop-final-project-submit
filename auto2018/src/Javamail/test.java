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


public class test {
	/*
	 * 收发件人配置信息
	 */
	public static String myEmailAccount = "chen_test@126.com";
	public static String myEmailPassword = "abc123";
	public static String myEmailSMTPHost = "smtp.126.com";
	public static String receiveMailAccount = "2734828072@qq.com";
	public static String mailtheme = "这是一个测试邮件";
	public static Date data = new Date();
	/*
	 * 编写代码开始
	 */
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
		// 5. 创建图片“节点”
		MimeBodyPart image = new MimeBodyPart();
		DataHandler dh = new DataHandler(new FileDataSource(local_pic)); // 读取本地文件
		image.setDataHandler(dh); // 将图片数据添加到“节点”
		image.setContentID("image_page_one"); // 为“节点”设置一个唯一编号（在文本“节点”将引用该ID）
		// 6. 创建文本“节点”
		MimeBodyPart text = new MimeBodyPart();
		// 这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
		text.setContent(describe+"<br/><img src='cid:image_page_one'/><br/>", "text/html;charset=UTF-8");
		// 7. （文本+图片）设置 文本 和 图片 “节点”的关系（将 文本 和 图片 “节点”合成一个混合“节点”）
		MimeMultipart mm_text_image = new MimeMultipart();
		mm_text_image.addBodyPart(text);
		mm_text_image.addBodyPart(image);
		mm_text_image.setSubType("related"); // 关联关系
		// 8. 将 文本+图片 的混合“节点”封装成一个普通“节点”
		MimeBodyPart text_image = new MimeBodyPart();
		text_image.setContent(mm_text_image);
		
		return text_image;
	}

	public static MimeBodyPart createBodyAttach(String local_att) throws Exception {
		MimeBodyPart attachment = new MimeBodyPart();
		DataHandler dh2 = new DataHandler(new FileDataSource(local_att)); // 读取本地文件
		attachment.setDataHandler(dh2); // 将附件数据添加到“节点”
		attachment.setFileName(MimeUtility.encodeText(dh2.getName()));
		return attachment;
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
		
		//创建新邮件，设置收件人，发件人和邮件主题
		MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myEmailAccount,"UTF-8"));
        message.addRecipient(RecipientType.TO, new InternetAddress(receiveMailAccount, "UTF-8"));
        message.setSubject(mailtheme, "UTF-8");// 4. Subject: 邮件主题
        
        int length = param.length;
        MimeMultipart mm = new MimeMultipart();
        for (int i = 0;i<length;i++) {
        	mm.addBodyPart(param[i]);
        }

		// 11. 设置整个邮件的关系（将最终的混合“节点”作为邮件的内容添加到邮件对象）
        message.setContent(mm);
        message.setSentDate(data);  // 12. 设置发件时间
        message.saveChanges();  // 13. 保存上面的所有设置
        return message;
	}
}