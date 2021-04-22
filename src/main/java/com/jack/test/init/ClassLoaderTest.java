package com.jack.test.init;

import java.net.URL;

/**
* @Description:    
* @Author:         ZhangQiang
* @CreateDate:     2020/8/15 17:51
*/
public class ClassLoaderTest {

	public static void main(String[] args) {
		URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
		for(URL url : urls){
			System.out.println(url.toExternalForm());
		}
	}
}