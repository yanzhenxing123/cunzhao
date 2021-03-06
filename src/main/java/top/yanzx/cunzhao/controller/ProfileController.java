package top.yanzx.cunzhao.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.yanzx.cunzhao.config.annotation.RequiresPermissions;
import top.yanzx.cunzhao.dao.ProfileDao;
import top.yanzx.cunzhao.dto.session.SessionUserInfo;
import top.yanzx.cunzhao.service.ProfileService;
import top.yanzx.cunzhao.service.SignService;
import top.yanzx.cunzhao.service.TokenService;
import top.yanzx.cunzhao.util.CommonUtil;
import top.yanzx.cunzhao.util.DateUtil;

/**
 * @Author: yanzx
 * @Date: 2022/2/11 20:27
 * @Description: 用户信息
 */

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private SignService signService;

    @Autowired
    private ProfileService profileService;

    /**
     * 用户信息
     */
    @RequiresPermissions("sign:add")
    @GetMapping("/info")
    public JSONObject getProfileInfo() {
        JSONObject jsonObject = tokenService.getUserIdParams();
        return profileService.info(jsonObject);
    }

    /**
     * 添加金币接口
     */
    @RequiresPermissions("sign:add")
    @PostMapping("/addCoin")
    public JSONObject addCoin(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "num");
        requestJson.put("userId", tokenService.getUserId());
        return profileService.addCoin(requestJson);
    }


    /***
     * @Author: yanzx
     * @Date: 2022/3/2 22:51
     * @Description: 用户信息修改接口
     */
    @RequiresPermissions("sign:add")
    @PostMapping("/update")
    public JSONObject update(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "birthday,nickname");
        requestJson.put("userId", tokenService.getUserId());
        return profileService.updateProfile(requestJson);
    }

    /**
     * @Author: yanzx
     * @Date: 2022/3/20 22:11
     * @Description: 用户头像修改
     */
    @RequiresPermissions("sign:add")
    @PostMapping("/updateAvatar")
    public JSONObject updateAvatar(@RequestParam("file") MultipartFile file) {
        JSONObject requestJson = new JSONObject();
        if (file == null){
            CommonUtil.hasAllRequired(requestJson, "file");
        }
        requestJson.put("userId", tokenService.getUserId());
        requestJson.put("file", file);
        return profileService.updateAvatar(requestJson);
//
    }








}
