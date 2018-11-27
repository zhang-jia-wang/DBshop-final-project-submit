package com.website.zip;

import org.testng.annotations.Test;

public class YasuoTest {

	public class TestZip {
		@Test
		 public void nihao(){  
			//压缩后的存放路径
	        Zip zc = new  Zip("F:\\git\\test-practice\\auto2018\\test-output\\html.zip");  
	    //压缩谁
	        zc.compressExe("F:\\git\\test-practice\\auto2018\\test-output\\html");
		 }  
	}

}
