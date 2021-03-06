package top.yanzx.cunzhao.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import top.yanzx.cunzhao.dao.ProfileDao;
import top.yanzx.cunzhao.dao.SignDao;
import top.yanzx.cunzhao.util.CommonUtil;
import top.yanzx.cunzhao.util.DateUtil;
import top.yanzx.cunzhao.util.constants.ErrorEnum;

import java.util.HashMap;

/**
 * @Author: yanzx
 * @Date: 2022/2/11 22:18
 * @Description:
 */


@Service
public class ProfileService {

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private FileUploadService fileUploadService;


    /**
     * @author yanzx
     * @date 2022/2/12
     * @desc 设置默认用户信息：用于登录
     */
    public JSONObject setDefaultProfile(JSONObject jsonObject) {
        profileDao.setDefaultProfile(jsonObject);
        return CommonUtil.successJson();
    }

    public JSONObject info(JSONObject jsonObject) {
        /**
         *
         * @author yanzx
         * @date 2022/2/11
         * @param []
         * @return com.alibaba.fastjson.JSONObject
         *
         */
        JSONObject profileInfo = profileDao.getProfileInfo(jsonObject);
        return CommonUtil.successJson(profileInfo);
    }

    /**
     * @author yanzx
     * @date 2022/2/12
     * @desc 增加萝卜币
     */
    public JSONObject addCoin(JSONObject jsonObject) {
        profileDao.addCoin(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * @author yanzx
     * @date 2022/2/12
     * @desc 更新用户信息 birthday, nickname
     */
    public JSONObject updateProfile(JSONObject jsonObject) {
        if (!DateUtil.isRqFormat(jsonObject.getString("birthday"))) {
            return CommonUtil.errorJson(ErrorEnum.E_10012);
        }
        if (profileDao.updateProfile(jsonObject) + profileDao.updateNickname(jsonObject) != 2) {
            return CommonUtil.errorJson(ErrorEnum.E_10011);
        }
        return CommonUtil.successJson();
    }


    /**
     * @Author: yanzx
     * @Date: 2022/3/20 22:19
     * @Description: 更新avatar
     */
    public JSONObject updateAvatar(JSONObject jsonObject) {
        MultipartFile file = (MultipartFile) jsonObject.get("file");
        String returnFileUrl = fileUploadService.upload(file);
        if(returnFileUrl.equals("error")){
            return CommonUtil.errorJson(ErrorEnum.E_30001);
        }
        jsonObject.remove("file");
        jsonObject.put("avatarUrl",returnFileUrl);
        profileDao.updateAvatar(jsonObject);
        return CommonUtil.successJson(jsonObject);
    }
}