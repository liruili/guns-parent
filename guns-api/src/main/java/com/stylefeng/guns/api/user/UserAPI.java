package com.stylefeng.guns.api.user;

public interface UserAPI {

    int login(String name, String pwd);

    boolean register(UserModel userModel);

    boolean checkUsername(String username);

    UserInfoModel getUserInfo(int uuid);

    UserInfoModel updateUserInfo(UserInfoModel userInfoModel);

}
