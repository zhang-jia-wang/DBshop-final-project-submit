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
	 * �շ�����������Ϣ
	 */
	public static String myEmailAccount = "chen_test@126.com";
	public static String myEmailPassword = "abc123";
	public static String myEmailSMTPHost = "smtp.126.com";
	public static String receiveMailAccount = "2734828072@qq.com";
	public static String mailtheme = "����һ�������ʼ�";
	public static Date data = new Date();
	/*
	 * ��д���뿪ʼ
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
		// 5. ����ͼƬ���ڵ㡱
		MimeBodyPart image = new MimeBodyPart();
		DataHandler dh = new DataHandler(new FileDataSource(local_pic)); // ��ȡ�����ļ�
		image.setDataHandler(dh); // ��ͼƬ������ӵ����ڵ㡱
		image.setContentID("image_page_one"); // Ϊ���ڵ㡱����һ��Ψһ��ţ����ı����ڵ㡱�����ø�ID��
		// 6. �����ı����ڵ㡱
		MimeBodyPart text = new MimeBodyPart();
		// �������ͼƬ�ķ�ʽ�ǽ�����ͼƬ�������ʼ�������, ʵ����Ҳ������ http ���ӵ���ʽ�������ͼƬ
		text.setContent(describe+"<br/><img src='cid:image_page_one'/><br/>", "text/html;charset=UTF-8");
		// 7. ���ı�+ͼƬ������ �ı� �� ͼƬ ���ڵ㡱�Ĺ�ϵ���� �ı� �� ͼƬ ���ڵ㡱�ϳ�һ����ϡ��ڵ㡱��
		MimeMultipart mm_text_image = new MimeMultipart();
		mm_text_image.addBodyPart(text);
		mm_text_image.addBodyPart(image);
		mm_text_image.setSubType("related"); // ������ϵ
		// 8. �� �ı�+ͼƬ �Ļ�ϡ��ڵ㡱��װ��һ����ͨ���ڵ㡱
		MimeBodyPart text_image = new MimeBodyPart();
		text_image.setContent(mm_text_image);
		
		return text_image;
	}

	public static MimeBodyPart createBodyAttach(String local_att) throws Exception {
		MimeBodyPart attachment = new MimeBodyPart();
		DataHandler dh2 = new DataHandler(new FileDataSource(local_att)); // ��ȡ�����ļ�
		attachment.setDataHandler(dh2); // ������������ӵ����ڵ㡱
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
		
		//�������ʼ��������ռ��ˣ������˺��ʼ�����
		MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myEmailAccount,"UTF-8"));
        message.addRecipient(RecipientType.TO, new InternetAddress(receiveMailAccount, "UTF-8"));
        message.setSubject(mailtheme, "UTF-8");// 4. Subject: �ʼ�����
        
        int length = param.length;
        MimeMultipart mm = new MimeMultipart();
        for (int i = 0;i<length;i++) {
        	mm.addBodyPart(param[i]);
        }

		// 11. ���������ʼ��Ĺ�ϵ�������յĻ�ϡ��ڵ㡱��Ϊ�ʼ���������ӵ��ʼ�����
        message.setContent(mm);
        message.setSentDate(data);  // 12. ���÷���ʱ��
        message.saveChanges();  // 13. �����������������
        return message;
	}
}