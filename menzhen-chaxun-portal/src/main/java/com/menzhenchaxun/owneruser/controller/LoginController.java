package com.menzhenchaxun.owneruser.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.menzhenchaxun.common.annotation.Log;
import com.menzhenchaxun.common.controller.BaseController;
import com.menzhenchaxun.common.utils.ShiroUtils;
import com.menzhenchaxun.owneruser.comment.GenerateCode;
import com.menzhenchaxun.owneruser.domain.OwnerUserDO;
import com.menzhenchaxun.owneruser.service.OwnerUserService;


@RestController
public class LoginController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

   
    @Autowired
    OwnerUserService userService;
   
    @Log("密码登录")  
	@PostMapping("/loginP")
    Map<String, Object> loginP(String phone, String password) {
 	    Map<String, Object> message = new HashMap<>();
	   	UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
	   	Subject subject = SecurityUtils.getSubject();
	   		try {
	   			Map<String, Object> mapP = new HashMap<String, Object>();
	   			mapP.put("username", phone);
	   			boolean flag = userService.exit(mapP);
	   			if (!flag) {
	   				message.put("msg","该手机号码未注册");
	   			}
	   			OwnerUserDO udo= userService.getbyname(phone);
	   			if(udo.getDeleteFlag()==0){
	   				message.put("msg","禁止登录，请联系客服");
	   			}
	   			subject.login(token);
	   			udo.setLoginTime(new Date());
	   			userService.update(udo);
	   			
                message.put("id", udo.getId());
                message.put("nickname", udo.getNickname());
                message.put("heardUrl", udo.getHeardUrl());
                message.put("loginTime", udo.getLoginTime());
	   			message.put("msg","登录成功");
	   		} catch (AuthenticationException e) {
	   			message.put("msg","用户或密码错误");
	   		}
	    	return message;
    }
   
   /* @Log("发送验证码")
    @PostMapping("/captcha")
    Map<String, String> captcha(String phone, String type) {
        Map<String, String> message = new HashMap<>();
        try {
            if (phone == null || "".equals(phone)) {
                message.put("msg", "手机号码不能为空");
            } else {
                SMSTemplate contentType = SMSContent.ZHUCE;
                if ("0".equals(type)) {
                    contentType = SMSContent.ZHUCE;//注册
                }
                if ("1".equals(type)) {
                    contentType = SMSContent.LOGIN;//登录
                }
                
                Map<String, Object> map = sMSService.sendCodeNumber(SMSPlatform.yihuitong, phone, contentType);
                if (map == null) {
                    message.put("msg", "验证码发送出现问题,请三分钟后再试");
                } else {
	                String code = map.get("randomCode").toString();
	                Subject subject = SecurityUtils.getSubject();
	                subject.getSession().setAttribute("sys.login.check.code", phone + code);
	                message.put("msg", "发送成功");
	                message.put("sessionId",subject.getSession().getId().toString());
               }
            }
        } catch (Exception e) {
            logger.info("SMS==================验证码发送出现问题========" + phone + "======");
            message.put("code", "1");
            message.put("msg", "验证码发送出现问题,请三分钟后再试");
        }
        return message;
    }*/
    
    /*
    pom.xml
    <dependency>
      <groupId>com.aliyun</groupId>
      <artifactId>aliyun-java-sdk-core</artifactId>
      <version>4.0.3</version>
    </dependency>
    */
    
    /**
     * @param phone 手机号
     * @param type  类型 0：注册   1：登录	 2：绑定手机号  3：更换绑定手机号
     * @说明 发送验证码
     */
    @Log("发送验证码")
	@PostMapping("/captcha")
       static Map<String, String> getSms(String phone,String type){
    		Map<String, String> message = new HashMap<>();
    		
    		try { 
    			if (phone == null || "".equals(phone)) {
	                message.put("msg", "手机号码不能为空");
	            }else {
	            	DefaultProfile profile = DefaultProfile.getProfile("default", "LTAI8vH63loOPvE8", "Cg5DIuEkqYjIJ1dYoU7FNsMapAPNtz");
		            IAcsClient client = new DefaultAcsClient(profile);
		            
		            Integer templateParam = (int)((Math.random()*9+1)*100000);
		            System.out.println("============验证码===================="+templateParam);
		            
		            CommonRequest request = new CommonRequest();
		            //request.setProtocol(ProtocolType.HTTPS);
		            request.setMethod(MethodType.POST);
		            request.setDomain("dysmsapi.aliyuncs.com");
		            request.setVersion("2017-05-25");
		            request.setAction("SendSms");
		             
		            request.putQueryParameter("PhoneNumbers", phone);
		           
		            request.putQueryParameter("SignName", "询识");
		            if ("0".equals(type)) {
		            	request.putQueryParameter("TemplateCode", "SMS_170352724");
	                }else if ("1".equals(type)) {			//登陆
	                	 request.putQueryParameter("TemplateCode", "SMS_170347750");
	                }else if ("2".equals(type)){			//绑定手机号
	                	request.putQueryParameter("TemplateCode", "SMS_170352719");
	                }else if ("3".equals(type)){			//更换绑定手机号
	                	request.putQueryParameter("TemplateCode", "SMS_170347752");
	                }
		           
		            request.putQueryParameter("TemplateParam",  "{\"code\":\""+templateParam+"\"}");
	            
	            
	                CommonResponse response = client.getCommonResponse(request);
	                
	                Subject subject = SecurityUtils.getSubject();
	                subject.getSession().setAttribute("sys.login.check.code", phone + templateParam);
	                message.put("msg", "发送成功");
	                message.put("sessionId",subject.getSession().getId().toString());
	            } 
            } catch (ServerException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                e.printStackTrace();
            }
            return message;
    } 
    
    
    /**
     * 用于登录注册发送验证码
     * */
    @Log("判断是否注册")
    @PostMapping("/isZhuce")
    Map<String, String> isZhuce(String phone){
    	Map<String, String> message = new HashMap<>();
    	 Map<String, Object> mapP = new HashMap<String, Object>();
         mapP.put("username", phone);
         boolean flag = userService.exit(mapP);
         
         if(flag){
        	return getSms(phone,"1");
         }else if(!flag){
        	return getSms(phone,"0");
         }else{
        	message.put("msg", "发送失败");
        	return message;
         }
    }
    
    
	/*
	 * @Log("验证码登录")
	 * 
	 * @PostMapping("/loginC") Map<String, Object> loginC(String phone, String
	 * codenum) { Map<String, Object> message = new HashMap<>(); Map<String, Object>
	 * map = new HashMap<>(); String msg = ""; Subject subject =
	 * SecurityUtils.getSubject();
	 * 
	 * Object object = subject.getSession().getAttribute("sys.login.check.code");
	 * try { if (object != null) {
	 * 
	 * String captcha = object.toString(); if (captcha == null ||
	 * "".equals(captcha)) { message.put("code", 1); message.put("msg",
	 * "验证码已失效，请重新点击发送验证码"); } else { // session中存放的验证码是手机号+验证码 if
	 * (!captcha.equalsIgnoreCase(phone+codenum)) { message.put("code", 1);
	 * message.put("msg", "手机验证码错误"); } else { Map<String, Object> mapP = new
	 * HashMap<String, Object>(); mapP.put("username", phone); boolean flag =
	 * userService.exit(mapP); if (!flag) { //message.put("msg", "该手机号码未注册");
	 * OwnerUserDO udo = new OwnerUserDO(); Long userId = GenerateCode.gen16(8);
	 * udo.setUserId(userId); udo.setUsername(phone); udo.setPhone(phone);
	 * udo.setDeleteFlag(0); udo.setRegisterTime(new Date());
	 * udo.setLoginUserFlag(0); userService.save(udo);
	 * 
	 * OwnerUserDO udos = userService.getbyname(phone); if
	 * (udos==null||udos.getDeleteFlag() == 1) { message.put("msg", "禁止登录，请联系客服"); }
	 * else {
	 * 
	 * // String password = udos.getPassword(); //
	 * System.out.println("==================="+password+"========================")
	 * ; UsernamePasswordToken token = new UsernamePasswordToken(phone, codenum);
	 * subject.login(token);
	 * 
	 * udos.setLoginTime(new Date());
	 * 
	 * userService.update(udos); SubscriberDO info =
	 * subscriberService.getInfo(phone); // map.put("id", udos.getId()); //
	 * map.put("nickname", udos.getNickname()); // map.put("heardUrl",
	 * udos.getHeardUrl()); // map.put("loginTime", udos.getLoginTime());
	 * message.put("code", 0); message.put("msg", "登录成功"); message.put("data",
	 * info); getCouponByRegistry(udo.getId());
	 * 
	 * } } else { OwnerUserDO udo = userService.getbyname(phone); if
	 * (udo==null||udo.getDeleteFlag() == 1) { message.put("msg", "禁止登录，请联系客服"); }
	 * else {
	 * 
	 * // String password = udo.getPassword(); //
	 * System.out.println("==================="+password+"========================")
	 * ; UsernamePasswordToken token = new UsernamePasswordToken(phone, codenum);
	 * subject.login(token);
	 * 
	 * udo.setLoginTime(new Date());
	 * 
	 * userService.update(udo); SubscriberDO info =
	 * subscriberService.getInfo(phone); // map.put("id", udo.getId()); //
	 * map.put("nickname", udo.getNickname()); // map.put("heardUrl",
	 * udo.getHeardUrl()); // map.put("loginTime", udo.getLoginTime());
	 * message.put("code", 0); message.put("msg", "登录成功"); message.put("data",
	 * info);
	 * 
	 * } } } } } else { message.put("code", 1); message.put("msg", "手机验证码错误"); } }
	 * catch (AuthenticationException e) { message.put("code", 1);
	 * message.put("msg", "手机号或验证码错误"); } return message; }
	 */
 

    @Log("用户注册")
    @PostMapping("/register")
    Map<String, String> register(String phone, String codenum,String password) {
        Map<String, String> message = new HashMap<>();
        String msg = "";
        if (StringUtils.isBlank(phone)) {
            message.put("msg", "手机号码不能为空");
        } else {
            Subject subject = SecurityUtils.getSubject();
            Object object = subject.getSession().getAttribute("sys.login.check.code");
            if (object != null) {
            	String captcha = object.toString();
            	//String captcha = "666666";
                if (captcha == null || "".equals(captcha)) {
                    message.put("msg", "验证码已失效，请重新点击发送验证码");
                } else {
                    // session中存放的验证码是手机号+验证码
                    if (!captcha.equalsIgnoreCase(phone + codenum)) {
                        message.put("msg", "手机验证码错误");
                    } else {
                        Map<String, Object> mapP = new HashMap<String, Object>();
                        mapP.put("username", phone);
                        boolean flag = userService.exit(mapP);
                        if (flag) {
                            message.put("msg", "手机号码已存在");
                        } else {
                            OwnerUserDO udo = new OwnerUserDO();
                            Long userId = GenerateCode.gen16(8);
                            udo.setUserId(userId);
                            udo.setUsername(phone);
                            udo.setPhone(phone);
                            udo.setPassword(password);
                            udo.setDeleteFlag(1);
                            udo.setRegisterTime(new Date());
                            if (userService.save(udo) > 0) {
                                message.put("msg", "注册成功");
                               
                            } else {
                                message.put("msg", "注册失败");
                            }
                        }
                    }
                }
            } else {
                message.put("msg", "手机验证码错误");
            }
        }
        return message;
    }
    
  
    @Log("微信登录")  
	@PostMapping("/loginWechat")
    Map<String, Object> loginWechat(String openId,String heardUrl,String nickname) {
    	   Subject subject = SecurityUtils.getSubject();
 	       Map<String, Object> message = new HashMap<>();
	       System.out.println("=========openId=============="+openId);
 	    	try{
 	    		OwnerUserDO ownerUserDO = userService.getByOpenid(openId);
 	    		System.out.println("==========ownerUserDO=========="+ownerUserDO);
 	    		if(ownerUserDO!=null){
 	    			String phone = ownerUserDO.getPhone();
    	    		UsernamePasswordToken token = new UsernamePasswordToken(phone, openId);
                    subject.login(token);
                    ownerUserDO.setLoginTime(new Date());
                    userService.update(ownerUserDO);
                    message.put("data", ownerUserDO);
    	    	    message.put("code", 0);
    	    		message.put("msg", "登录成功");
 	    		}else{
 	    			OwnerUserDO users = new OwnerUserDO();
 	    			users.setDeleteFlag(1);
 	    			users.setRegisterTime(new Date());
 	    			Long userId = GenerateCode.gen16(8);
 	    			users.setUserId(userId);
 	    			users.setHeardUrl(heardUrl);
 	    			users.setNickname(nickname);
 	    			users.setOpenId(openId);
 	    			users.setUsername(openId);
 	    			users.setLoginUserFlag(0);
 	    			userService.save(users);
 	    			UsernamePasswordToken token = new UsernamePasswordToken(openId,openId);
                    subject.login(token);
 	    			System.out.println("==========users=========="+users);
 	    			message.put("code", 0);
    	    		message.put("msg", "微信登录成功，请修改用户的相关信息");
    	    		message.put("data", users);
 	    		}
	   			
	   		}catch (AuthenticationException e) {
	   			message.put("msg", "异常！请重新登录尝试");
	   		}
	    	return message;
    }
    
    @Log("绑定微信")  
	@PostMapping("/bindWechat")
    Map<String, Object> loginWechat(String openId,String phone,String heardUrl,String nickname) {
	    Map<String, Object> message = new HashMap<>();
	    Map<String, Object> params = new HashMap<>();
	    System.out.println("========phone=========="+phone);
	    if(phone!=null && !phone.equals("")){
	    	params.put("phone", phone);
	    	boolean flag = userService.exit(params);
	    	if(flag){
	    		OwnerUserDO ownerUser =  userService.getByphone(phone);
		    	System.out.println("=======OwnerUserDO==========="+ownerUser);
			    if(ownerUser != null && openId!= null){
			    	OwnerUserDO ownerUserDO1 =  userService.getByOpenid(openId);
			    	if(ownerUserDO1 == null){
			    		ownerUser.setOpenId(openId);
			    		ownerUser.setNickname(nickname);
			    		ownerUser.setHeardUrl(heardUrl);
				    	userService.update(ownerUser);
				    	message.put("msg", "绑定成功");
				    	message.put("code", 0);
			    	}else if(ownerUserDO1 !=null && ownerUserDO1.getPhone() == null){
			    		ownerUser.setOpenId(openId);
			    		ownerUser.setNickname(ownerUserDO1.getNickname());
			    		ownerUser.setHeardUrl(ownerUserDO1.getHeardUrl());
			    		
			    		userService.remove(ownerUserDO1.getId());
			    		userService.update(ownerUser);
			    		
			    		message.put("msg", "绑定成功");
				    	message.put("code", 1);
			    	}
			    }else if(ownerUser == null){
			    	message.put("msg", "该手机号已绑定");
			    	message.put("code", 1);
			    }else{
			    	message.put("msg", "绑定失败");
			    	message.put("code", 1);
			    }
	    	}else{
	    		message.put("msg", "手机号不存在，请设置...");
	    		message.put("code", 1);
	    	}
	    	
	    }
    	return message;
    }
    
    
    @Log("忘记密码")
	@PostMapping("/retpwd")
    Map<String, String> retpwd(String username, String password,  String codenum) {
        Map<String, String> message = new HashMap<>();
		if (StringUtils.isBlank(username)) {
			message.put("msg","手机号码不能为空");
		}else{
			OwnerUserDO udo= userService.getbyname(username);
			Subject subject = SecurityUtils.getSubject();
			Object object =subject.getSession().getAttribute("sys.login.check.code");
			if (object != null) {
	            String captcha = object.toString();
	            if (captcha == null || "".equals(captcha)) {
	                message.put("msg", "验证码已失效，请重新点击发送验证码");
	            } else {
	                // session中存放的验证码是手机号+验证码
	                if (!captcha.equalsIgnoreCase(username + codenum)) {
	                    message.put("msg", "手机验证码错误");
	                } else {
	                    Map<String, Object> mapP = new HashMap<String, Object>();
	                    mapP.put("username", username);
	                    boolean flag = userService.exit(mapP);
	                    if (!flag) {
	                        message.put("msg", "该手机号码未注册");
	                    }else{
	                    	udo.setPassword(password);
	            			if (userService.update(udo) > 0) {
	            				message.put("msg","修改成功");
	            			}
	                    }
	                }
	            }
	        } else {
	            message.put("msg", "手机验证码错误");
	        }
		}
		return message;
	}
    
    /**
     * 用户注册获取优惠券
     */
    
	/*
	 * public void getCouponByRegistry(Long userId){ List<CouponDO> list=
	 * couponService.listCouponByUsageScenario(2);//注册自动获取优惠券 CouponDO
	 * couponDO=null; if(list.size()>0){ couponDO=list.get(0);
	 * if(couponDO.getCouponSurplus()>0){ couponDO.setUserId(userId);
	 * couponDO.setIfUser(1); couponDO.setSendoutTime(new Date());
	 * couponDO.setCouponGroup(0); int result =
	 * couponService.saveSendoutCoupon(couponDO); if(result>0){
	 * subscriberService.insertCoupon(userId);
	 * couponService.descCoupon(couponDO.getCouponId()); } } }
	 * 
	 * }
	 */
    
   

	@Log("登出")
    @GetMapping("/logout")
    Map<String, String> logout() {
        Map<String, String> message = new HashMap<>();
        ShiroUtils.logout();
        message.put("msg", "登出成功");
        return message;
    }

}
