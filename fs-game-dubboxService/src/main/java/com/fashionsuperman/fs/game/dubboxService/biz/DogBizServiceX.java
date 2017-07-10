package com.fashionsuperman.fs.game.dubboxService.biz;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionSuperman.fs.core.util.JedisUtil;
import com.fashionsuperman.fs.game.dao.entity.LoginCount;
import com.fashionsuperman.fs.game.dao.entity.NoLoginCount;
import com.fashionsuperman.fs.game.dao.entity.Package;
import com.fashionsuperman.fs.game.dao.entity.User;
import com.fashionsuperman.fs.game.dao.mapper.LoginCountMapper;
import com.fashionsuperman.fs.game.dao.mapper.NoLoginCountMapper;
import com.fashionsuperman.fs.game.dao.mapper.PackageMapper;
import com.fashionsuperman.fs.game.dao.mapper.UserMapper;
import com.fashionsuperman.fs.game.dubboxService.common.DubboxCookieComponent;
import com.fashionsuperman.fs.game.facet.biz.DogBizServiceI;
import com.fashionsuperman.fs.game.facet.biz.message.MesApplyWXPay;
import com.fashionsuperman.fs.game.facet.biz.message.MesJudgeCanPlay;
import com.fashionsuperman.fs.game.facet.biz.message.MesSign;
import com.fashionsuperman.fs.game.facet.biz.message.ResApplyWXPay;
import com.fashionsuperman.fs.game.facet.biz.message.ResSign;
import com.fashionsuperman.fs.game.facet.biz.message.StatusCode;
import com.fashionsuperman.fs.game.facet.user.message.UserLogin;
import com.fashionsuperman.fs.game.service.biz.DogBizService;
import com.fashionsuperman.fs.game.service.common.UtilConstant;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/DogBiz")
@Produces(ContentType.APPLICATION_JSON_UTF_8)
@Service("DogBizServiceX")
public class DogBizServiceX implements DogBizServiceI{
	@Autowired
	private DogBizService dogBizService;
	@Value("#{utilProperties.canPlayCommodityId}")
	private Long canPlayCommodityId;
	@Value("#{utilProperties.permanentCommodityId}")
	private Long permanentCommodityId;
	@Autowired
	private JedisUtil jedisUtil;
	@Autowired
	private DubboxCookieComponent dubboxCookieComponent;
	@Autowired
	private UtilConstant UtilConstant;
	@Autowired
	private NoLoginCountMapper noLoginCountMapper;
	@Autowired
	private LoginCountMapper loginCountMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PackageMapper packageMapper;
	
	private Logger logger = LogManager.getLogger(DogBizServiceX.class);
	
	/**
	 * 判断用户是否可以继续游戏
	 * 0失败
	 * 1成功
	 * @throws IOException 
	 */
	@POST
	@Path("/judgeCanPlay")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public StatusCode judgeCanPlay(MesJudgeCanPlay mesJudgeCanPlay) throws IOException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) RpcContext.getContext().getResponse();
		StatusCode result = new StatusCode();
//		if(mesJudgeCanPlay == null){
//			throw new BizException(com.fashionSuperman.fs.core.constant.StatusCode.FAILURE_AUTHENTICATE, "请求参数不能为空");
//		}
//		Long userid = mesJudgeCanPlay.getUserid();
//		if(userid == null || userid < 0){
//			throw new BizException(com.fashionSuperman.fs.core.constant.StatusCode.FAILURE_AUTHENTICATE, "userid不能为空");
//		}
		
		//获取登录信息
		UserLogin user = dubboxCookieComponent.getLoginUser();
		//优先判断 是否允许不登录可以玩
		if("1".equals(UtilConstant.shouldLogin)){//必须登录
			if(user == null){//未登录 重定向到登录
				httpServletResponse.sendRedirect(UtilConstant.ifyoudogindex);
				return result;
			}
		}
		
		if(user == null){//没有登录
			//判断试玩次数
			Cookie cookie = dubboxCookieComponent.getCookie("userTempId");
			if(cookie == null){//用户第一次访问  或者清除了cookie
				//生成一个随机id  保存到数据库
				String userTempId = UUID.randomUUID().toString().replace("-", "");
				NoLoginCount noLoginCount = new NoLoginCount();
				noLoginCount.setRandomid(userTempId);
				noLoginCount.setNumber(3);
				noLoginCount.setUpdatetime(new Date());
				noLoginCountMapper.insert(noLoginCount);
				//返回结果  回写cookie
				cookie = new Cookie("userTempId", userTempId);
				cookie.setMaxAge(30*24*60*60);
				cookie.setPath("/");
				httpServletResponse.addCookie(cookie);
				result.setStatus("1");
				logger.debug("临时用户 " + userTempId + " 未登录业务判断成功");
				return result;
			}else{
				//查询该用户的临时次数,判断是否超过12小时,超过先更新次数
				String userTempId = cookie.getValue();
				NoLoginCount noLoginCount = noLoginCountMapper.selectByPrimaryKey(userTempId);
				if(noLoginCount == null){
					noLoginCount = new NoLoginCount();
					noLoginCount.setRandomid(userTempId);
					noLoginCount.setNumber(3);
					noLoginCount.setUpdatetime(new Date());
					noLoginCountMapper.insert(noLoginCount);
					result.setStatus("1");
					logger.debug("临时用户 " + userTempId + " 未登录业务判断成功");
					return result;
				}
				
				Date date = noLoginCount.getUpdatetime();
				if( ( (new Date().getTime()) - date.getTime() ) > 12 * 60 * 60 * 1000){
					noLoginCount.setNumber(3);
					noLoginCount.setUpdatetime(new Date());
					noLoginCountMapper.updateByPrimaryKeySelective(noLoginCount);
					result.setStatus("1");
					return result;
				}
				
				int number = noLoginCount.getNumber();
				if(number > 0){
					number--;
					noLoginCount.setNumber(number);
					noLoginCountMapper.updateByPrimaryKeySelective(noLoginCount);
					result.setStatus("1");
					logger.debug("临时用户 " + userTempId + " 未登录业务判断成功");
					return result;
				}else{
					result.setStatus("2");
					logger.debug("临时用户 " + userTempId + " 未登录业务判断失败");
					return result;
				}
			}
			
		}else{//已登录
			//先判断是否有永久免费道具 , 再判断免费次数 , 最后判断购买次数
			
			Map<String, Object> paramMap;
			Package packageUser;
			
			//查询该用户信息
			User UserInDb = userMapper.selectByForeignId(user.getForeighid());
			
			//首先判断是否有永久道具
			paramMap = new HashMap<>();
			paramMap.put("userid", UserInDb.getUserid());
			paramMap.put("commodityid", permanentCommodityId);
			dogBizService.getUserFooCommodityNum(paramMap);
			packageUser = dogBizService.getUserFooCommodityNum(paramMap);
			if(packageUser != null && packageUser.getNumber() != null && packageUser.getNumber() > 0){
				result.setStatus("1");
				//如果有永久道具,直接返回
				return result;
			}
			
			
			//没有永久道具判断每日免费次数
			LoginCount loginCount = loginCountMapper.selectByPrimaryKey(UserInDb.getUserid());
			if(loginCount == null){//没有该用户的登录免费次数记录
				loginCount = new LoginCount();
				loginCount.setUserid(UserInDb.getUserid());
				loginCount.setNumber(15);
				loginCount.setUpdatedate(new Date());
				loginCountMapper.insert(loginCount);
				result.setStatus("1");
				return result;
			}
			
			Date date = loginCount.getUpdatedate();
			if( ( (new Date().getTime()) - date.getTime() ) > 12 * 60 * 60 * 1000){
				loginCount.setNumber(15);
				loginCount.setUpdatedate(new Date());
				loginCountMapper.updateByPrimaryKeySelective(loginCount);
				result.setStatus("1");
				return result;
			}
			
			int number = loginCount.getNumber();
			if(number > 0){
				number--;
				loginCount.setNumber(number);
				loginCountMapper.updateByPrimaryKeySelective(loginCount);
				result.setStatus("1");
				return result;
			}else{//用户每日免费次数用完 , 判断购买次数
				//没有永久道具,判断分次道具
				paramMap = new HashMap<>();
				paramMap.put("userid", UserInDb.getUserid());
				paramMap.put("commodityid", canPlayCommodityId);
				
				
				packageUser = dogBizService.getUserFooCommodityNum(paramMap);
				
				if(packageUser != null && packageUser.getNumber() != null && packageUser.getNumber() > 0){
					//更新购买次数
					int packageNumber = packageUser.getNumber();
					packageUser.setNumber(packageNumber-1);
					
					packageMapper.updateByPrimaryKeySelective(packageUser);
					
					
					result.setStatus("1");
				}else{
					result.setStatus("3");
				}
				
				return result;
			}
			
		}
	}
	
	
	/**
	 * 签名
	 */
	@POST
	@Path("/sign")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public ResSign sign(MesSign mesSign){
		return dogBizService.sign(mesSign);
	}

	/**
	 * 调起微信支付
	 */
	@POST
	@Path("/applyWXPay")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public ResApplyWXPay applyWXPay(MesApplyWXPay mesApplyWXPay){
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) RpcContext.getContext().getRequest();
		
		String ip = httpServletRequest.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = httpServletRequest.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = httpServletRequest.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = httpServletRequest.getRemoteAddr();
	    }
	    ip = ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
		
		Cookie[] cookies = httpServletRequest.getCookies();
		Cookie cookie = null;
		if (cookies == null || cookies.length == 0) {
			throw new BizException(com.fashionSuperman.fs.core.constant.StatusCode.FAILURE_AUTHENTICATE, "未登录");
		}
		for (Cookie c : cookies) {
			if ("sessionId".equals(c.getName())) {
				cookie = c;
				break;
			}
		}
		
		if(cookie == null){
			throw new BizException(com.fashionSuperman.fs.core.constant.StatusCode.FAILURE_AUTHENTICATE, "未登录");
		}
		
		String sessionId = cookie.getValue();
		
		//从redis查询该用户
		String userInRedis = this.jedisUtil.STRINGS.get(sessionId);
//		String userInRedis = this.jedisUtil.STRINGS.get("oR8r7t5-S45elI3FJjX17v86sYcU");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		User user = null;
		try {
			user = mapper.readValue(userInRedis, User.class);
		} catch (IOException e) {
			throw new BizException(com.fashionSuperman.fs.core.constant.StatusCode.FAILURE_AUTHENTICATE, "未登录");
		}
		
		mesApplyWXPay.setOpenid(user.getForeighid());
		
		ResApplyWXPay result = new ResApplyWXPay();
		result = dogBizService.applyWXPay(mesApplyWXPay);
		return result;
	}
}
