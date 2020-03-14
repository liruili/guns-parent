package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.api.user.vo.UserInfoModel;
import com.stylefeng.guns.api.user.vo.UserModel;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.modular.vo.ResponseVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference(interfaceClass = UserAPI.class)
    private UserAPI userAPI;

    @PostMapping("register")
    public ResponseVo register(UserModel userModel){
        if(StringUtils.isEmpty(userModel.getUsername())){
            return ResponseVo.serviceFail("用户名不能为空");
        }
        if(StringUtils.isEmpty(userModel.getPassword())){
            return ResponseVo.serviceFail("密码不能为空");
        }

        boolean isRegister = userAPI.register(userModel);
        if(isRegister){
            return ResponseVo.success("注册成功");
        }
        return ResponseVo.serviceFail("注册失败");
    }

    @PostMapping("check")
    public ResponseVo check(String username){
        if(StringUtils.isEmpty(username)){
            return ResponseVo.serviceFail("用户名不能为空");
        }

        if(userAPI.checkUsername(username)){
            return ResponseVo.serviceFail("用户名已存在");
        }else{
            return ResponseVo.success("用户名不存在");
        }

    }

    @GetMapping("logout")
    public ResponseVo logout(String username){
       return ResponseVo.success("用户已退出");
    }

    @GetMapping("getUserInfo")
    public ResponseVo getUserInfo(){
        String userId = CurrentUser.getUserId();
        if(StringUtils.isNotEmpty(userId)){
            UserInfoModel userInfo = userAPI.getUserInfo(Integer.parseInt(userId));
            if(userInfo != null){
                return ResponseVo.success(userInfo);
            }else{
                return ResponseVo.serviceFail("用户信息查询失败");
            }
        }
        return ResponseVo.serviceFail("用户未登录");
    }

    @PostMapping("updateUserInfo")
    public ResponseVo updateUserInfo(UserInfoModel userInfoModel){
        String userId = CurrentUser.getUserId();
        if(StringUtils.isNotEmpty(userId)){
            int uuid = Integer.parseInt(userId);
            if(uuid != userInfoModel.getUuid()){
                return ResponseVo.serviceFail("不允许修改他人信息");
            }
            UserInfoModel userInfo = userAPI.updateUserInfo(userInfoModel);
            if(userInfo != null){
                return ResponseVo.success(userInfo);
            }else{
                return ResponseVo.serviceFail("用户信息修改失败");
            }
        }
        return ResponseVo.serviceFail("用户未登录");
    }


}
