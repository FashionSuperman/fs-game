package com.fashionsuperman.fs.game.facet.trade.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fashionsuperman.fs.game.facet.AdapterCDATA;


@XmlAccessorType(XmlAccessType.FIELD)  
@XmlRootElement(name = "xml")  
public class ResPayCallback {
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String return_code;
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String return_msg;
	@XmlElement
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	@XmlElement
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	
	
}
