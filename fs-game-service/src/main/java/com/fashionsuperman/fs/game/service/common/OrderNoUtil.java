package com.fashionsuperman.fs.game.service.common;

import java.util.Date;
import java.util.Random;

import com.fashionSuperman.fs.core.util.DateUtil;

/**
 * 
 * @description 内部订单号管理
 * @author FashionSuperman
 * @date 2017年7月3日 下午4:00:36
 * @version 1.0
 */
public class OrderNoUtil {
	public static String generateOrderNo(){
		String result = "";
		
		result = DateUtil.getDateTime("yyyyMMddHHmmss", new Date());
		
		//生成四位随机数
		result += getFourRandom();
		
		return result;
	}
	
	
	
	public static String getFourRandom(){
        Random random = new Random();
        String fourRandom = random.nextInt(10000) + "";
        int randLength = fourRandom.length();
        if(randLength<4){
          for(int i=1; i<=4-randLength; i++)
              fourRandom = "0" + fourRandom  ;
      }
        return fourRandom;
    } 
}
