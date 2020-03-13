package com.stylefeng.guns.rest.modular.auth.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.rest.common.exception.BizExceptionEnum;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthRequest;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthResponse;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.modular.auth.validator.IReqValidator;
import com.stylefeng.guns.rest.modular.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 请求验证的
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:22
 */
@RestController
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;



    @Reference(interfaceClass = UserAPI.class)
    private UserAPI userAPI;

    // 去掉guns自带的用户名密码验证机制，使用自定义的
    @RequestMapping(value = "${jwt.auth-path}")
    public ResponseVo createAuthenticationToken(AuthRequest authRequest) {
        boolean validate = true;

//        int userId = userAPI.login(authRequest.getUserName(), authRequest.getPassword());
        int userId = 2;

        if(userId == 0){ // id为0就表示验证失败
            validate = false;
        }

        if (validate) {
            final String randomKey = jwtTokenUtil.getRandomKey();
            final String token = jwtTokenUtil.generateToken(userId+"", randomKey);

            return ResponseVo.success(new AuthResponse(token, randomKey));
        } else {
            return ResponseVo.serviceFail("用户名密码错误");
        }
    }
}
