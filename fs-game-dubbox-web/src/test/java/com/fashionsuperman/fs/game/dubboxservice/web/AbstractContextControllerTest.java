package com.fashionsuperman.fs.game.dubboxservice.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fashionSuperman.fs.core.mvc.filter.FormatDataFilter;
import com.fashionSuperman.fs.core.util.AesCryptography;
import com.fashionSuperman.fs.core.util.StringUtil;


@WebAppConfiguration  
@ContextConfiguration(locations = { "classpath:/spring/spring-context.xml"})
public class AbstractContextControllerTest {
	
	private static Logger logger = LoggerFactory.getLogger(AbstractContextControllerTest.class);
	
	@Autowired
    protected WebApplicationContext applicationContext;
	
	public static String BASE_JSON_DIR = System.getProperty("user.dir") + "/src/test/java/";
	private HttpHeaders httpHeaders = new HttpHeaders();
	/**
	 * 添加指定的controller
	 * @param mvc controller
	 * @return
	 */
	public MockMvc buildMockMvc(Object mvc) {
		try{
//			String originalData = "projectCode=YFZ&timestamp=" + System.currentTimeMillis();
//			String key = AesCryptography.generateKey();
//			logger.info(key);
//			String encryptData = AesCryptography.encodeData(originalData, key);
			//TODO 模拟添加header信息
//			httpHeaders.add(AuthConstant.PARAM_PROJECT_CODE, "YFZ");
//			httpHeaders.add(AuthConstant.PARAM_ENCRYPT_DATA, encryptData);
		}catch(Exception e){
			logger.info(e.getMessage(),e);
		}
		
		FormatDataFilter dataFilter = new FormatDataFilter();
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(mvc).addFilters(dataFilter)
									.build();  
		return mockMvc;
	}
	
	public MockMvc buildMockMvcWithOutHeaders(Object mvc) {
		FormatDataFilter dataFilter = new FormatDataFilter();
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(mvc).addFilters(dataFilter)
									.build();  
		return mockMvc;
	}
	
	/**
	 * 默认添加所有的controller测试
	 * @return
	 */
	public MockMvc buildMockMvc() {
		try{
//			String originalData = "projectCode=YFZ&timestamp=" + System.currentTimeMillis();
//			String key = AesCryptography.generateKey();
//			logger.info(key);
//			String encryptData = AesCryptography.encodeData(originalData, key);
			//TODO 模拟添加头信息
//			httpHeaders.add(AuthConstant.PARAM_PROJECT_CODE, "YFZ");
//			httpHeaders.add(AuthConstant.PARAM_ENCRYPT_DATA, encryptData);
		}catch(Exception e){
			logger.info(e.getMessage(),e);
		}
		
		FormatDataFilter dataFilter = new FormatDataFilter();
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).addFilters(dataFilter).build(); 
		return mockMvc;
	}
	
	/** 
     * mock 
     *  
     * @param uri 
     * @param json 
     * @return 
     * @throws UnsupportedEncodingException 
     * @throws Exception 
     */  
    public String mock(MockMvc mvc, String uri, String jsonFile)  
            throws UnsupportedEncodingException, Exception {
    	String data = "";
    	if(StringUtil.isNotEmpty(jsonFile)){
    		data = this.readJsonFile(jsonFile);
    	}
    	
//    	ObjectMapper mapper = new ObjectMapper();
//    	JsonNode node = mapper.readTree(data);
//    	String requestData = node.get("requestData").toString();
    	
    	
        return mvc  
                .perform(  
                        post(uri, "json").headers(this.httpHeaders).characterEncoding("UTF-8")  
                                .contentType(MediaType.APPLICATION_JSON)  
                                .content(data.getBytes())).andReturn()  
                .getResponse().getContentAsString();  
    }  
    
    /**
     * 读取json文件到String
     * @param fileName
     * @return
     */
    public String readJsonFile(String fileName){
    	StringBuffer sb = new StringBuffer();
    	Reader reader = null;
    	BufferedReader br = null;
    	try {
    		reader = new FileReader(fileName);
    		br = new BufferedReader(reader);
    		String data = null;
		    while ((data = br.readLine()) != null) {
		    sb.append(data);
	     }
    	 } catch (IOException e) {
    		e.printStackTrace();
    	 } finally {
    		 try {
    			 reader.close();
    			 br.close();
		     } catch (Exception e) {
		    	 e.printStackTrace();
		     }
    	 } 
    	return sb.toString();
    }
    
    
}
