package Javamail;

import javax.mail.internet.MimeMessage;

import org.testng.annotations.Test;



public class test1 {
	@Test
	public void tests() throws Exception {
		test a=new test();
		MimeMessage maildetail = a.mailDetails(a.createBodyPic("��������ͼƬ��һ����ͼ","F:\\git\\test-practice\\auto2018\\material\\1120.png"),a.createBodyTxt("pic1"));
		a.sendmail(maildetail);
	}
}



