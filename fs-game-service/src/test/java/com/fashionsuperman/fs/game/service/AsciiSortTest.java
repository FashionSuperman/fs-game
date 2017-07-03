package com.fashionsuperman.fs.game.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import com.fashionSuperman.fs.core.util.XMLUtil;
import com.fashionsuperman.fs.game.service.common.WXSignUtil;
import com.fashionsuperman.fs.game.service.trade.message.MesUnifiedorder;


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
	
	@Test
	public void testMd5Enc() throws IllegalArgumentException, IllegalAccessException{
		MesUnifiedorder mesUnifiedorder = new MesUnifiedorder();
		
		mesUnifiedorder.setAppid("1111");
		mesUnifiedorder.setMch_id("222");
		mesUnifiedorder.setDevice_info("WEB");
		String nonce_str = UUID.randomUUID().toString().replace("-", "");
		mesUnifiedorder.setNonce_str(nonce_str);
		
		String result = WXSignUtil.sign(mesUnifiedorder, "fs");
		System.out.println(result);
	}
	
	@Test
	public void testXml() throws IllegalArgumentException, IllegalAccessException{
		MesUnifiedorder mesUnifiedorder = new MesUnifiedorder();
		
		mesUnifiedorder.setAppid("1111");
		mesUnifiedorder.setMch_id("222");
		mesUnifiedorder.setDevice_info("WEB");
		String nonce_str = UUID.randomUUID().toString().replace("-", "");
		mesUnifiedorder.setNonce_str(nonce_str);
		
		mesUnifiedorder.setSign_type("MD5");
		
		mesUnifiedorder.setBody("hello");
		//encode
		String result = WXSignUtil.sign(mesUnifiedorder, "fs");
		mesUnifiedorder.setSign(result);
		
		
		
		
		String xml = XMLUtil.convertToXml(mesUnifiedorder).replace("&lt;", "<").replace("&gt;", ">");
		System.out.println(xml);
	}
	
	@Test
	public void testUnXml(){
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><xml><appid>1111</appid><mch_id>222</mch_id><device_info>WEB</device_info><nonce_str>380b73c53255448aa05005aaaa8dd5f8</nonce_str><sign>14C0CD18E35DE230FD6A82784AB59CDF</sign><sign_type>MD5</sign_type><body>&lt;![CDATA[hello]]&gt;</body></xml>";
		MesUnifiedorder rs = (MesUnifiedorder) XMLUtil.convertXmlStrToObject(MesUnifiedorder.class, xml);
		
		System.out.println(rs.getBody());
	}
}
