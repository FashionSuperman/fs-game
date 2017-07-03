package com.fashionsuperman.fs.game.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;


public class AsciiSortTest {
	@Test
	public void testAsciiSort(){
		String appid = "appid";
		String mch_id = "mch_id";
		String device_info = "device_info";
		String body = "body";
		String nonce_str = "nonce_str";
		
		
		String[] arr = {appid,mch_id,device_info,body,nonce_str};
		
		Arrays.sort(arr);
		
		List<String> temp = Arrays.asList(arr);
		temp.stream().forEach(s -> System.out.println(s));
	}
}
