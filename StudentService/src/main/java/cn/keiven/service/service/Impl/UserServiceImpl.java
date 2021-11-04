package cn.keiven.service.service.Impl;

import cn.keiven.service.dao.Impl.UserDaoImpl;
import cn.keiven.service.dao.UserDao;
import cn.keiven.service.domain.User;
import cn.keiven.service.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    public User longin(String name) {
        return userDao.findUserByName(name);

    }

    @Override
    public void logout() {

    }

}
