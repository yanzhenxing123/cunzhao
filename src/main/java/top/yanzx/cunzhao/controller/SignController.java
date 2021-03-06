package top.yanzx.cunzhao.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.yanzx.cunzhao.config.annotation.RequiresPermissions;
import top.yanzx.cunzhao.dao.SignDao;
import top.yanzx.cunzhao.dto.session.SessionUserInfo;
import top.yanzx.cunzhao.service.LoginService;
import top.yanzx.cunzhao.service.SignService;
import top.yanzx.cunzhao.service.SmsService;
import top.yanzx.cunzhao.service.TokenService;
import top.yanzx.cunzhao.util.CommonUtil;
import top.yanzx.cunzhao.util.constants.ErrorEnum;

import java.util.Date;
import java.util.HashMap;

/**
 * @Author: yanzx
 * @Date: 2022/2/11 20:27
 * @Description: 签到
 */

@RestController
@RequestMapping("/sign")
public class SignController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private SignService signService;

    /**
     * @Author: yanzx
     * @Date: 2022/2/12 20:00
     * @Description: 签到
     */
    @RequiresPermissions("sign:add")
    @GetMapping("/create")
    public JSONObject createSign() {
        JSONObject jsonObject = tokenService.getUserIdParams();
        return signService.createSign(jsonObject);
    }


    /**
     * @Author: yanzx
     * @Date: 2022/2/12 20:00
     * @Description: 获取用户签到的数量
     */
    @RequiresPermissions("sign:add")
    @GetMapping("/nums")
    public JSONObject signNums() {
        JSONObject jsonObject = tokenService.getUserIdParams();
        return signService.signNums(jsonObject);
    }

}
