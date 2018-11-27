package com.webtest.utils;

import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
public class PhotoMail {
	//�����˵�ַ
    public static String senderAddress = "test2014123@126.com";
    //�ռ��˵�ַ
    public static String recipientAddress = "945248846@qq.com";
    //�������˻���
    public static String senderAccount = "test2014123@126.com";
    //�������˻�����
    public static String senderPassword = "123456abcd";
	     
	    public static void main(String[] args) throws Exception {
	        //1�������ʼ��������Ĳ�������
	        Properties props = new Properties();
	        //�����û�����֤��ʽ
	        props.setProperty("mail.smtp.auth", "true");
	        //���ô���Э��
	        props.setProperty("mail.transport.protocol", "smtp");
	        //���÷����˵�SMTP��������ַ
	        props.setProperty("mail.smtp.host", "smtp.126.com");
	        //2��������������Ӧ�ó�������Ļ�����Ϣ�� Session ����
	        Session session = Session.getInstance(props);
	        //���õ�����Ϣ�ڿ���̨��ӡ����
	        session.setDebug(true);
	        //3�������ʼ���ʵ������
	        Message msg = getMimeMessage(session);
	        //4������session�����ȡ�ʼ��������Transport
	        Transport transport = session.getTransport();
	        //���÷����˵��˻���������
	        transport.connect(senderAccount, senderPassword);
	        //�����ʼ��������͵������ռ��˵�ַ��message.getAllRecipients() ��ȡ�������ڴ����ʼ�����ʱ��ӵ������ռ���, ������, ������
	        transport.sendMessage(msg,msg.getAllRecipients());
	         
	        //5���ر��ʼ�����
	        transport.close();
	    }
	     
	    /**
	     * ��ô���һ���ʼ���ʵ������
	     * @param session
	     * @return
	     * @throws MessagingException
	     * @throws AddressException
	     */
	    public static MimeMessage getMimeMessage(Session session) throws Exception{
	        //1.����һ���ʼ���ʵ������
	        MimeMessage msg = new MimeMessage(session);
	        //2.���÷����˵�ַ
	        msg.setFrom(new InternetAddress(senderAddress));
	        /**
	         * 3.�����ռ��˵�ַ���������Ӷ���ռ��ˡ����͡����ͣ�����������һ�д�����д����
	         * MimeMessage.RecipientType.TO:����
	         * MimeMessage.RecipientType.CC������
	         * MimeMessage.RecipientType.BCC������
	         */
	        msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(recipientAddress));
	        //4.�����ʼ�����
	        msg.setSubject("�ʼ�����(����ͼƬ�͸���)","UTF-8");
	         
	        //�����������ʼ�����
	        //msg.setContent("�򵥵Ĵ��ı��ʼ���", "text/html;charset=UTF-8");
	         
	        // 5. ����ͼƬ"�ڵ�"
	        MimeBodyPart image = new MimeBodyPart();
	        // ��ȡ�����ļ�	        
	        DataHandler dh = new DataHandler(new FileDataSource("src\\mailTestPic.png"));
	     
	        // ��ͼƬ������ӵ�"�ڵ�"
	        image.setDataHandler(dh);
	        // Ϊ"�ڵ�"����һ��Ψһ��ţ����ı�"�ڵ�"�����ø�ID��
	        image.setContentID("mailTestPic");    
	         
	        // 6. �����ı�"�ڵ�"
	        MimeBodyPart text = new MimeBodyPart();
	        // �������ͼƬ�ķ�ʽ�ǽ�����ͼƬ�������ʼ�������, ʵ����Ҳ������ http ���ӵ���ʽ�������ͼƬ
//	        text.setContent("����һ��ͼƬ<br/><a href='http://www.cnblogs.com/ysocean/p/7666061.html'><img src='cid:mailTestPic'/></a>", "text/html;charset=UTF-8");
	        text.setContent("����һ��ͼƬ<br/><img src='cid:mailTestPic'/>", "text/html;charset=UTF-8");
	        // 7. ���ı�+ͼƬ������ �ı� �� ͼƬ"�ڵ�"�Ĺ�ϵ���� �ı� �� ͼƬ"�ڵ�"�ϳ�һ�����"�ڵ�"��
	        MimeMultipart mm_text_image = new MimeMultipart();
	        mm_text_image.addBodyPart(text);
	        mm_text_image.addBodyPart(image);
	        mm_text_image.setSubType("related");    // ������ϵ
	         
	        // 8. �� �ı�+ͼƬ �Ļ��"�ڵ�"��װ��һ����ͨ"�ڵ�"
	        // ������ӵ��ʼ��� Content ���ɶ�� BodyPart ��ɵ� Multipart, ����������Ҫ���� BodyPart,
	        // ����� mailTestPic ���� BodyPart, ����Ҫ�� mm_text_image ��װ��һ�� BodyPart
	        MimeBodyPart text_image = new MimeBodyPart();
	        text_image.setContent(mm_text_image);
	         
	        // 9. ��������"�ڵ�"
	        MimeBodyPart attachment = new MimeBodyPart();
	        // ��ȡ�����ļ�
	        DataHandler dh2 = new DataHandler(new FileDataSource("src\\mailTestDoc.docx"));
	        // ������������ӵ�"�ڵ�"
	        attachment.setDataHandler(dh2);
	        // ���ø������ļ�������Ҫ���룩
	        attachment.setFileName(MimeUtility.encodeText(dh2.getName()));       
	         
	        // 10. ���ã��ı�+ͼƬ���� ���� �Ĺ�ϵ���ϳ�һ����Ļ��"�ڵ�" / Multipart ��
	        MimeMultipart mm = new MimeMultipart();
	        mm.addBodyPart(text_image);
	        mm.addBodyPart(attachment);     // ����ж�����������Դ������������
	        mm.setSubType("mixed");         // ��Ϲ�ϵ
	 
	        // 11. ���������ʼ��Ĺ�ϵ�������յĻ��"�ڵ�"��Ϊ�ʼ���������ӵ��ʼ�����
	        msg.setContent(mm);
	        //�����ʼ��ķ���ʱ��,Ĭ����������
	        msg.setSentDate(new Date());
	         
	        return msg;
	    }
//	    @Test(description="��������iframe֮����л�")
//	    public void changeIframe(){
//	    	Reporter.log("lalala �Ҿ��������־������");
//	    	Assert.assertTrue(true);
//	    }
}

