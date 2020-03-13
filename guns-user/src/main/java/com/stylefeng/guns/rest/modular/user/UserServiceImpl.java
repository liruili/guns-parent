package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.api.user.UserInfoModel;
import com.stylefeng.guns.api.user.UserModel;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.persistence.dao.MoocUserTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocUserT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;

@Component
@Service
public class UserServiceImpl implements UserAPI {

    @Autowired
    private MoocUserTMapper userTMapper;

    @Override
    public int login(String name, String pwd) {
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUserName(name);
        moocUserT.setUserPwd(MD5Util.encrypt(pwd));
        MoocUserT userT = userTMapper.selectOne(moocUserT);
        if(userT != null){
            return userT.getUuid();
        }
        return 0;
    }

    @Override
    public boolean register(UserModel userModel) {

        String md5Pwd = MD5Util.encrypt(userModel.getPassword());

        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUserName(userModel.getUsername());
        moocUserT.setUserPwd(md5Pwd);
        moocUserT.setEmail(userModel.getEmail());
        moocUserT.setUserPhone(userModel.getPhone());
        moocUserT.setAddress(userModel.getAddress());
        moocUserT.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        System.out.println(moocUserT);

        Integer id = userTMapper.insert(moocUserT);
        if(id != null && id > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean checkUsername(String username) {
        EntityWrapper<MoocUserT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_name",username);
        Integer count = userTMapper.selectCount(entityWrapper);
        if(count != null && count > 0){
            return true;
        }
        return false;
    }

    private UserInfoModel do2UserInfo(MoocUserT moocUserT){
        UserInfoModel userInfoModel = new UserInfoModel();

        if(moocUserT != null){
            userInfoModel.setUuid(moocUserT.getUuid());
            userInfoModel.setNickName(moocUserT.getNickName());
            userInfoModel.setUserName(moocUserT.getUserName());
            userInfoModel.setUpdateTime(moocUserT.getUpdateTime().getTime());
            userInfoModel.setSex(moocUserT.getUserSex());
            userInfoModel.setPhone(moocUserT.getUserPhone());
            userInfoModel.setLifeState(moocUserT.getLifeState()+"");
            userInfoModel.setHeadAddress(moocUserT.getHeadUrl());
            userInfoModel.setEmail(moocUserT.getEmail());
            userInfoModel.setBirthday(moocUserT.getBirthday());
            userInfoModel.setBiography(moocUserT.getBiography());
            userInfoModel.setBeginTime(moocUserT.getBeginTime().getTime());
            userInfoModel.setAddress(moocUserT.getAddress());
        }

        return userInfoModel;
    }

    @Override
    public UserInfoModel getUserInfo(int uuid) {

        MoocUserT moocUserT = userTMapper.selectById(uuid);
        UserInfoModel userInfoModel = do2UserInfo(moocUserT);
        return userInfoModel;
    }

    private Timestamp time2Date(long time){
        return new Timestamp(time);
    }

    @Override
    public UserInfoModel updateUserInfo(UserInfoModel userInfoModel) {
        MoocUserT moocUserT = new MoocUserT();

        moocUserT.setAddress(userInfoModel.getAddress());
        moocUserT.setUserPhone(userInfoModel.getPhone());
        moocUserT.setEmail(userInfoModel.getEmail());
        moocUserT.setUserSex(userInfoModel.getSex());
        moocUserT.setUpdateTime(time2Date(System.currentTimeMillis()));
        moocUserT.setNickName(userInfoModel.getNickName());
        moocUserT.setLifeState(Integer.parseInt(userInfoModel.getLifeState()));
        moocUserT.setHeadUrl(userInfoModel.getHeadAddress());
        moocUserT.setBirthday(userInfoModel.getBirthday());
        moocUserT.setBiography(userInfoModel.getBiography());
        moocUserT.setBeginTime(time2Date(userInfoModel.getBeginTime()));
        moocUserT.setUuid(userInfoModel.getUuid());

        Integer result = userTMapper.updateById(moocUserT);
        if(result != null && result > 0){
            return getUserInfo(userInfoModel.getUuid());
        }
        return userInfoModel;
    }
}
