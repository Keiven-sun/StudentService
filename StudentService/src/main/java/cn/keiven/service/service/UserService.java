package cn.keiven.service.service;

import cn.keiven.service.domain.User;

public interface UserService {

    /*
    * 登录用户功能
    * */
    public User longin(String name);

    public void logout();
}
