package top.yanzx.cunzhao.controller;

import com.alibaba.fastjson.JSONObject;
import top.yanzx.cunzhao.service.LoginService;
import top.yanzx.cunzhao.service.SmsService;
import top.yanzx.cunzhao.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: heeexy
 * @description: 登录相关Controller
 * @date: 2017/10/24 10:33
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private SmsService smsService;


    /**
     * 登录 使用短信验证码进行登录
     */
    @PostMapping
    public JSONObject login(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "phone_number,message_id,code");
        requestJson.put("username", requestJson.get("phone_number"));
        smsService.authCode(requestJson);
        return loginService.login(requestJson);
    }


    /**
     * 登录 使用账号密码进行登录
     */
    @PostMapping("/auth")
    public JSONObject authLogin(@RequestBody JSONObject requestJson) {
//        CommonUtil.hasAllRequired(requestJson, "username,password,message_id,code");
        CommonUtil.hasAllRequired(requestJson, "username,password");
//        smsService.authCode(requestJson);
        return loginService.authLogin(requestJson);
    }



    /**
     * 查询当前登录用户的信息
     */
    @PostMapping("/getInfo")
    public JSONObject getInfo() {
        return loginService.getInfo();
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    public JSONObject logout() {
        return loginService.logout();
    }

}
