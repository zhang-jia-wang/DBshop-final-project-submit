package com.website.zip;

import org.testng.annotations.Test;

public class YasuoTest {

	public class TestZip {
		@Test
		 public void nihao(){  
			//ѹ����Ĵ��·��
	        Zip zc = new  Zip("F:\\git\\test-practice\\auto2018\\test-output\\html.zip");  
	    //ѹ��˭
	        zc.compressExe("F:\\git\\test-practice\\auto2018\\test-output\\html");
		 }  
	}

}
