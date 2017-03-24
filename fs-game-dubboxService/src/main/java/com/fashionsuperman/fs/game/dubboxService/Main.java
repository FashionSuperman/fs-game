package com.fashionsuperman.fs.game.dubboxService;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * dubbo服务启动测试类
 * @description 
 * @author FashionSuperman
 * @date 2017年3月22日 下午1:54:27
 * @version 1.0
 */
@SuppressWarnings("resource")
public class Main 
{
    public static void main( String[] args ) throws IOException
    {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"classpath:spring/spring-context.xml"});
    	applicationContext.start();
		System.in.read();
    }
}
