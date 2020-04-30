package com.boyo.wuhang.controller.base;

import com.boyo.wuhang.entity.base.BaseRole;
import com.boyo.wuhang.entity.base.BaseUser;
import com.boyo.wuhang.service.RedisService;
import com.boyo.wuhang.service.base.RoleService;
import com.boyo.wuhang.service.base.UserService;
import com.boyo.wuhang.ultity.DateJsonValueProcessor;
import com.boyo.wuhang.ultity.JsonBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Controller
@Api(tags = "登录管理")
public class SsoController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private RedisService redisService;

	@RequestMapping(value = "/sso/login",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "登录",notes = "返回成功与失败")
	public JSONObject Login(@RequestBody JSONObject jsonObject, HttpServletRequest request){
		if (jsonObject.get("userNo")==null){
			return JsonBuilder.build(1, "登入账号名为空", null);
		}
		if (jsonObject.get("password")==null){
			return JsonBuilder.build(1, "密码为空", null);
		}
		String userNo = jsonObject.getString("userNo");
		String password = jsonObject.getString("password");
		BaseUser user = userService.getUserByNo(userNo);
		if (user == null){
			return JsonBuilder.build(1, "用户不存在", null);
		}
		if (user.getTryLogCount() > 5
				&& (user.getLastLoginTime().getTime() + 600 * 1000) > Calendar.getInstance().getTime().getTime()) {
			return JsonBuilder.build(1, "连续5次登录失败，请稍后10分钟重试！", null);
		}
		String passwordMd5 = DigestUtils.md5DigestAsHex(password.getBytes());

		//获取登入IP
		String ip = request.getHeader("X-Real-IP");
		if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
			if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
				int index = ip.indexOf(",");
				if (index != -1) {
					ip = ip.substring(0, index);
				}
			} else {
				ip = request.getRemoteAddr();
			}
		}

		if (user.getUserPassword().equals(passwordMd5)){
			//密码验证成功 生成token --- Id + 用户名 + UUID
            String token = user.getId().toString() + user.getUserNo() + UUID.randomUUID().toString();
            token = DigestUtils.md5DigestAsHex(token.getBytes());
            //生成clientId
            String clientId = UUID.randomUUID().toString();
            clientId = DigestUtils.md5DigestAsHex(clientId.getBytes());

			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());

			BaseRole role = roleService.getRoleById(user.getRoleId());
			user.setUserPassword(null); //密码不能
			JSONObject contain = new JSONObject();
			contain.put("Token", token);
			contain.put("flag", true);
			contain.put("user", JSONObject.fromObject(user, jsonConfig).toString());
			contain.put("isAdmin", role.getIsAdmin());
			contain.put("system",role.getTargetSystem());

			redisService.set(clientId, contain); //只有一个key = ClientId

			BaseUser updateUser = new BaseUser();
			updateUser.setId(user.getId());
			updateUser.setLastLoginTime(new Date());
			updateUser.setLoginIP(ip);
			updateUser.setTryLogCount(0);

			if (userService.updateUserBase(updateUser)){
				user.setUserPassword(null);
				JSONObject result = new JSONObject();
				result.put("Token", token);
				result.put("Client", clientId);
				result.put("user", JSONObject.fromObject(user, jsonConfig).toString());
				return JsonBuilder.build(0, "登入成功", result);
			} else {
				return JsonBuilder.build(1, "update error", null);
			}
		}else {
			//登入失败
			BaseUser updateUser = new BaseUser();
			updateUser.setId(user.getId());
			updateUser.setLoginIP(ip);
			updateUser.setLastLoginTime(new Date());
			updateUser.setTryLogCount(user.getTryLogCount() + 1);
			if (userService.updateUserBase(updateUser)){
				return JsonBuilder.build(1, "密码或用户名错误，登入失败", null);
			} else {
				return JsonBuilder.build(1, "update error", null);
			}
		}
	}

	@RequestMapping(value = "/api/sso/login_out", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "注销登录",notes = "返回成功与失败")
	public JSONObject LoginOut(HttpServletRequest request) {
		String clientId = request.getHeader("Client");
		redisService.delete(clientId);
		return JsonBuilder.build(0, "注销成功", null);
	}

	@RequestMapping(value = {"/sso/no_login"}, method = RequestMethod.POST)
	@ResponseBody
	public JSONObject NoLogin(HttpServletRequest request) {
		return JsonBuilder.build(2, "未登录", null);
	}


	@RequestMapping(value = {"/testGross"})
	@ResponseBody
	public JSONObject testGross(){
		Integer gross = 50;
		return JsonBuilder.build(0,"",gross);
	}

	@RequestMapping(value = {"/testTare"})
	@ResponseBody
	public JSONObject testTare(){
		Integer gross = 10;
		return JsonBuilder.build(0,"",gross);
	}
}
